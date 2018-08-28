package com.trannguyentanthuan2903.yourfoods.product.model;

import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Administrator on 10/17/2017.
 */

public class Product implements Serializable {
    private String idProduct;
    private String idCategory;
    private String productName;
    private String linkPhotoProduct;
    private int rating;
    private float price;
    private String infoProduct;
    private boolean status;
    //Contructor
    public Product() {
    }

    public Product(String idProduct, String idCategory, String productName, String linkPhotoProduct, int rating, float price, String infoProduct, boolean status) {
        this.idProduct = idProduct;
        this.idCategory = idCategory;
        this.productName = productName;
        this.linkPhotoProduct = linkPhotoProduct;
        this.rating = rating;
        this.price = price;
        this.infoProduct = infoProduct;
        this.status = status;
    }
    //put a Map and set on FireBase
    public HashMap<String, Object> putMap() {
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_PRODUCT, idProduct);
        myMap.put(Constain.ID_CATEGORY, idCategory);
        myMap.put(Constain.PRODUCT_NAME, productName);
        myMap.put(Constain.LINK_PHOTO_PRODUCT, linkPhotoProduct);
        myMap.put(Constain.RATING, rating);
        myMap.put(Constain.PRICE, price);
        myMap.put(Constain.INFO_PRODUCT, infoProduct);
        myMap.put(Constain.STATUS, status);
        return myMap;
    }
    //Getter and setter

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getInfoProduct() {
        return infoProduct;
    }

    public void setInfoProduct(String infoProduct) {
        this.infoProduct = infoProduct;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
