package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.AuthUser;
import ru.yandex.praktikum.CreateUser;
import ru.yandex.praktikum.DeleteUser;
import ru.yandex.praktikum.GetUserData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserDataTest {

    CreateUser createUser = new CreateUser();
    GetUserData getUserData = new GetUserData();
    DeleteUser deleteUser = new DeleteUser();
    AuthUser authUser = new AuthUser();

    String userName;
    String userEmail;
    String userPassword;
    String tokenFull;
    String tokenValue;

    String requestBody = "{\"email\" : \"patch999@yopmail.com\", \"name\" : \"PatchName\"}";

    String log = "test999@yopmail.com";
    String pass = "123456";

    @Before
    @Step("before")
    public void createNewUser() {
        userName = RandomStringUtils.randomAlphabetic(6);
        userEmail = RandomStringUtils.randomAlphanumeric(10) + "@mail.com";
        userPassword = RandomStringUtils.randomAlphabetic(6);
        ValidatableResponse create = createUser.postFullUserData(userName, userEmail, userPassword);
        tokenFull = create.extract().body().path("accessToken");
        tokenValue = tokenFull.substring(7);
        create.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

//    public void successfulLoginTest() {
//        ValidatableResponse auth = authUser.authUserWithFullData(log, pass);
//        tokenFull = auth.extract().body().path("accessToken");
//        tokenValue = tokenFull.substring(7);
//        auth.assertThat()
//                .statusCode(200)
//                .and()
//                .body("success", equalTo(true));
//    }

//    @Test
//    public void getUserDataTest() {
//        ValidatableResponse receiveData = getUserData.getUserData(tokenValue);
//        receiveData.assertThat()
//                .statusCode(200)
//                .and()
//                .body("success", equalTo(true));
//        String info = receiveData.extract().body().path("user.email");
//        System.out.println(info);
//    }

    @Test
    @DisplayName("Change user")
    @Description("Successful user data changing with authentication")
    @Step("Change user")
    public void patchUserDataWithAuthTest() {
        ValidatableResponse response = given()
                .header("authorization", "bearer " + tokenValue)
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .patch("https://stellarburgers.nomoreparties.site/api/auth/user")
                .then();

        String email = response.extract().body().path("user.email");
        String name = response.extract().body().path("user.name");

        response.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.email", equalTo("patch999@yopmail.com"))
                .body("user.name", equalTo("PatchName"));

        System.out.println(userName);
        System.out.println(userEmail);
        System.out.println(userPassword);
        System.out.println(requestBody);
        System.out.println(email);
        System.out.println(name);
    }

    @Test
    @DisplayName("Change user")
    @Description("Unsuccessful user data changing without authentication")
    @Step("Change user")
    public void patchUserDataWithoutAuthTest() {
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .patch("https://stellarburgers.nomoreparties.site/api/auth/user")
                .then();

        String email = response.extract().body().path("message");

        response.assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));

        System.out.println(userName);
        System.out.println(userEmail);
        System.out.println(userPassword);
        System.out.println(requestBody);
        System.out.println(email);
    }

    @After
    @Step("after")
    public void deleteUserTest() {
        ValidatableResponse delete = deleteUser.deleteUser(tokenValue);
        delete.assertThat()
                .statusCode(202)
                .and()
                .body("success", equalTo(true));
    }
}