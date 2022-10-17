package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.CreateUser;
import ru.yandex.praktikum.DeleteUser;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateNewUserTest {

    CreateUser createUser = new CreateUser();
    DeleteUser deleteUser = new DeleteUser();

    String userName = "test";
    String userEmail = "stellar.test999@yopmail.com";
    String userPassword = "123456";

    String tokenFull;
    String tokenValue;

    //проверка успешного создания юзера
    @Test
    @DisplayName("Create new user")
    @Description("Successful create new user with unique data")
    public void createNewUser() {
        ValidatableResponse create = createUser.postFullUserData(userName, userEmail, userPassword);
        create.assertThat()
                .body("success", equalTo(true))
                .and().statusCode(200);

        tokenFull = create.extract().body().path("accessToken");
        tokenValue = tokenFull.substring(7);
    }

    @After
    public void deleteUserTest() {
        ValidatableResponse delete = deleteUser.deleteUser(tokenValue);
        delete.assertThat()
                .body("success", equalTo(true))
                .and().statusCode(202);
    }
}