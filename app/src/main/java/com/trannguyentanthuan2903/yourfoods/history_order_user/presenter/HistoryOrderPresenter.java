package com.trannguyentanthuan2903.yourfoods.history_order_user.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.history_order_user.model.HistoryOrderSubmitter;
import com.trannguyentanthuan2903.yourfoods.history_order_user.model.HistoryOrderUser;

/**
 * Created by Administrator on 10/16/2017.
 */

public class HistoryOrderPresenter {
    private DatabaseReference mData;
    private HistoryOrderSubmitter submitter;

    public HistoryOrderPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new HistoryOrderSubmitter(mData);
    }
    public void createHistoryOrder (String idUser, String idHistoryOrder, final String idStore, HistoryOrderUser historyOrderUser){
        submitter.createHistoryOrder(idUser, idHistoryOrder, idStore, historyOrderUser);
    }
    public void deleteMyCart (String idUser, String idStore){
        submitter.deleteMyCart(idUser, idStore);
    }
}