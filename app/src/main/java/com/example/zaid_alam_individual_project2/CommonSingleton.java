package com.example.zaid_alam_individual_project2;

import java.util.ArrayList;

public class CommonSingleton {

    private static CommonSingleton instance;
    private String userName,productName,userEmail,userUID;
    private int cartItem,productQuantity;
    private Double productPrice;
    private  Double totalPrice;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<AddToCartItems> getCartItems() {
        return cartItems;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public void setCartItems(ArrayList<AddToCartItems> cartItems) {
        this.cartItems = cartItems;
    }

    private ArrayList<AddToCartItems> cartItems;

    public CommonSingleton() {
    }

    public static CommonSingleton getInstance() {
        if (instance == null) {
            instance = new CommonSingleton();
        }
        return instance;
    }

    public static void setInstance(CommonSingleton instance) {
        CommonSingleton.instance = instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCartItem() {
        return cartItem;
    }

    public void setCartItem(int cartItem) {
        this.cartItem = cartItem;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
