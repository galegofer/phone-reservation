package org.galegofer.phone.test.repository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.galegofer.phone.test.domain.model.payload.Root;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class DeviceInformationRepository {

    WebClient http;

    @Cacheable("devices")
    public Mono<Root> getDeviceInformation(String manufactureName, String modelName) {
        return http.get()
                .uri("/get-devices?key=5b882ff0382e&manufacturer={manufacturer}&name={model}&limit=1", manufactureName, modelName)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Root.class);
    }
}
