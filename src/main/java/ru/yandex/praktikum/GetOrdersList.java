package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class GetOrdersList extends StellarRestClient {
    public ValidatableResponse getOrdersWithAuth(String tokenValue) {
        return given().spec(baseSpec())
                .header("authorization", "bearer " + tokenValue)
                .when()
                .get("/api/orders")
                .then();
    }

    public ValidatableResponse getOrdersWithoutAuth() {
        return given().spec(baseSpec())
                .when()
                .get("/api/orders")
                .then();
    }

}
