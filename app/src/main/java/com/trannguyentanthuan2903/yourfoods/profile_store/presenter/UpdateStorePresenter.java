package com.trannguyentanthuan2903.yourfoods.profile_store.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.trannguyentanthuan2903.yourfoods.profile_store.model.UpdateStoreSubmitter;
import com.trannguyentanthuan2903.yourfoods.profile_store.view.Profile_Store_Fragment;

/**
 * Created by Administrator on 10/16/2017.
 */


public class UpdateStorePresenter {
    private DatabaseReference mData;
    private Context mContext;
    private ProgressDialog mProgress;
    private UpdateStoreSubmitter submitter;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private Profile_Store_Fragment fragment;

    public UpdateStorePresenter(Context mContext, Profile_Store_Fragment fragment) {
        this.mContext = mContext;
        this.fragment = fragment;
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(mContext);
        submitter = new UpdateStoreSubmitter(mData, mContext, mProgress, mAuth, mStorage, fragment);
    }

    public void updateStatusStore(String idStore, int isOpen) {
        submitter.updateStatusStore(idStore, isOpen);
    }

    public void updateStoreName(String idStore, String storeName) {
        submitter.updateStoreName(idStore, storeName);
    }

    public void updatephoneNumberStore(String idStore, String phoneNumber) {
        submitter.updatephoneNumberStore(idStore, phoneNumber);
    }

    public void updateAddressStore(String idStore, String addressStore) {
        submitter.updateAddressStore(idStore, addressStore);
    }

    public void updateEmailStore(final String idStore, final String email, String password, final String newEmail) {
        submitter.updateEmailStore(idStore, email, password, newEmail);
    }


    public void updatePasswordStore(final String email, String password, final String newPassword) {
        submitter.updatePasswordStore(email, password, newPassword);
    }

    public void updateCoverStore(Bitmap bitmap, final String idStore) {
        submitter.updateCoverStore(bitmap, idStore);
    }

    public void updateAvataStore(Bitmap bitmap, final String idStore) {
        submitter.updateAvataStore(bitmap, idStore);
    }

    public void updateSumOrderedStore(String idStore, int sumOrdered) {
        submitter.updateSumOrderedStore(idStore, sumOrdered);
    }

    public void updateSumShippedStore(String idStore, int sumShipped) {
        submitter.updateSumShippedStore(idStore, sumShipped);
    }

    public void updateSumProduct(String idStore, int sumProduct) {
        submitter.updateSumProduct(idStore, sumProduct);
    }
}
