package org.galegofer.phone.test.service;

import org.galegofer.phone.test.domain.model.BookedPhone;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PhoneService {
    Flux<BookedPhone> getAllBookings();
    Mono<BookedPhone> bookPhone(String personCorpKey, String phoneSerialNumber);
    Mono<BookedPhone> returnPhone(String phoneSerialNumber);
}
