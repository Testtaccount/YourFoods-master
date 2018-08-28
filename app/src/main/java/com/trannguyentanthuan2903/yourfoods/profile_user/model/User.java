package com.trannguyentanthuan2903.yourfoods.profile_user.model;

import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 10/15/2017.
 */

public class User {
    private String idUser;
    private String userName;
    private String email;
    private boolean gender;
    private String phoneNumber;
    private String linkPhotoUser;
    private String birthDay;
    private boolean isStore;
    private int sumOrdered;
    private int sumShipped;
    private HashMap<String, Object> location;
    private HashMap<String, Object> favorite_drink;

    //contructor
    public User() {
    }

    public User(String idUser, String userName, String email, boolean gender, String phoneNumber, String linkPhotoUser, String birthDay, boolean isStore, int sumOrdered, int sumShipped, HashMap<String, Object> location, HashMap<String, Object> favorite_drink) {
        this.idUser = idUser;
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.linkPhotoUser = linkPhotoUser;
        this.birthDay = birthDay;
        this.isStore = isStore;
        this.sumOrdered = sumOrdered;
        this.sumShipped = sumShipped;
        this.location = location;
        this.favorite_drink = favorite_drink;
    }

    //put everything to hashmap then push on firebase
    public HashMap<String, Object> putMap() {
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put(Constain.ID_USER, idUser);
        myMap.put(Constain.USER_NAME, userName);
        myMap.put(Constain.EMAIL, email);
        myMap.put(Constain.GENDER, gender);
        myMap.put(Constain.PHONENUMBER, phoneNumber);
        myMap.put(Constain.LINKPHOTOUSER, linkPhotoUser);
        myMap.put(Constain.BIRTHDAY, birthDay);
        myMap.put(Constain.IS_STORE, isStore);
        myMap.put(Constain.SUM_ORDERED, sumOrdered);
        myMap.put(Constain.SUM_SHIPPED, sumShipped);
        myMap.put(Constain.LOCATION, location);
        myMap.put(Constain.FAVORITE_DRINK, favorite_drink);
        return myMap;
    }
    //getter and setter


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLinkPhotoUser() {
        return linkPhotoUser;
    }

    public void setLinkPhotoUser(String linkPhotoUser) {
        this.linkPhotoUser = linkPhotoUser;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isStore() {
        return isStore;
    }

    public void setStore(boolean store) {
        isStore = store;
    }

    public int getSumOrdered() {
        return sumOrdered;
    }

    public void setSumOrdered(int sumOrdered) {
        this.sumOrdered = sumOrdered;
    }

    public int getSumShipped() {
        return sumShipped;
    }

    public void setSumShipped(int sumShipped) {
        this.sumShipped = sumShipped;
    }

    public HashMap<String, Object> getLocation() {
        return location;
    }

    public void setLocation(HashMap<String, Object> address) {
        this.location = address;
    }

    public HashMap<String, Object> getFavorite_drink() {
        return favorite_drink;
    }

    public void setFavorite_drink(HashMap<String, Object> favorite_drink) {
        this.favorite_drink = favorite_drink;
    }
}
