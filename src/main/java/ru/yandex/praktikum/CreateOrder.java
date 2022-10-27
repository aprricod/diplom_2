package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class CreateOrder extends StellarRestClient {
    // заказ с авторизацией, с ингредиентами
    public ValidatableResponse createOrderWithAuthWithIngredients(String tokenValue, String ingredients) {
        return given().spec(baseSpec())
                .header("authorization", "bearer " + tokenValue)
                .and()
                .body(ingredients)
                .when()
                .post("/api/orders")
                .then();
    }

    // заказ без авторизации, с ингредиентами
    public ValidatableResponse createOrderWithoutAuthWithIngredients(String ingredients) {
        return given().spec(baseSpec())
                .and()
                .body(ingredients)
                .when()
                .post("/api/orders")
                .then();
    }

    // заказ без авторизации, без ингредиентов
    public ValidatableResponse createOrderWithoutAuthWithoutIngredients() {
        return given().spec(baseSpec())
                .when()
                .post("/api/orders")
                .then();
    }
}
