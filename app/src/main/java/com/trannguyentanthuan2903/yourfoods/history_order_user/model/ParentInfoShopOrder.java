package com.trannguyentanthuan2903.yourfoods.history_order_user.model;

/**
 * Created by Administrator on 10/16/2017.
 */

public class ParentInfoShopOrder {

    private String nameShop;
    private String linkPhotoShop;
    private String phoneNumberShop;
    private String addressShop;
    private String timeOrderShop;
    private int statusShop;

    public ParentInfoShopOrder(String nameShop, String linkPhotoShop, String phoneNumberShop, String addressShop, String timeOrderShop, int statusShop) {
        this.nameShop = nameShop;
        this.linkPhotoShop = linkPhotoShop;
        this.phoneNumberShop = phoneNumberShop;
        this.addressShop = addressShop;
        this.timeOrderShop = timeOrderShop;
        this.statusShop = statusShop;
    }

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public String getLinkPhotoShop() {
        return linkPhotoShop;
    }

    public void setLinkPhotoShop(String linkPhotoShop) {
        this.linkPhotoShop = linkPhotoShop;
    }

    public String getPhoneNumberShop() {
        return phoneNumberShop;
    }

    public void setPhoneNumberShop(String phoneNumberShop) {
        this.phoneNumberShop = phoneNumberShop;
    }

    public String getAddressShop() {
        return addressShop;
    }

    public void setAddressShop(String addressShop) {
        this.addressShop = addressShop;
    }

    public String getTimeOrderShop() {
        return timeOrderShop;
    }

    public void setTimeOrderShop(String timeOrderShop) {
        this.timeOrderShop = timeOrderShop;
    }

    public int getStatusShop() {
        return statusShop;
    }

    public void setStatusShop(int statusShop) {
        this.statusShop = statusShop;
    }
}
