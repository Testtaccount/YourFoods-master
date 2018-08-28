package com.trannguyentanthuan2903.yourfoods.store_list.model;

import com.google.firebase.database.DatabaseReference;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 10/17/2017.
 */

public class StoreListSubmitter {
    private DatabaseReference mData;

    public StoreListSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void removeHeart (String idStore, String idUser){
        mData.child(Constain.STORES).child(idStore).child(Constain.FAVORITE_LIST).child(idUser).removeValue();
    }
    public void addHeart (String idStore, String idUser, String emailUser){
        HashMap<String, Object> userHeart = new HashMap<>();
        userHeart.put(Constain.ID_USER, idUser);
        userHeart.put(Constain.USER_NAME, emailUser);
        mData.child(Constain.STORES).child(idStore).child(Constain.FAVORITE_LIST).child(idUser).setValue(userHeart);
    }
}
