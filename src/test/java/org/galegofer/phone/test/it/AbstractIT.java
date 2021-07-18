package org.galegofer.phone.test.it;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.common.io.ByteStreams;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.ClassRule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

abstract class AbstractIT {
    private static final int APPLICATION_PORT = 8080;
    private static final int WIREMOCK_PORT = 8000;

    private static final String APPLICATION_SERVICE_NAME = "application";
    private static final String WIREMOCK_SERVICE_NAME = "wiremock";
    private static final String HTTP = "http";

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

        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = UriComponentsBuilder.newInstance()
                .host("localhost")
                .scheme(HTTP)
                .build()
                .toString();
        RestAssured.port = composer.getServicePort(APPLICATION_SERVICE_NAME, APPLICATION_PORT);
    }

    protected static void mockGet(final String url, final String responseResource) throws IOException {
        WireMock.stubFor(get(urlEqualTo(url))
                .willReturn(aResponse().withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(readResource(responseResource))));
    }

    protected static void mockNotFound(final String url) throws IOException {
        WireMock.stubFor(get(urlEqualTo(url))
                .willReturn(aResponse().withStatus(NOT_FOUND.value())));
    }

    protected static void mockServerInternalError(final String url) throws IOException {
        WireMock.stubFor(get(urlEqualTo(url))
                .willReturn(aResponse().withStatus(INTERNAL_SERVER_ERROR.value())));
    }

    protected static byte[] readResource(String resourcePath) throws IOException {
        try (InputStream resource = new ClassPathResource(resourcePath).getInputStream()) {
            return ByteStreams.toByteArray(resource);
        }
    }
}
