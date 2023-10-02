package com.example.zaid_alam_individual_project2;

public class AddToCartItems {

    String productName, productImage;
    int productQuantity;
    Double price;

    public AddToCartItems(String productName, String productImage, int productQuantity, Double price) {
        this.productName = productName;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
        this.price = price;
    }

    public AddToCartItems() {
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
