package org.galegofer.phone.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.galegofer.phone.test.domain.model.payload.BookedPhoneRequestPayload;
import org.galegofer.phone.test.domain.model.payload.BookedPhoneResponsePayload;
import org.galegofer.phone.test.mapper.BookedPhoneMapper;
import org.galegofer.phone.test.service.PhoneService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/phone")
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class PhoneController {

    PhoneService phoneService;
    BookedPhoneMapper mapper;

    // FIXME: Add validation
    // FIXME: Add IT.
    @GetMapping("/list-bookings")
    public Flux<BookedPhoneResponsePayload> getAllBookings() {
        return phoneService.getAllBookings()
                .map(mapper::modelToPayload);
    }

    @PostMapping("/book")
    public Mono<BookedPhoneResponsePayload> bookPhone(@RequestBody BookedPhoneRequestPayload requestPayload) {
        return phoneService.bookPhone(requestPayload.getPersonCorpKey(), requestPayload.getSerialNumber())
                .map(mapper::modelToPayload);
    }

    @PostMapping("/return")
    public Mono<BookedPhoneResponsePayload> returnPhone(@RequestBody BookedPhoneRequestPayload requestPayload) {
        return phoneService.returnPhone(requestPayload.getSerialNumber())
                .map(mapper::modelToPayload);
    }
}
