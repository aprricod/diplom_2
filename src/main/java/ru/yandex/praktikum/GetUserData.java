package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import model.User;

import static io.restassured.RestAssured.given;

public class GetUserData extends StellarRestClient {
    public ValidatableResponse getUserData(String tokenValue) {
        User user = new User();
        return given().spec(baseSpec())
                .and()
                .header("authorization", "bearer " + tokenValue)
                .and()
                .body(user)
                .when()
                .get("/api/auth/user")
                .then();
    }
}
