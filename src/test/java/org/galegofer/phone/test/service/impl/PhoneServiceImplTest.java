package org.galegofer.phone.test.service.impl;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.galegofer.phone.test.domain.PhoneApplicationException;
import org.galegofer.phone.test.domain.model.BookedPhone;
import org.galegofer.phone.test.domain.model.entity.BookedPhoneEntity;
import org.galegofer.phone.test.domain.model.entity.PhoneEntity;
import org.galegofer.phone.test.domain.model.payload.Root;
import org.galegofer.phone.test.mapper.BookedPhoneMapper;
import org.galegofer.phone.test.repository.BookedPhoneRepository;
import org.galegofer.phone.test.repository.DeviceInformationRepository;
import org.galegofer.phone.test.repository.PhoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(MockitoExtension.class)
class PhoneServiceImplTest {

    private final PodamFactory factory = new PodamFactoryImpl();

    @Mock
    private BookedPhoneMapper mapper;

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private BookedPhoneRepository bookedPhoneRepository;

    @Mock
    private DeviceInformationRepository deviceInformationRepository;

    @Captor
    private ArgumentCaptor<BookedPhoneEntity> captor;

    @InjectMocks
    private PhoneServiceImpl underTest;

    @Test
    void getAllBookings() {
        var bookedPhoneEntity = factory.manufacturePojoWithFullData(BookedPhoneEntity.class);
        var phoneEntity = PhoneEntity.builder()
            .serialNumber(bookedPhoneEntity.getSerialNumber())
            .manufacturer("manufacturer")
            .model("model")
            .build();
        var bookedPhone = BookedPhone.builder()
            .serialNumber(phoneEntity.getSerialNumber())
            .manufacturer(phoneEntity.getManufacturer())
            .model(phoneEntity.getModel())
            .build();
        var root = Root.builder()
            .build();

        var expectedBookedPhones = BookedPhone.builder()
            .manufacturer(phoneEntity.getManufacturer())
            .model(phoneEntity.getModel())
            .serialNumber(bookedPhoneEntity.getSerialNumber())
            .technologies(emptyList())
            .build();

        when(mapper.entityToModel(bookedPhoneEntity))
            .thenReturn(bookedPhone);
        when(bookedPhoneRepository.findAll())
            .thenReturn(Flux.just(bookedPhoneEntity));
        when(phoneRepository.findBySerialNumber(bookedPhoneEntity.getSerialNumber()))
            .thenReturn(Mono.just(phoneEntity));
        when(deviceInformationRepository.getDeviceInformation(phoneEntity.getManufacturer(), phoneEntity.getModel()))
            .thenReturn(Mono.just(root));

        var resultBookedPhones = underTest.getAllBookings();

        StepVerifier.create(resultBookedPhones)
            .expectNextMatches(expectedBookedPhones::equals)
            .verifyComplete();

        verify(mapper).entityToModel(bookedPhoneEntity);
        verify(bookedPhoneRepository).findAll();
        verify(phoneRepository).findBySerialNumber(bookedPhoneEntity.getSerialNumber());
        verify(deviceInformationRepository).getDeviceInformation(phoneEntity.getManufacturer(), phoneEntity.getModel());
    }

    @Test
    void bookPhone() {
        var personCorpKey = "personCorpKey";

        var bookedPhoneEntity = factory.manufacturePojoWithFullData(BookedPhoneEntity.class);
        var phoneEntity = PhoneEntity.builder()
            .serialNumber(bookedPhoneEntity.getSerialNumber())
            .manufacturer("manufacturer")
            .model("model")
            .build();
        var bookedPhone = BookedPhone.builder()
            .serialNumber(phoneEntity.getSerialNumber())
            .manufacturer(phoneEntity.getManufacturer())
            .model(phoneEntity.getModel())
            .build();
        var root = Root.builder()
            .build();

        var expectedBookedPhone = BookedPhone.builder()
            .manufacturer(phoneEntity.getManufacturer())
            .model(phoneEntity.getModel())
            .serialNumber(bookedPhoneEntity.getSerialNumber())
            .technologies(emptyList())
            .build();

        when(bookedPhoneRepository.findBySerialNumber(bookedPhoneEntity.getSerialNumber()))
            .thenReturn(Mono.empty());
        when(mapper.entityToModel(bookedPhoneEntity))
            .thenReturn(bookedPhone);
        when(bookedPhoneRepository.save(captor.capture()))
            .thenReturn(Mono.just(bookedPhoneEntity));
        when(phoneRepository.findBySerialNumber(bookedPhoneEntity.getSerialNumber()))
            .thenReturn(Mono.just(phoneEntity));
        when(deviceInformationRepository.getDeviceInformation(phoneEntity.getManufacturer(), phoneEntity.getModel()))
            .thenReturn(Mono.just(root));

        var resultBookedPhone = underTest.bookPhone(personCorpKey, bookedPhoneEntity.getSerialNumber());

        StepVerifier.create(resultBookedPhone)
            .expectNextMatches(expectedBookedPhone::equals)
            .verifyComplete();

        verify(bookedPhoneRepository).findBySerialNumber(bookedPhoneEntity.getSerialNumber());
        verify(mapper).entityToModel(bookedPhoneEntity);
        verify(bookedPhoneRepository).save(captor.getValue());
        verify(phoneRepository).findBySerialNumber(bookedPhoneEntity.getSerialNumber());
        verify(deviceInformationRepository).getDeviceInformation(phoneEntity.getManufacturer(), phoneEntity.getModel());
    }

    @Test
    void bookPhoneAlreadyBooked() {
        var personCorpKey = "personCorpKey";

        var bookedPhoneEntity = factory.manufacturePojoWithFullData(BookedPhoneEntity.class);
        var phoneEntity = PhoneEntity.builder()
            .serialNumber(bookedPhoneEntity.getSerialNumber())
            .manufacturer("manufacturer")
            .model("model")
            .build();

        var expectedException = PhoneApplicationException.builder()
            .message(String
                .format("Provided phone with serial number: %s, is already booked", phoneEntity.getSerialNumber()))
            .statusCode(BAD_REQUEST.value())
            .build();

        when(bookedPhoneRepository.findBySerialNumber(bookedPhoneEntity.getSerialNumber()))
            .thenReturn(Mono.just(bookedPhoneEntity));
        when(bookedPhoneRepository.save(captor.capture()))
            .thenReturn(Mono.just(bookedPhoneEntity));

        var resultBookedPhone = underTest.bookPhone(personCorpKey, bookedPhoneEntity.getSerialNumber());

        StepVerifier.create(resultBookedPhone)
            .verifyErrorMatches(expectedException::equals);

        verify(bookedPhoneRepository).findBySerialNumber(bookedPhoneEntity.getSerialNumber());
        verify(bookedPhoneRepository).save(captor.getValue());
        verify(phoneRepository, never()).findBySerialNumber(bookedPhoneEntity.getSerialNumber());
        verify(deviceInformationRepository, never())
            .getDeviceInformation(phoneEntity.getManufacturer(), phoneEntity.getModel());
    }

    @Test
    void returnPhone() {
        var bookedPhoneEntity = factory.manufacturePojoWithFullData(BookedPhoneEntity.class);
        var phoneEntity = PhoneEntity.builder()
            .serialNumber(bookedPhoneEntity.getSerialNumber())
            .manufacturer("manufacturer")
            .model("model")
            .build();
        var bookedPhone = BookedPhone.builder()
            .serialNumber(phoneEntity.getSerialNumber())
            .manufacturer(phoneEntity.getManufacturer())
            .model(phoneEntity.getModel())
            .build();
        var root = Root.builder()
            .build();

        var expectedBookedPhone = BookedPhone.builder()
            .manufacturer(phoneEntity.getManufacturer())
            .model(phoneEntity.getModel())
            .serialNumber(bookedPhoneEntity.getSerialNumber())
            .technologies(emptyList())
            .build();

        when(bookedPhoneRepository.findBySerialNumber(bookedPhoneEntity.getSerialNumber()))
            .thenReturn(Mono.just(bookedPhoneEntity));
        when(mapper.entityToModel(bookedPhoneEntity))
            .thenReturn(bookedPhone);
        when(bookedPhoneRepository.deleteBySerialNumber(bookedPhoneEntity.getSerialNumber()))
            .thenReturn(Mono.just(1));
        when(phoneRepository.findBySerialNumber(bookedPhoneEntity.getSerialNumber()))
            .thenReturn(Mono.just(phoneEntity));
        when(deviceInformationRepository.getDeviceInformation(phoneEntity.getManufacturer(), phoneEntity.getModel()))
            .thenReturn(Mono.just(root));

        var resultBookedPhone = underTest.returnPhone(bookedPhoneEntity.getSerialNumber());

        StepVerifier.create(resultBookedPhone)
            .expectNextMatches(expectedBookedPhone::equals)
            .verifyComplete();

        verify(bookedPhoneRepository).findBySerialNumber(bookedPhoneEntity.getSerialNumber());
        verify(mapper).entityToModel(bookedPhoneEntity);
        verify(bookedPhoneRepository).deleteBySerialNumber(bookedPhoneEntity.getSerialNumber());
        verify(phoneRepository).findBySerialNumber(bookedPhoneEntity.getSerialNumber());
        verify(deviceInformationRepository).getDeviceInformation(phoneEntity.getManufacturer(), phoneEntity.getModel());
    }

    @Test
    void returnPhoneAlreadyReturned() {
        var bookedPhoneEntity = factory.manufacturePojoWithFullData(BookedPhoneEntity.class);
        var phoneEntity = PhoneEntity.builder()
            .serialNumber(bookedPhoneEntity.getSerialNumber())
            .manufacturer("manufacturer")
            .model("model")
            .build();
        var bookedPhone = BookedPhone.builder()
            .serialNumber(phoneEntity.getSerialNumber())
            .manufacturer(phoneEntity.getManufacturer())
            .model(phoneEntity.getModel())
            .build();
        var root = Root.builder()
            .build();

        var expectedException = PhoneApplicationException.builder()
            .message(String
                .format("Provided phone with serial number: %s, wasn't reserved", phoneEntity.getSerialNumber()))
            .statusCode(NOT_FOUND.value())
            .build();

        when(bookedPhoneRepository.findBySerialNumber(bookedPhoneEntity.getSerialNumber()))
            .thenReturn(Mono.empty());

        var resultBookedPhone = underTest.returnPhone(bookedPhoneEntity.getSerialNumber());

        StepVerifier.create(resultBookedPhone)
            .verifyErrorMatches(expectedException::equals);

        verify(bookedPhoneRepository).findBySerialNumber(bookedPhoneEntity.getSerialNumber());
        verify(bookedPhoneRepository, never()).deleteBySerialNumber(bookedPhoneEntity.getSerialNumber());
        verify(deviceInformationRepository, never())
            .getDeviceInformation(phoneEntity.getManufacturer(), phoneEntity.getModel());
    }
}