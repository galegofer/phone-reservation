package org.galegofer.phone.test.it;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import org.junit.ClassRule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import reactor.netty.http.client.HttpClient;

abstract class AbstractIT {

    private static final int APPLICATION_PORT = 8080;
    private static final int WIREMOCK_PORT = 8000;

    private static final String APPLICATION_SERVICE_NAME = "application";
    private static final String WIREMOCK_SERVICE_NAME = "wiremock";
    private static final String HTTP = "http";

    protected static final WebTestClient webTestClient;

    @ClassRule
    public static final DockerComposeContainer composer =
        new DockerComposeContainer<>(new File("src/test/resources/compose-it.yml"))
            .withExposedService(APPLICATION_SERVICE_NAME, APPLICATION_PORT,
                Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
            .withExposedService(WIREMOCK_SERVICE_NAME, WIREMOCK_PORT,
                Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(90)))
            .withLocalCompose(true);

    static {
        composer.start();

        WireMock.configureFor(HTTP, composer.getServiceHost(WIREMOCK_SERVICE_NAME, WIREMOCK_PORT), WIREMOCK_PORT);

        webTestClient = configureHttpsConnector();
    }

    private static WebTestClient configureHttpsConnector() {
        return WebTestClient.bindToServer(new ReactorClientHttpConnector(HttpClient.create()))
            .baseUrl("http://localhost:" + composer.getServicePort(APPLICATION_SERVICE_NAME, APPLICATION_PORT))
            .responseTimeout(Duration.ofMillis(6_000))
            .build();
    }

    protected static void mockGet(final String url, final String responseResource) throws IOException {
        WireMock.stubFor(get(urlEqualTo(url))
            .willReturn(aResponse().withHeader("Content-Type", APPLICATION_JSON_VALUE)
                .withBody(readResource(responseResource))));
    }

    protected static void mockNotFound(final String url) {
        WireMock.stubFor(get(urlEqualTo(url))
            .willReturn(aResponse().withStatus(NOT_FOUND.value())));
    }

    protected static void mockServerInternalError(final String url) {
        WireMock.stubFor(get(urlEqualTo(url))
            .willReturn(aResponse().withStatus(INTERNAL_SERVER_ERROR.value())));
    }

    protected static byte[] readResource(String resourcePath) throws IOException {
        try (InputStream resource = new ClassPathResource(resourcePath).getInputStream()) {
            return ByteStreams.toByteArray(resource);
        }
    }
}
