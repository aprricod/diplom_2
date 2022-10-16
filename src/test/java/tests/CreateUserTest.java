package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.CreateUser;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {

    CreateUser createUser = new CreateUser();

    String userName = "test";
    String userEmail = "stellar.test5@yopmail.com";
    String userPassword = "123456";

    @Test
    @DisplayName("Create new user")
    @Description("Successful create new user with unique data")
    public void createNewUser() {
        ValidatableResponse create = createUser.postFullUserData(userName, userEmail, userPassword);
        create.assertThat()
                .body("success", equalTo(true))
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Create new user with non-unique data")
    @Description("Unsuccessful create user with non-unique data")
    public void createUserWithNonUniqueData() {
        ValidatableResponse create = createUser.postFullUserData(userName, userEmail, userPassword);
        create.assertThat()
                .body("message", equalTo("User already exists"))
                .and().statusCode(403);
    }

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
