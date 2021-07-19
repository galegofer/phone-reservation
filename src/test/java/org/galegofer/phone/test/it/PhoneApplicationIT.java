package org.galegofer.phone.test.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

import java.io.IOException;
import org.galegofer.phone.test.domain.model.payload.BookedPhoneRequestPayload;
import org.galegofer.phone.test.domain.model.payload.BookedPhoneResponsePayload;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import reactor.core.publisher.Mono;

@Testable
class PhoneApplicationIT extends AbstractIT {

    private static final String PATH_GET_ALL_BOOKINGS = "/phone/list-bookings";
    private static final String PATH_BOOK = "/phone/book";
    private static final String PATH_RETURN = "/phone/return";

    private static final String USER_CORP_KEY = "uu0000";

    @Test
    void getAllBookedDevices() throws IOException {
        mockGet(fromPath("/api/get-devices?key={appKey}&manufacturer={manufacturer}&name={model}&limit=1")
                .buildAndExpand("5b882ff0382e", "Nokia", "3310")
                .getPath()
            , "/it/nokia-3310-response.json");

        webTestClient
            .get()
            .uri(uriBuilder -> uriBuilder
                .path(PATH_GET_ALL_BOOKINGS)
                .build())
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(BookedPhoneResponsePayload.class)
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getManufacturer())
                .isEqualTo("Nokia"))
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getModel())
                .isEqualTo("3310"))
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getTechnologies())
                .isNotEmpty());
    }

    @Test
    void bookPhone() throws IOException {
        mockGet(fromPath("/api/get-devices?key={appKey}&manufacturer={manufacturer}&name={model}&limit=1")
                .buildAndExpand("5b882ff0382e", "Samsung", "Galaxy%20S9")
                .getPath()
            , "/it/samsung-s9-response.json");

        webTestClient
            .post()
            .uri(uriBuilder -> uriBuilder
                .path(PATH_BOOK)
                .build())
            .body(Mono.just(BookedPhoneRequestPayload.builder()
                .personCorpKey(USER_CORP_KEY)
                .serialNumber("a")
                .build()), BookedPhoneRequestPayload.class)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(BookedPhoneResponsePayload.class)
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getManufacturer())
                .isEqualTo("Samsung"))
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getModel())
                .isEqualTo("Galaxy S9"))
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getTechnologies())
                .isNotEmpty());
    }

    @Test
    void returnPhone() throws IOException {
        mockGet(fromPath("/api/get-devices?key={appKey}&manufacturer={manufacturer}&name={model}&limit=1")
                .buildAndExpand("5b882ff0382e", "Samsung", "Galaxy%20S9")
                .getPath()
            , "/it/samsung-s9-response.json");

        webTestClient
            .post()
            .uri(uriBuilder -> uriBuilder
                .path(PATH_BOOK)
                .build())
            .body(Mono.just(BookedPhoneRequestPayload.builder()
                .personCorpKey(USER_CORP_KEY)
                .serialNumber("a")
                .build()), BookedPhoneRequestPayload.class)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(BookedPhoneResponsePayload.class)
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getManufacturer())
                .isEqualTo("Samsung"))
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getModel())
                .isEqualTo("Galaxy S9"))
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getTechnologies())
                .isNotEmpty());

        webTestClient
            .post()
            .uri(uriBuilder -> uriBuilder
                .path(PATH_RETURN)
                .build())
            .body(Mono.just(BookedPhoneRequestPayload.builder()
                .personCorpKey(USER_CORP_KEY)
                .serialNumber("a")
                .build()), BookedPhoneRequestPayload.class)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(BookedPhoneResponsePayload.class)
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getManufacturer())
                .isEqualTo("Samsung"))
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getModel())
                .isEqualTo("Galaxy S9"))
            .value(bookedPhone -> assertThat(bookedPhone.get(0).getTechnologies())
                .isNotEmpty());
    }
}
