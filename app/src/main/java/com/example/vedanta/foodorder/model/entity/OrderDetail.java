package com.example.vedanta.foodorder.model.entity;

//helper class to temporarily place each food order and quantity
public class OrderDetail {
    private String food;
    private int price;
    private int quantity;

    public OrderDetail(String food, int price, int quantity) {
        this.food = food;
        this.price = price;
        this.quantity = quantity;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
