package com.trannguyentanthuan2903.yourfoods.main.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */

public class UserSubmitter {
    private DatabaseReference mData;

    public UserSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void updateLocation (String idUser, HashMap<String, Object> location){
        mData.child(Constain.USERS).child(idUser).child(Constain.LOCATION).setValue(location).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("D", "SUCCESS");
                }
                else {
                    Log.d("D", "UNSUCCESS");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("D", e.getMessage());

            }
        });
    }
}
