package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import model.User;

import static io.restassured.RestAssured.given;

public class AuthUser extends StellarRestClient {

    public ValidatableResponse authUserWithoutPassword(String email) {
        User user = new User(email);
        return given().spec(baseSpec())
                .and()
                .body(user)
                .when()
                .post("/api/auth/login")
                .then();
    }

    public ValidatableResponse authUserWithFullData(String email, String password) {
        User user = new User(email, password);
        return given().spec(baseSpec())
                .and()
                .body(user)
                .when()
                .post("/api/auth/login")
                .then();
    }
}
