package org.galegofer.phone.test.it;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.io.IOException;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Testable
class PhoneEntityApplicationIT extends AbstractIT {

    @Test
    void validCityNameSuccessfulResponse() throws IOException {
        mockGet(fromPath("/weather?q={cityName}&appid={apiKey}&units=metric")
                        .buildAndExpand("Cordoba", "testApiKey")
                        .getPath()
                , "/it/response.json");

        RestAssured.given()
                .contentType(JSON)
                .when()
                .get(fromPath("/weather/city/{cityName}")
                        .buildAndExpand("Cordoba")
                        .getPath())
                .then()
                .statusCode(OK.value())
                .body("title", equalTo("Weather"))
                .body("type", equalTo("object"))
                .body("properties.condition.type", equalTo("string"))
                .body("properties.condition.description", equalTo("overcast clouds"))
                .body("properties.condition.type", equalTo("string"))
                .body("properties.temperature.type", equalTo("number"))
                .body("properties.temperature.description", equalTo("21.43"))
                .body("properties.wind_speed.type", equalTo("number"))
                .body("properties.wind_speed.description", equalTo("2.57"))
                .body("properties.wind_speed.minimum", equalTo(0));
    }

    @Test
    void invalidCityNameBadRequestResponse() {
        RestAssured.given()
                .contentType(JSON)
                .when()
                .get(fromPath("/weather/city/{cityName}")
                        .buildAndExpand("111111")
                        .getPath())
                .then()
                .statusCode(BAD_REQUEST.value())
                .body("code", equalTo(BAD_REQUEST.value()))
                .body("message", equalTo("Error while validating input parameters"));
    }

    @Test
    void validNonExistingCityNameNotFoundResponse() throws IOException {
        mockNotFound(fromPath("/weather?q={cityName}&appid={apiKey}&units=metric")
                .buildAndExpand("NonExisting", "testApiKey")
                .getPath());

        RestAssured.given()
                .contentType(JSON)
                .when()
                .get(fromPath("/weather/city/{cityName}")
                        .buildAndExpand("NonExisting")
                        .getPath())
                .then()
                .statusCode(NOT_FOUND.value())
                .body("code", equalTo(NOT_FOUND.value()))
                .body("message", equalTo("Not found"));
    }

    @Test
    void validExistingCityNameWeatherInternalServerErrorResponse() throws IOException {
        mockServerInternalError(fromPath("/weather?q={cityName}&appid={apiKey}&units=metric")
                .buildAndExpand("Cordoba", "testApiKey")
                .getPath());

        RestAssured.given()
                .contentType(JSON)
                .when()
                .get(fromPath("/weather/city/{cityName}")
                        .buildAndExpand("Cordoba")
                        .getPath())
                .then()
                .statusCode(INTERNAL_SERVER_ERROR.value())
                .body("code", equalTo(INTERNAL_SERVER_ERROR.value()))
                .body("message", equalTo("Application error while trying to access to the provided operation"));
    }
}
