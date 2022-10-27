package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.yandex.praktikum.CreateOrder;
import ru.yandex.praktikum.CreateUser;
import ru.yandex.praktikum.DeleteUser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest {
    CreateOrder createOrder = new CreateOrder();
    CreateUser createUser = new CreateUser();
    DeleteUser deleteUser = new DeleteUser();

    String userName = RandomStringUtils.randomAlphabetic(6);
    String userEmail = RandomStringUtils.randomAlphanumeric(10) + "@mail.com";
    String userPassword = RandomStringUtils.randomAlphabetic(6);
    String tokenFull;

    String validIngredients = "{\"ingredients\" : [\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa72\"]}";
    String invalidIngredients = "{\"ingredients\" : [\"test1\", \"test2\"]}";

    // заказ с авторизацией, с ингредиентами
    @Test
    @DisplayName("Create new order")
    @Description("Successful create new order with auth & ingredients")
    @Step("Create order")
    public void createOrderWithAuthWithIngredientsTest() {
        ValidatableResponse create = createUser.postFullUserData(userName, userEmail, userPassword);
        tokenFull = create.extract().body().path("accessToken");
        String tokenValue = tokenFull.substring(7);

        create.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("accessToken", notNullValue());

        ValidatableResponse placeOrder = createOrder.createOrderWithAuthWithIngredients(tokenValue, validIngredients);
        placeOrder.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));

        ValidatableResponse delete = deleteUser.deleteUser(tokenValue);
        delete.assertThat()
                .statusCode(202)
                .and()
                .body("success", equalTo(true));
    }

    // заказ без авторизации, с ингредиентами
    @Test
    @DisplayName("Create new order")
    @Description("Successful create new order without auth, with ingredients")
    @Step("Create order")
    public void createOrderWithoutAuthWithIngredientsTest() {
        ValidatableResponse placeOrder = createOrder.createOrderWithoutAuthWithIngredients(validIngredients);
        placeOrder.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    // заказ без авторизации, с неверным хешем ингредиентов
    @Test
    @DisplayName("Create new order")
    @Description("Unsuccessful create new order without auth, with invalid ingredients hash")
    @Step("Create order")
    public void createOrderWithoutAuthWithInvalidIngredientsTest() {
        ValidatableResponse placeOrder = createOrder.createOrderWithoutAuthWithIngredients(invalidIngredients);
        placeOrder.assertThat()
                .statusCode(500);
    }

    // заказ без авторизации, без ингредиентов
    @Test
    @DisplayName("Create new order")
    @Description("Unsuccessful create new order without auth & ingredients")
    @Step("Create order")
    public void createOrderWithoutAuthWithoutIngredientsTest() {
        ValidatableResponse placeOrder = createOrder.createOrderWithoutAuthWithoutIngredients();
        placeOrder.assertThat()
                .statusCode(400)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }
}
