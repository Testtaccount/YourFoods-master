package com.trannguyentanthuan2903.yourfoods.my_cart.model;

import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 10/17/2017.
 */


public class MyCart {
    private String idMyOrder;
    private String categoryName;
    private String idProduct;
    private String productName;
    private String linkPhotoProduct;
    private int count;
    private float price;
    private String timeOrder;

    public MyCart() {
    }

    public MyCart(String idMyOrder, String categoryName, String idProduct, String productName, String linkPhotoProduct, int count, String timeOrder, float price) {
        this.idMyOrder = idMyOrder;
        this.categoryName = categoryName;
        this.idProduct = idProduct;
        this.productName = productName;
        this.linkPhotoProduct = linkPhotoProduct;
        this.count = count;
        this.timeOrder = timeOrder;
        this.price = price;
    }

    //putMap
    public HashMap<String, Object> putMap() {
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_MYORDER, idMyOrder);
        myMap.put(Constain.CATEGORY_NAME, categoryName);
        myMap.put(Constain.ID_PRODUCT, idProduct);
        myMap.put(Constain.PRODUCT_NAME, productName);
        myMap.put(Constain.LINK_PHOTO_PRODUCT, linkPhotoProduct);
        myMap.put(Constain.COUNT, count);
        myMap.put(Constain.TIME_ORDER, timeOrder);
        myMap.put(Constain.PRICE, price);
        return myMap;
    }

    //getter setter


    public String getIdMyOrder() {
        return idMyOrder;
    }

    public void setIdMyOrder(String idMyOrder) {
        this.idMyOrder = idMyOrder;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLinkPhotoProduct() {
        return linkPhotoProduct;
    }

    public void setLinkPhotoProduct(String linkPhotoProduct) {
        this.linkPhotoProduct = linkPhotoProduct;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
