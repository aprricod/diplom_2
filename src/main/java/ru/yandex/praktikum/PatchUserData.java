package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class PatchUserData extends StellarRestClient {

    public ValidatableResponse patchDataWithAuth(String tokenValue, String requestBody) {
        return given().spec(baseSpec())
                .header("authorization", "bearer " + tokenValue)
                .and()
                .body(requestBody)
                .when()
                .patch("/api/auth/user/")
                .then();
    }

    public ValidatableResponse patchDataWithOutAuth(String requestBody) {
        return given().spec(baseSpec())
                .body(requestBody)
                .when()
                .patch("/api/auth/user/")
                .then();
    }
}
