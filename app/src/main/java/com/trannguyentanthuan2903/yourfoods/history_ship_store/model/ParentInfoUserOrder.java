package com.trannguyentanthuan2903.yourfoods.history_ship_store.model;

/**
 * Created by Administrator on 10/16/2017.
 */


public class ParentInfoUserOrder {
    private String nameUser;
    private String idUser;
    private String idHistoryShip;
    private String linkPhotoUser;
    private String phoneNumberUser;
    private String addressUser;
    private String timeOrder;
    public int getStatus() {
        return statusOrder;
    }

    public void setStatus(int statusOrder) {
        this.statusOrder = statusOrder;
    }

    private int statusOrder;

    public String getNameUser() {
        return nameUser;
    }

    public ParentInfoUserOrder(String nameUser, String idUser, String idHistoryShip, String linkPhotoUser, String phoneNumberUser, String addressUser, String timeOrder, int statusOrder) {
        this.nameUser = nameUser;
        this.idUser = idUser;
        this.idHistoryShip = idHistoryShip;
        this.linkPhotoUser = linkPhotoUser;
        this.phoneNumberUser = phoneNumberUser;
        this.addressUser = addressUser;
        this.timeOrder = timeOrder;
        this.statusOrder = statusOrder;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getLinkPhotoUser() {
        return linkPhotoUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdHistoryShip() {
        return idHistoryShip;
    }

    public void setIdHistoryShip(String idHistoryShip) {
        this.idHistoryShip = idHistoryShip;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setLinkPhotoUser(String linkPhotoUser) {
        this.linkPhotoUser = linkPhotoUser;
    }

    public String getPhoneNumberUser() {
        return phoneNumberUser;
    }

    public void setPhoneNumberUser(String phoneNumberUser) {
        this.phoneNumberUser = phoneNumberUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }
}
