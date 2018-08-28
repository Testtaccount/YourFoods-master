package com.trannguyentanthuan2903.yourfoods.history_ship_store.model;

import com.trannguyentanthuan2903.yourfoods.product.model.OrderProduct;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */


public class HistoryShipStore {
    private String idHistoryStore;
    private String idUser;
    private String userName;
    private String linkPhotoUser;
    private String phoneNumber;
    private String address;
    private String timeOrder;
    private ArrayList<OrderProduct> arrProduct;
    private int statusOrder; // 0 : Đang chờ // 1: Đã giao //2 : Bị hủy
    //contructor
    public HistoryShipStore() {
    }

    public HistoryShipStore(String idHistoryStore, String idUser, String userName, String linkPhotoUser, String phoneNumber, String address, String timeOrder, ArrayList<OrderProduct> arrProduct, int statusOrder) {
        this.idHistoryStore = idHistoryStore;
        this.idUser = idUser;
        this.userName = userName;
        this.linkPhotoUser = linkPhotoUser;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.timeOrder = timeOrder;
        this.arrProduct = arrProduct;
        this.statusOrder = statusOrder;
    }

    //put all to HashMap
    public HashMap<String, Object> putMap (){
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_HISTORY_SHIP_STORE, idHistoryStore);
        myMap.put(Constain.ID_USER, idUser);
        myMap.put(Constain.USER_NAME, userName);
        myMap.put(Constain.LINKPHOTOUSER, linkPhotoUser);
        myMap.put(Constain.PHONENUMBER, phoneNumber);
        myMap.put(Constain.ADDRESS, address);
        myMap.put(Constain.TIME_ORDER, timeOrder);
        myMap.put(Constain.PRODUCT_LIST, arrProduct);
        myMap.put(Constain.STATUS_PRODUCTS, statusOrder);
        return myMap;
    }
    //Getter and setter

    public String getIdHistoryStore() {
        return idHistoryStore;
    }

    public void setIdHistoryStore(String idHistoryStore) {
        this.idHistoryStore = idHistoryStore;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLinkPhotoUser() {
        return linkPhotoUser;
    }

    public void setLinkPhotoUser(String linkPhotoUser) {
        this.linkPhotoUser = linkPhotoUser;
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
