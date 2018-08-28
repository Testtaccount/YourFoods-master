package com.trannguyentanthuan2903.yourfoods.history_order_user.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

/**
 * Created by Administrator on 10/16/2017.
 */

public class HistoryOrderSubmitter {
    private DatabaseReference mData;

    public HistoryOrderSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void createHistoryOrder (final String idUser, String idHistoryOrder, final String idStore, HistoryOrderUser historyOrderUser){
        mData.child(Constain.USERS).child(idUser).child(Constain.HISTORY_ORDER_USER).child(idStore).child(idHistoryOrder).setValue(historyOrderUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("ERRO", "Successful");
                }
                else {
                    Log.d("ERRO", "NotSuccessful");
                }
            }
        });
    }
    public void deleteMyCart (String idUser, String idStore){
        mData.child(Constain.USERS).child(idUser).child(Constain.MY_CART).child(idStore).removeValue();
    }
}