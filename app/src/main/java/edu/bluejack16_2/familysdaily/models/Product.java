package edu.bluejack16_2.familysdaily.models;

import android.os.Bundle;

/**
 * Created by fidel on 13-Jul-17.
 */

public class Product {
    private String productName, productExpiredDate, productPrice;

    public Product(){

    }

    public Product(Bundle product) {
        this.productName = product.getString("productName");
        this.productExpiredDate = product.getString("productExpiredDate");
        this.productPrice = product.getString("productPrice");
    }

    public Bundle toBundle() {
        Bundle product = new Bundle();
        product.putString("productName", productName);
        product.putString("productExpiredDate", productExpiredDate);
        product.putString("productPrice", productPrice);
        return product;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductExpiredDate() {
        return productExpiredDate;
    }

    public void setProductExpiredDate(String productExpiredDate) {
        this.productExpiredDate = productExpiredDate;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

}
