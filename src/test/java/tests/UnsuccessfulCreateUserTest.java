package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.yandex.praktikum.CreateUser;

import static org.hamcrest.CoreMatchers.equalTo;

public class UnsuccessfulCreateUserTest {

    CreateUser createUser = new CreateUser();

    String userEmail = RandomStringUtils.randomAlphanumeric(10) + "@mail.com";
    String userPassword = RandomStringUtils.randomAlphabetic(6);

    //проверка неуспешного создания юзера без указания имени
    @Test
    @DisplayName("Create new user without name")
    @Description("Unsuccessful create new user without all required data")
    @Step("Create user")
    public void createUserWithoutName() {
        ValidatableResponse create = createUser.postUserDataWithoutName(userEmail, userPassword);
        create.assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
