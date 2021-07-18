package org.galegofer.phone.test.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.galegofer.phone.test.domain.PhoneApplicationException;
import org.galegofer.phone.test.domain.model.BookedPhone;
import org.galegofer.phone.test.domain.model.entity.BookedPhoneEntity;
import org.galegofer.phone.test.domain.model.payload.ConnectivityFeature;
import org.galegofer.phone.test.domain.model.payload.Device;
import org.galegofer.phone.test.domain.model.payload.Root;
import org.galegofer.phone.test.domain.model.payload.Specs;
import org.galegofer.phone.test.mapper.BookedPhoneMapper;
import org.galegofer.phone.test.repository.BookedPhoneRepository;
import org.galegofer.phone.test.repository.DeviceInformationRepository;
import org.galegofer.phone.test.repository.PhoneRepository;
import org.galegofer.phone.test.service.PhoneService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toUnmodifiableList;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class PhoneServiceImpl implements PhoneService {

    BookedPhoneMapper mapper;

    PhoneRepository phoneRepository;
    BookedPhoneRepository bookedPhoneRepository;
    DeviceInformationRepository deviceInformationRepository;

    public Flux<BookedPhone> getAllBookings() {
        log.info("Calling get all phones");

        return bookedPhoneRepository.findAll()
                .flatMap(this::enrichWithTechnicalsDetails);
    }

    @Override
    public Mono<BookedPhone> bookPhone(String personCorpKey, String phoneSerialNumber) {
        log.info("Calling book phone with person corp key: {}, and phone serial number: {}", personCorpKey, phoneSerialNumber);

        return bookedPhoneRepository.findBySerialNumber(phoneSerialNumber)
                .flatMap(emptyResult -> Mono.<BookedPhone>error(PhoneApplicationException.builder()
                        .message(String.format("Provided phone with serial number: %s, is already booked", phoneSerialNumber))
                        .statusCode(BAD_REQUEST.value())
                        .build()))
                .switchIfEmpty(saveBooking(personCorpKey, phoneSerialNumber)
                        .doOnSuccess(result -> log.info("Phone with serial number: {}, was successfully booked for person with corp key: {}", phoneSerialNumber, personCorpKey))
                        .flatMap(this::enrichWithTechnicalsDetails));
    }

    @Override
    public Mono<BookedPhone> returnPhone(String phoneSerialNumber) {
        log.info("Calling return phone with phone serial number: {}", phoneSerialNumber);

        return bookedPhoneRepository.findBySerialNumber(phoneSerialNumber)
                .flatMap(returnedPhone -> bookedPhoneRepository.deleteBySerialNumber(returnedPhone.getSerialNumber())
                        .doOnSuccess(result -> log.info("Phone with serial number: {}, was successfully returned", phoneSerialNumber))
                        .flatMap(result -> enrichWithTechnicalsDetails(returnedPhone)))
                .switchIfEmpty(Mono.error(PhoneApplicationException.builder()
                        .message(String.format("Provided phone with serial number: %s, wasn't reserved", phoneSerialNumber))
                        .statusCode(NOT_FOUND.value())
                        .build()));
    }

    private Mono<BookedPhoneEntity> saveBooking(String personCorpKey, String phoneSerialNumber) {
        return bookedPhoneRepository.save(BookedPhoneEntity.builder()
                .bookDate(LocalDateTime.now())
                .personCorpKey(personCorpKey)
                .serialNumber(phoneSerialNumber)
                .build());
    }

    private Mono<BookedPhone> enrichWithTechnicalsDetails(BookedPhoneEntity returnedPhone) {
        return phoneRepository.findBySerialNumber(returnedPhone.getSerialNumber())
                .flatMap(phone -> deviceInformationRepository.getDevices(phone.getManufacturer(), phone.getModel())
                        .map(details -> mapper.entityToModel(returnedPhone)
                                .withManufacturer(phone.getManufacturer())
                                .withModel(phone.getModel())
                                .withTechnologies(extractDeviceDetails(details))));
    }

    private static List<String> extractDeviceDetails(Root details) {
        return details.getDevices().stream()
                .findFirst()
                .map(Device::getSpecs)
                .map(Specs::getConnectivityFeatures)
                .map(features -> features.stream()
                        .map(ConnectivityFeature::getDescription)
                        .collect(toUnmodifiableList()))
                .orElse(emptyList());
    }
}
