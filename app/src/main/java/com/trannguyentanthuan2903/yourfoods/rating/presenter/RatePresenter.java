package com.trannguyentanthuan2903.yourfoods.rating.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.rating.model.RateSubmitter;

/**
 * Created by Administrator on 11/21/2017.
 */

public class RatePresenter {
    private DatabaseReference mData;
    private RateSubmitter submitter;

    public RatePresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new RateSubmitter(mData);
    }
    public void removeRate (String idStore, String idRate){
        submitter.removeRate(idStore, idRate);
    }
    public void addRate (String idStore, String idUser, String rate, String createDate){
        submitter.addRate(idStore, idUser,rate,createDate);
    }
}
