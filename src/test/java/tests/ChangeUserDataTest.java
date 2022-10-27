package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.CreateUser;
import ru.yandex.praktikum.DeleteUser;
import ru.yandex.praktikum.PatchUserData;

import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserDataTest {

    CreateUser createUser = new CreateUser();
    DeleteUser deleteUser = new DeleteUser();
    PatchUserData patchUserData = new PatchUserData();

    String userName;
    String userEmail;
    String userPassword;
    String tokenFull;
    String tokenValue;
    String requestBody = "{\"email\" : \"patch999@yopmail.com\", \"name\" : \"PatchName\"}";

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

    @Test
    @DisplayName("Change user")
    @Description("Successful user data changing with authentication")
    @Step("Change user")
    public void patchUserDataWithAuthTest() {
        ValidatableResponse changeData = patchUserData.patchDataWithAuth(tokenValue, requestBody);
        changeData.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.email", equalTo("patch999@yopmail.com"))
                .body("user.name", equalTo("PatchName"));
    }

    @Test
    @DisplayName("Change user")
    @Description("Unsuccessful user data changing without authentication")
    @Step("Change user")
    public void patchUserDataWithoutAuthTest() {
        ValidatableResponse changeData = patchUserData.patchDataWithOutAuth(requestBody);
        changeData.assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
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