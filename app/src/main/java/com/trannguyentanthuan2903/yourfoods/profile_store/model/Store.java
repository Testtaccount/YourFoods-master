package com.trannguyentanthuan2903.yourfoods.profile_store.model;

import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */

public class Store {
    private String idStore;
    private String storeName;
    private String email;
    private boolean isStore;
    private int isOpen; //0 mở của 1 đóng cửa
    private String phoneNumber;
    private String linkPhotoStore;
    private String linkCoverStore;
    private int sumShipped;
    private int sumOrdered;
    private int sumProduct;
    private String timeWork;
    private HashMap<String, Object> location;
    private HashMap<String, Object> favoriteList;
    private HashMap<String, Object> products;
    private HashMap<String, Object> orderSchedule;
    private HashMap<String, Object> comment;
    private HashMap<String, Object> rate;

    //Contructor
    public Store() {
    }

    public Store(String idStore, String storeName, String email, boolean isStore, int isOpen, String phoneNumber, String linkPhotoStore,
                 String linkCoverStore, int sumShipped, int sumOrdered, int sumProduct, String timeWork, HashMap<String, Object> location,
                 HashMap<String, Object> favorite_list, HashMap<String, Object> products, HashMap<String, Object> orderSchedule,
                 HashMap<String, Object> comment,HashMap<String, Object> rate) {
        this.idStore = idStore;
        this.storeName = storeName;
        this.email = email;
        this.isStore = isStore;
        this.isOpen = isOpen;
        this.phoneNumber = phoneNumber;
        this.linkPhotoStore = linkPhotoStore;
        this.linkCoverStore = linkCoverStore;
        this.sumShipped = sumShipped;
        this.sumOrdered = sumOrdered;
        this.sumProduct = sumProduct;
        this.timeWork = timeWork;
        this.location = location;
        this.favoriteList = favorite_list;
        this.products = products;
        this.orderSchedule = orderSchedule;
        this.comment = comment;
        this.rate = rate;
    }

    //put everything a HashMap
    public HashMap<String, Object> putMap() {
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_STORE, idStore);
        myMap.put(Constain.STORE_NAME, storeName);
        myMap.put(Constain.EMAIL, email);
        myMap.put(Constain.IS_STORE, isStore);
        myMap.put(Constain.IS_OPEN, isOpen);
        myMap.put(Constain.PHONENUMBER, phoneNumber);
        myMap.put(Constain.LINKPHOTOSTORE, linkPhotoStore);
        myMap.put(Constain.LINKCOVERSTORE, linkCoverStore);
        myMap.put(Constain.SUM_SHIPPED, sumShipped);
        myMap.put(Constain.SUM_ORDERED, sumOrdered);
        myMap.put(Constain.SUM_PRODUCT, sumProduct);
        myMap.put(Constain.TIME_WORK, timeWork);
        myMap.put(Constain.LOCATION, location);
        myMap.put(Constain.FAVORITE_LIST, favoriteList);
        myMap.put(Constain.PRODUCTS, products);
        myMap.put(Constain.ORDER_SCHEDULE, orderSchedule);
        myMap.put(Constain.COMMENT, comment);
        myMap.put(Constain.RATE, rate);
        return myMap;
    }
    //getter and setter

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStore() {
        return isStore;
    }

    public void setStore(boolean store) {
        isStore = store;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLinkPhotoStore() {
        return linkPhotoStore;
    }

    public void setLinkPhotoStore(String linkPhotoStore) {
        this.linkPhotoStore = linkPhotoStore;
    }

    public String getLinkCoverStore() {
        return linkCoverStore;
    }

    public void setLinkCoverStore(String linkCoverStore) {
        this.linkCoverStore = linkCoverStore;
    }

    public int getSumShipped() {
        return sumShipped;
    }

    public void setSumShipped(int sumShipped) {
        this.sumShipped = sumShipped;
    }

    public int getSumOrdered() {
        return sumOrdered;
    }

    public void setSumOrdered(int sumOrdered) {
        this.sumOrdered = sumOrdered;
    }

    public int getSumProduct() {
        return sumProduct;
    }

    public void setSumProduct(int sumProduct) {
        this.sumProduct = sumProduct;
    }

    public String getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(String timeWork) {
        this.timeWork = timeWork;
    }

    public HashMap<String, Object> getLocation() {
        return location;
    }

    public void setLocation(HashMap<String, Object> location) {
        this.location = location;
    }

    public HashMap<String, Object> getFavoriteList() {
        return favoriteList;
    }

    public HashMap<String, Object> getComment() {
        return comment;
    }

    public void setComment(HashMap<String, Object> comment) {
        this.comment = comment;
    }

    public void setFavoriteList(HashMap<String, Object> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public HashMap<String, Object> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Object> products) {
        this.products = products;
    }

    public HashMap<String, Object> getOrderSchedule() {
        return orderSchedule;
    }

    public void setOrderSchedule(HashMap<String, Object> orderSchedule) {
        this.orderSchedule = orderSchedule;
    }

    public HashMap<String, Object> getRate() {
        return rate;
    }

    public void setRate(HashMap<String, Object> rate) {
        this.rate = rate;
    }
}
