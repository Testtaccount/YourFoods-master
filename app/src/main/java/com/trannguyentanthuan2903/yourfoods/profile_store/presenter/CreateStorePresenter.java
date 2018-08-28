package com.trannguyentanthuan2903.yourfoods.profile_store.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.main.view.MainActivity;
import com.trannguyentanthuan2903.yourfoods.profile_store.model.CreateStoreSubmitter;
import com.trannguyentanthuan2903.yourfoods.profile_store.view.CreateStoreActivity;

import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */

public class CreateStorePresenter {
    private DatabaseReference mData;
    private CreateStoreSubmitter submitter;
    private CreateStoreActivity view;
    private FirebaseAuth mAuth;

    public CreateStorePresenter(CreateStoreActivity view, FirebaseAuth mAuth) {
        this.view = view;
        this.mAuth = mAuth;
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new CreateStoreSubmitter(mData, view);
    }

    public void createNewStore(String email, String password, final String storeName, final String phoneNumber, final HashMap<String, Object> location,
                               final String from, final String to) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    HashMap<String, Object> favoriteList = new HashMap<>();
                    HashMap<String, Object> products = new HashMap<>();
                    HashMap<String, Object> orderSchedule = new HashMap<>();
                    HashMap<String, Object> comment = new HashMap<>();
                    HashMap<String, Object> rate = new HashMap<>();
                    String timework = from + "-" + to;
                    addNewStore(task.getResult().getUser().getUid().toString(), storeName, task.getResult().getUser().getEmail(), true, 0, phoneNumber,
                            "", timework, location, favoriteList, products, orderSchedule,comment,rate);
                    view.hideProgressDialog();
                    view.showToast("Create new store successful");
                    view.startActivity(new Intent(view, MainActivity.class));
                }else {
                    view.hideProgressDialog();
                    view.showToast("Email đã được đăng ký, vui lòng thử lại!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                view.hideProgressDialog();
                view.showToast("Đăng ký không thành công!");
            }
        });
    }

    public void addNewStore(String idStore, String storeName, String email, boolean isStore, int isOpen, String phoneNumber, String linkPhotoStore, String timeWork, HashMap<String, Object>location, HashMap<String, Object> favoriteList, HashMap<String, Object> products, HashMap<String, Object> orderSchedule,HashMap<String, Object> comment,HashMap<String, Object> rate) {
        submitter.addNewStore(idStore, storeName, email, isStore, isOpen, phoneNumber, linkPhotoStore, timeWork,location, favoriteList, products, orderSchedule,comment,rate);
    }
}
