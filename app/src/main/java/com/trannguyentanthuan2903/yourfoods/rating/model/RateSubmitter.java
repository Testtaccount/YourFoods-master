package com.trannguyentanthuan2903.yourfoods.rating.model;

import com.google.firebase.database.DatabaseReference;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 11/21/2017.
 */

public class RateSubmitter {
    private DatabaseReference mData;

    public RateSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }

    public void removeRate(String idStore, String idCmt) {
        mData.child(Constain.STORES).child(idStore).child(Constain.RATES).child(idCmt).removeValue();
    }

    public void addRate(String idStore, String idUser, String rate, String createDate) {
        HashMap<String, Object> rateStore = new HashMap<>();
        rateStore.put(Constain.USER_ID, idUser);
        rateStore.put(Constain.RATE, rate);
        rateStore.put(Constain.CREATE_DATE,createDate);
        mData.child(Constain.STORES).child(idStore).child(Constain.RATES).child(mData.push().getKey()).setValue(rateStore);
    }
}
