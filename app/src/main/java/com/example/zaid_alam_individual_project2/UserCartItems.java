package com.example.zaid_alam_individual_project2;

import java.util.ArrayList;

public class UserCartItems {

    private ArrayList<AddToCartItems> addedItems;
    private String uid;


    public UserCartItems() {
    }

    public ArrayList<AddToCartItems> getAddedItems() {
        return addedItems;
    }

    public void setAddedItems(ArrayList<AddToCartItems> addedItems) {
        this.addedItems = addedItems;
    }

    public UserCartItems(ArrayList<AddToCartItems> addedItems, String uid) {
        this.addedItems = addedItems;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
