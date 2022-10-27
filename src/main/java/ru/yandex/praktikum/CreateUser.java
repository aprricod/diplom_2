package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import model.User;

import static io.restassured.RestAssured.given;

public class CreateUser extends StellarRestClient {

    public ValidatableResponse postUserDataWithoutName(String email, String password) {
        User user = new User(email, password);
        return given().spec(baseSpec())
                .and()
                .body(user)
                .when()
                .post("/api/auth/register")
                .then();
    }

    public ValidatableResponse postFullUserData(String name, String email, String password) {
        User user = new User(name, email, password);
        return given().spec(baseSpec())
                .and()
                .body(user)
                .when()
                .post("/api/auth/register")
                .then();
    }
}
