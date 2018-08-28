package com.trannguyentanthuan2903.yourfoods.main.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.main.model.StoreSubmitter;

import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */

public class StorePresenter {
    private DatabaseReference mData;
    private StoreSubmitter submitter;

    public StorePresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new StoreSubmitter(mData);
    }
    public void updateLocation (String idUser, HashMap<String, Object> location){
        submitter.updateLocation(idUser, location);
    }
}
