package com.trannguyentanthuan2903.yourfoods.rating.model;

/**
 * Created by Administrator on 11/21/2017.
 */

public class Rate {

    private String key_rate;
    private String rate;
    private String user_id;
    private String date_created;

    public Rate() {

    }

    public Rate(String key_rate, String rate, String user_id, String date_created) {
        this.key_rate = key_rate;
        this.rate = rate;
        this.user_id = user_id;
        this.date_created = date_created;
    }

    public String getKey_rate() {
        return key_rate;
    }

    public void setKey_rate(String key_rate) {
        this.key_rate = key_rate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "key_rate='" + key_rate + '\'' +
                ", rate='" + rate + '\'' +
                ", user_id='" + user_id + '\'' +
                ", date_created='" + date_created + '\'' +
                '}';
    }
}
