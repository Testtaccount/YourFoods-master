package com.trannguyentanthuan2903.yourfoods.product.model;

/**
 * Created by Administrator on 10/16/2017.
 */

public class OrderProduct {
    private String  productName;
    private int count;
    private float price;

    public OrderProduct() {
    }

    public OrderProduct(String productName, int count, float price) {
        this.productName = productName;
        this.count = count;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
