package com.example.zaid_alam_individual_project2;

import java.util.ArrayList;

public class Orders {

    String userName,userAddress,userEmail,userPhone,userUID,paymentOption,cardNumber,cardExpiry,cardCVV;
    Double totalCost;
    ArrayList<AddToCartItems> addedItems;

    public Orders() {
    }

    public Orders(String userName, String userAddress, String userEmail, String userPhone, String userUID, String paymentOption, String cardNumber, String cardExpiry, String cardCVV, Double totalCost, ArrayList<AddToCartItems> addedItems) {
        this.userName = userName;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userUID = userUID;
        this.paymentOption = paymentOption;
        this.cardNumber = cardNumber;
        this.cardExpiry = cardExpiry;
        this.cardCVV = cardCVV;
        this.totalCost = totalCost;
        this.addedItems = addedItems;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public ArrayList<AddToCartItems> getAddedItems() {
        return addedItems;
    }

    public void setAddedItems(ArrayList<AddToCartItems> addedItems) {
        this.addedItems = addedItems;
    }
}
