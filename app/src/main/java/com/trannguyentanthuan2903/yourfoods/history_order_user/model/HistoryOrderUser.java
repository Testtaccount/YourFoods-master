package com.trannguyentanthuan2903.yourfoods.history_order_user.model;

import com.trannguyentanthuan2903.yourfoods.product.model.OrderProduct;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */
public class HistoryOrderUser {
    private String idHistoryUser;
    private String idStore;
    private String storeName;
    private String linkPhotoStore;
    private String phoneNumber;
    private String address;
    private String timeOrder;
    private ArrayList<OrderProduct> arrProduct;
    private int statusOrder; // 0 : Đang chờ // 1: Đã giao //2 : Bị hủy
    //contructor
    public HistoryOrderUser() {
    }

    public HistoryOrderUser(String idHistoryUser, String idStore, String storeName, String linkPhotoStore, String phoneNumber, String address, String timeOrder, ArrayList<OrderProduct> arrProduct, int statusOrder) {
        this.idHistoryUser = idHistoryUser;
        this.idStore = idStore;
        this.storeName = storeName;
        this.linkPhotoStore = linkPhotoStore;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.timeOrder = timeOrder;
        this.arrProduct = arrProduct;
        this.statusOrder = statusOrder;
    }

    //put all to HashMap
    public HashMap<String, Object> putMap (){
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_HISTORY_ORDER_USER, idHistoryUser);
        myMap.put(Constain.ID_USER, idStore);
        myMap.put(Constain.USER_NAME, storeName);
        myMap.put(Constain.LINKPHOTOUSER, linkPhotoStore);
        myMap.put(Constain.PHONENUMBER, phoneNumber);
        myMap.put(Constain.ADDRESS, address);
        myMap.put(Constain.TIME_ORDER, timeOrder);
        myMap.put(Constain.PRODUCT_LIST, arrProduct);
        myMap.put(Constain.STATUS_PRODUCTS, statusOrder);
        return myMap;
    }
    //Getter and setter


    public String getIdHistoryUser() {
        return idHistoryUser;
    }

    public void setIdHistoryUser(String idHistoryUser) {
        this.idHistoryUser = idHistoryUser;
    }

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

    public String getLinkPhotoStore() {
        return linkPhotoStore;
    }

    public void setLinkPhotoStore(String linkPhotoStore) {
        this.linkPhotoStore = linkPhotoStore;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public ArrayList<OrderProduct> getArrProduct() {
        return arrProduct;
    }

    public void setArrProduct(ArrayList<OrderProduct> arrProduct) {
        this.arrProduct = arrProduct;
    }

    public int getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(int statusOrder) {
        this.statusOrder = statusOrder;
    }
}
