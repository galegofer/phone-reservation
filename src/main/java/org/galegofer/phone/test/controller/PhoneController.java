package org.galegofer.phone.test.controller;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.galegofer.phone.test.domain.model.payload.BookedPhoneRequestPayload;
import org.galegofer.phone.test.domain.model.payload.BookedPhoneResponsePayload;
import org.galegofer.phone.test.mapper.BookedPhoneMapper;
import org.galegofer.phone.test.service.PhoneService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/phone")
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class PhoneController {

    PhoneService phoneService;
    BookedPhoneMapper mapper;

    @GetMapping("/list-bookings")
    public Flux<BookedPhoneResponsePayload> getAllBookings() {
        return phoneService.getAllBookings()
            .map(mapper::modelToPayload);
    }

    @PostMapping("/book")
    public Mono<BookedPhoneResponsePayload> bookPhone(@RequestBody @Valid BookedPhoneRequestPayload requestPayload) {
        return phoneService.bookPhone(requestPayload.getPersonCorpKey(), requestPayload.getSerialNumber())
            .map(mapper::modelToPayload);
    }

    @PostMapping("/return")
    public Mono<BookedPhoneResponsePayload> returnPhone(@RequestBody @Valid BookedPhoneRequestPayload requestPayload) {
        return phoneService.returnPhone(requestPayload.getSerialNumber())
            .map(mapper::modelToPayload);
    }
}
