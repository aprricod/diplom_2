package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.CreateUser;

import static org.hamcrest.CoreMatchers.equalTo;

public class UnsuccessfulCreateUserTest {

    CreateUser createUser = new CreateUser();

    String userEmail = "stellar.test15@yopmail.com";
    String userPassword = "123456";

    @Test
    @DisplayName("Create new user without name")
    @Description("Unsuccessful create new user without all required data")
    public void createUserWithoutName() {
        ValidatableResponse create = createUser.postUserDataWithoutName(userEmail, userPassword);
        create.assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and().statusCode(403);
    }
}
