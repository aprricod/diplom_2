package tests;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.CreateUser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateUserTest {

    CreateUser createUser = new CreateUser();

    String userName = "test";
    String userEmail = "stellar.test3@yopmail.com";
    String userPassword = "123456";

    @Test
    public void createNewUser(){
        ValidatableResponse create = createUser.postFullUserData(userName, userEmail, userPassword);
        create.assertThat()
                .body("success", equalTo(true))
                .and().statusCode(200);
    }
}
