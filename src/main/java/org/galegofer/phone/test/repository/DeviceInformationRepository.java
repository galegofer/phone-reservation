package org.galegofer.phone.test.repository;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.galegofer.phone.test.config.CacheClient;
import org.galegofer.phone.test.domain.model.payload.Root;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class DeviceInformationRepository {

    WebClient http;
    CacheClient cacheClient;

    public Mono<Root> getDeviceInformation(String manufactureName, String modelName) {
        log.info("Getting information for device: {}, model: {}...", manufactureName, modelName);

        return cacheClient.get(normalizeKey(manufactureName, modelName))
            .map(DeviceInformationRepository::retrieveFromCache)
            .orElseGet(() -> getDeviceInformationFromWebService(manufactureName, modelName));
    }

    private Mono<Root> getDeviceInformationFromWebService(String manufactureName, String modelName) {
        return http.get()
            .uri("/api/get-devices?key={appKey}&manufacturer={manufacturer}&name={model}&limit=1", "5b882ff0382e",
                manufactureName,
                modelName)
            .accept(APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Root.class)
            .doOnSuccess(result -> log
                .info("Successfully retrieved information for device: {}, model: {} from web service", manufactureName,
                    modelName))
            .doOnSuccess(result -> cacheClient.put(normalizeKey(manufactureName, modelName), result))
            .doOnError(ex -> log
                .error("Error while obtaining extra information for device: {}, model: {} from web service", manufactureName, modelName,
                    ex));
    }

    private static Mono<Root> retrieveFromCache(Root root) {
        log.info("Successfully retrieved device information from cache");
        return Mono.just(root);
    }

    private static String normalizeKey(String manufactureName, String modelName) {
        return (manufactureName + modelName).toLowerCase().replaceAll(" ", "");
    }
}
