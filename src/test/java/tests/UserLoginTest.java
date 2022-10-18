package tests;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.AuthUser;
import ru.yandex.praktikum.CreateUser;
import ru.yandex.praktikum.DeleteUser;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest {

    AuthUser authUser = new AuthUser();
    CreateUser createUser = new CreateUser();
    DeleteUser deleteUser = new DeleteUser();

    String userName = RandomStringUtils.randomAlphabetic(6);
    String userEmail = RandomStringUtils.randomAlphanumeric(10) + "@mail.com";
    String userPassword = RandomStringUtils.randomAlphabetic(6);

    String tokenFull;
    String tokenValue;

    //создания нового юзера
    @Before
    public void createNewUser() {
        ValidatableResponse create = createUser.postFullUserData(userName, userEmail, userPassword);
        create.assertThat()
                .body("success", equalTo(true))
                .and().statusCode(200);

        tokenFull = create.extract().body().path("accessToken");
        tokenValue = tokenFull.substring(7);
    }


    @Test
    public void successfulLoginTest() {
        ValidatableResponse auth = authUser.authUserWithFullData(userEmail, userPassword);
        auth.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    public void unSuccessfulLoginTest() {
        ValidatableResponse auth = authUser.authUserWithoutPassword(userEmail);
        auth.assertThat().body("success", equalTo(false)).and().statusCode(401);
    }

    //удаление юзера
    @After
    public void deleteUserTest() {
        ValidatableResponse delete = deleteUser.deleteUser(tokenValue);
        delete.assertThat()
                .body("success", equalTo(true))
                .and().statusCode(202);
    }
}
