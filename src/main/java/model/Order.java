package model;

public class Order {

    private String ingredients;

    private Order() {
    }

    private Order(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
