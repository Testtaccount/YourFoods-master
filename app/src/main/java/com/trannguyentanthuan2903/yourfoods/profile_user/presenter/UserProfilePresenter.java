package com.trannguyentanthuan2903.yourfoods.profile_user.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.trannguyentanthuan2903.yourfoods.profile_user.model.UserProfileSubmitter;

/**
 * Created by Administrator on 10/17/2017.
 */

public class UserProfilePresenter {
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private UserProfileSubmitter submitter;
    private Context mContext;
    private ProgressDialog mProgress;

    public UserProfilePresenter(Context mContext) {
        this.mContext = mContext;
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(mContext);
        submitter = new UserProfileSubmitter(mContext, mProgress, mData, mAuth, mStorage);
    }

    public void updateUserName(String idUser, String userName) {
        submitter.updateUserName(idUser, userName);
    }

    public void updatePhoneNumber(String idUser, String phoneNumber) {
        submitter.updatePhoneNumber(idUser, phoneNumber);
    }

    public void updatePassword (String email, String password, String newPassword) {
        submitter.updatePassword(email, password, newPassword);
    }

    public void updateEmail (final String idUser, final String email, String password, final String newEmail) {
        submitter.updateEmail(idUser, email, password, newEmail);
    }

    public void updatePhoto(Bitmap bitmap, final String idUser) {
        submitter.updatePhoto(bitmap, idUser);
    }

    public void updateAdress(String idUser, String address) {
        submitter.updateAdress(idUser, address);
    }

    public void updateSumOrderUser(String idUser, int sumOrdered) {
        submitter.updateSumOrderUser(idUser, sumOrdered);
    }
}
