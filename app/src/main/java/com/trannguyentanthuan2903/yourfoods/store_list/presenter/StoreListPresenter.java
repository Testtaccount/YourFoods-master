package com.trannguyentanthuan2903.yourfoods.store_list.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.store_list.model.StoreListSubmitter;

/**
 * Created by Administrator on 10/17/2017.
 */
public class StoreListPresenter {
    private DatabaseReference mData;
    private StoreListSubmitter submitter;

    public StoreListPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new StoreListSubmitter(mData);
    }
    public void removeHeart (String idStore, String idUser){
        submitter.removeHeart(idStore, idUser);
    }
    public void addHeart (String idStore, String idUser, String emailUser){
        submitter.addHeart(idStore, idUser, emailUser);
    }

}
