package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class DeleteUser extends StellarRestClient {
    public ValidatableResponse deleteUser(String tokenValue) {
        return given().spec(baseSpec())
                .header("authorization", "bearer " + tokenValue)
                .when()
                .delete("/api/auth/user/")
                .then();
    }
}
