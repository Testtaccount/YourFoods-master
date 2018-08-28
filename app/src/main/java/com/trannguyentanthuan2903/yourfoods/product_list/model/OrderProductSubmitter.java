package com.trannguyentanthuan2903.yourfoods.product_list.model;

import com.google.firebase.database.DatabaseReference;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 10/17/2017.
 */

public class OrderProductSubmitter {
    private DatabaseReference mData;
    public OrderProductSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void orderProduct (String idStore, String idUser, String idCategory, String idProduct,  HashMap<String, Object> myMap){
        mData.child(Constain.STORES).child(idStore).child(idUser).child(idCategory).child(idProduct).setValue(myMap);
    }
}