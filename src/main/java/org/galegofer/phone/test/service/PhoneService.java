package org.galegofer.phone.test.service;

import org.galegofer.phone.test.domain.model.BookedPhone;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PhoneService {

    /**
     * Get all the bookings made in the system enriched with all the information related to it's connectivity.
     *
     * @return A Flux of BookedPhone.
     */
    Flux<BookedPhone> getAllBookings();

    /**
     * Books the given device based on the given phone serial number for the person associated to the given corporate
     * key.
     *
     * @param personCorpKey The cord key associated to the person who is creating the booking to take the device
     * @param phoneSerialNumber The unique device serial number for the phone that will be booked.
     * @return the booked device enriched with all the information related to it's connectivity.
     */
    Mono<BookedPhone> bookPhone(String personCorpKey, String phoneSerialNumber);

    /**
     * Returns a previously booked device.
     *
     * @param phoneSerialNumber The unique device serial number to be returned.
     * @return the returned device enriched with all the information related to it's connectivity.
     */
    Mono<BookedPhone> returnPhone(String phoneSerialNumber);
}
