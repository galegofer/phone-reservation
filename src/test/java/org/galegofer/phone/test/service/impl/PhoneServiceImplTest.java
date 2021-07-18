package org.galegofer.phone.test.service.impl;

import org.galegofer.phone.test.repository.PhoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(MockitoExtension.class)
class PhoneServiceImplTest {

    private final PodamFactory factory = new PodamFactoryImpl();

    @Mock
    private PhoneRepository phoneRepository;

    @Captor
    private ArgumentCaptor<String> captor;

    @InjectMocks
    private PhoneServiceImpl underTest;

    @Test
    void getWeatherByCityName() {
//        BookedPhone expectedBookedPhone = factory.manufacturePojoWithFullData(BookedPhone.class);
//
//        when(phoneRepository.findBySerialNumber(captor.capture()))
//                .thenReturn(Mono.just(expectedBookedPhone));
//
//        Mono<BookedPhone> postById = underTest.getAllBookings();
//
//        StepVerifier.create(postById)
//                .expectNextMatches(expectedWeather::equals)
//                .verifyComplete();
//
//        verify(phoneRepository).getPhones(captor.getValue());
    }
}