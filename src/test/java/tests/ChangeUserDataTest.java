package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
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

    String userName = RandomStringUtils.randomAlphabetic(6);
    String userEmail = RandomStringUtils.randomAlphanumeric(10) + "@mail.com";
    String userPassword = RandomStringUtils.randomAlphabetic(6);
    String tokenFull;
    String tokenValue;

    //    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzNGRiYjYxYzE2MWRhMDAxYjc3YmE3MyIsImlhdCI6MTY2NjM4NjIyNiwiZXhwIjoxNjY2Mzg3NDI2fQ.gMQqf61zOvM0qM8IgxM1g_egkIzElUUWlE4hE3O1L7Y";
    String requestBody = "{\"email\" : \"test99999@yopmail.com\", \"name\" : \"restname\"}";

    String log = "test999@yopmail.com";
    String pass = "123456";

    @Before
//    public void createNewUser() {
//        ValidatableResponse create = createUser.postFullUserData(userName, userEmail, userPassword);
//        tokenFull = create.extract().body().path("accessToken");
//        tokenValue = tokenFull.substring(7);
//
//        create.assertThat()
//                .statusCode(200)
//                .and()
//                .body("success", equalTo(true));
//    }

    public void successfulLoginTest() {
        ValidatableResponse auth = authUser.authUserWithFullData(log, pass);
        tokenFull = auth.extract().body().path("accessToken");
        tokenValue = tokenFull.substring(7);
        auth.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

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
    public void patchUserDataTest() {
        Response response = given()
                .log().all()
                .header("authorization", "bearer " + tokenValue)
                .and()
                .body(requestBody)
                .when()
                .patch("https://stellarburgers.nomoreparties.site/api/auth/user")
                .then()
                .log().all()
                .extract().response();

        System.out.println(requestBody);

        System.out.println(response.asString());

    }

//    @After
//    public void deleteUserTest() {
//        ValidatableResponse delete = deleteUser.deleteUser(tokenValue);
//        delete.assertThat()
//                .statusCode(202)
//                .and()
//                .body("success", equalTo(true));
//    }
}
