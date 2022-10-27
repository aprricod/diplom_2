package tests;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.CreateOrder;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest {
    CreateOrder createOrder = new CreateOrder();

    String validIngredients = "{\"ingredients\" : [\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa72\"]}";
    String invalidIngredients = "{\"ingredients\" : [\"test1\", \"test2\"]}";
    String token = "";

    // заказ с авторизацией, с ингредиентами
    @Test
    public void createOrderWithAuthWithIngredientsTest() {
        ValidatableResponse placeOrder = createOrder.createOrderWithAuthWithIngredients(token, validIngredients);
        placeOrder.assertThat()
                .log().all()
                .statusCode(200)
                .and()
                .log().all()
                .body("success", equalTo(true));
    }

    // заказ без авторизации, с ингредиентами
    @Test
    public void createOrderWithoutAuthWithIngredientsTest() {
        ValidatableResponse placeOrder = createOrder.createOrderWithoutAuthWithIngredients(validIngredients);
        placeOrder.assertThat()
                .log().all()
                .statusCode(200)
                .and()
                .log().all()
                .body("success", equalTo(true));
    }

    // заказ без авторизации, с неверным хешем ингредиентов
    @Test
    public void createOrderWithoutAuthWithInvalidIngredientsTest() {
        ValidatableResponse placeOrder = createOrder.createOrderWithoutAuthWithIngredients(invalidIngredients);
        placeOrder.assertThat()
                .log().all()
                .statusCode(200)
                .and()
                .log().all()
                .body("success", equalTo(true));
    }

    // заказ без авторизации, без ингредиентов
    @Test
    public void createOrderWithoutAuthWithoutIngredientsTest() {
        ValidatableResponse placeOrder = createOrder.createOrderWithoutAuthWithoutIngredients();
        placeOrder.assertThat()
                .log().all()
                .statusCode(200)
                .and()
                .log().all()
                .body("success", equalTo(true));
    }
}
