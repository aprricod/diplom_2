package tests;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.yandex.praktikum.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersListTest {
    CreateUser createUser = new CreateUser();
    DeleteUser deleteUser = new DeleteUser();
    CreateOrder createOrder = new CreateOrder();
    GetOrdersList getOrdersList = new GetOrdersList();

    String userName = RandomStringUtils.randomAlphabetic(6);
    String userEmail = RandomStringUtils.randomAlphanumeric(10) + "@mail.com";
    String userPassword = RandomStringUtils.randomAlphabetic(6);
    String tokenFull;

    String validIngredients = "{\"ingredients\" : [\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa72\"]}";

    @Test
    public void getOrdersListWithAuth() {
        ValidatableResponse create = createUser.postFullUserData(userName, userEmail, userPassword);
        tokenFull = create.extract().body().path("accessToken");
        String tokenValue = tokenFull.substring(7);

        create.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("accessToken", notNullValue());


        for (int i = 0; i < 5; i++) {
            ValidatableResponse placeOrder = createOrder.createOrderWithAuthWithIngredients(tokenValue, validIngredients);
            placeOrder.assertThat()
                    .statusCode(200)
                    .and()
                    .body("success", equalTo(true));
        }


        ValidatableResponse getOrders = getOrdersList.getOrdersWithAuth(tokenValue);
        getOrders.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));

        ValidatableResponse delete = deleteUser.deleteUser(tokenValue);
        delete.assertThat()
                .statusCode(202)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    public void getOrdersListWithoutAuth() {
        ValidatableResponse getOrders = getOrdersList.getOrdersWithoutAuth();
        getOrders.assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}
