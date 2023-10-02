package com.example.zaid_alam_individual_project2;

public class ProductCategories {

    private String categoryName,categoryImage;
    private int categoryId;

    public ProductCategories() {
    }

    public ProductCategories(String categoryName, String categoryImage, int categoryId) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.categoryId = categoryId;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
