package tests;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.yandex.praktikum.AuthUser;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest {

    AuthUser authUser = new AuthUser();

//    String userEmail = "stellar.test15@yopmail.com";
//    String userPassword = "123456";

    String userEmail = RandomStringUtils.randomAlphanumeric(10) + "@mail.com";
    String userPassword = RandomStringUtils.randomAlphabetic(6);




    @Test
    public void successfulLoginTest() {
        ValidatableResponse auth = authUser.authUserWithFullData(userEmail, userPassword);
        auth.assertThat().body("user.email", equalTo(userEmail)).and().statusCode(200);
    }

    @Test
    public void unSuccessfulLoginTest() {
        ValidatableResponse auth = authUser.authUserWithoutPassword(userEmail);
        auth.assertThat().body("success", equalTo(false)).and().statusCode(401);
    }
}
