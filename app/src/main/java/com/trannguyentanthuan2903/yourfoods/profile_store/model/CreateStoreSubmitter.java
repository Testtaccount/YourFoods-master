package com.trannguyentanthuan2903.yourfoods.profile_store.model;

import com.google.firebase.database.DatabaseReference;
import com.trannguyentanthuan2903.yourfoods.profile_store.view.CreateStoreActivity;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */
public class CreateStoreSubmitter {
    private DatabaseReference mData;
    private CreateStoreActivity view;

    public CreateStoreSubmitter(DatabaseReference mData, CreateStoreActivity view) {
        this.mData = mData;
        this.view = view;
    }
    public void addNewStore (String idStore, String storeName, String email, boolean isStore, int isOpen, String phoneNumber, String linkPhotoStore, String timeWork, HashMap<String, Object> location, HashMap<String, Object> favoriteList, HashMap<String, Object> products, HashMap<String, Object> orderSchedule,HashMap<String, Object> comment,HashMap<String, Object> rate){
        Store store = new Store(idStore, storeName, email, isStore, isOpen, phoneNumber, linkPhotoStore, "", 0, 0, 0, timeWork, location, favoriteList, products, orderSchedule,comment,rate);
        HashMap<String, Object> myMap = new HashMap<>();
        myMap = store.putMap();
        mData.child(Constain.STORES).child(idStore).setValue(myMap);
    }
    public void updateStatus (String idStore, boolean isOpen){
        mData.child(Constain.STORES).child(idStore).child(Constain.IS_OPEN).setValue(isOpen);
    }
}
