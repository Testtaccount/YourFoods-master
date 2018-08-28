package com.trannguyentanthuan2903.yourfoods.profile_store.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.trannguyentanthuan2903.yourfoods.profile_store.view.Profile_Store_Fragment;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 10/16/2017.
 */

public class UpdateStoreSubmitter {
    private DatabaseReference mData;
    private Context mContext;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private Profile_Store_Fragment fragment;

    public UpdateStoreSubmitter(DatabaseReference mData, Context mContext, ProgressDialog mProgress, FirebaseAuth mAuth, StorageReference mStorage, Profile_Store_Fragment fragment) {
        this.mData = mData;
        this.mContext = mContext;
        this.mProgress = mProgress;
        this.mAuth = mAuth;
        this.mStorage = mStorage;
        this.fragment = fragment;
    }

    public void updateStatusStore(final String idStore, final int isOpen) {
        mData.child(Constain.STORES).child(idStore).child(Constain.IS_OPEN).setValue(isOpen).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (isOpen == 0) {
                        showToast("Mở cửa");
                    } else if (isOpen == 1){
                        showToast("Đóng cửa");
                    }
                }

            }
        });
    }

    public void updateStoreName(String idStore, String storeName) {
        showProgressDialog();
        mData.child(Constain.STORES).child(idStore).child(Constain.STORE_NAME).setValue(storeName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    hideProgressDialog();
                    showToast("Cập nhật tên cửa hàng thành công!");
                }
                else {
                    hideProgressDialog();
                    showToast("Cập nhật tên cửa hàng không thành công!");
                }
            }
        });
    }

    public void updatephoneNumberStore(String idStore, String phoneNumber) {
        showProgressDialog();
        mData.child(Constain.STORES).child(idStore).child(Constain.PHONENUMBER).setValue(phoneNumber).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    hideProgressDialog();
                    showToast("Cập nhật số điện thoại thành công!");
                }
                else {
                    hideProgressDialog();
                    showToast("Cập nhật số điện thoại không thành công!");
                }
            }
        });
    }

    public void updateAddressStore(String idStore, String addressStore) {
        showProgressDialog();
        mData.child(Constain.STORES).child(idStore).child(Constain.LOCATION).child(Constain.ADDRESS).setValue(addressStore).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    hideProgressDialog();
                    showToast("Cập nhật địa chỉ thành công!");
                }
                else {
                    hideProgressDialog();
                    showToast("Cập nhật địa chỉ không thành công!");
                }
            }
        });
    }

    public void updateEmailStore (final String idStore, final String email, String password, final String newEmail) {
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    task.getResult().getUser().updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mData.child(Constain.STORES).child(idStore).child(Constain.EMAIL).setValue(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            hideProgressDialog();
                                            showToast("Cập nhật Email thành công!");
                                        }
                                        else {
                                            hideProgressDialog();
                                            showToast("Cập nhật Email không thành công!, vui lòng thử lại");
                                        }
                                    }
                                });
                            }
                            else {
                                hideProgressDialog();
                                showToast("Email đã tồn tại, vui lòng chọn Email khác");
                            }
                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                e.getMessage();
                if (e.getMessage().equals("The password is invalid or the user does not have a password.")){
                    showToast("Password không chính xác vui lòng thử lại");
                }
                Log.d("Error", e.getMessage().toString());
            }
        });
    }

    public void updatePasswordStore (final String email, String password, final String newPassword) {
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    task.getResult().getUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                hideProgressDialog();
                                showToast("Cập nhật mật khẩu thành công!");
                            }
                            else {
                                hideProgressDialog();
                                showToast("Vui lòng chọn mật khẩu khác mật khẩu cũ");
                            }
                        }
                    });

                }
                else {
                    hideProgressDialog();
                    showToast("Password không chính xác vui lòng thử lại!");
                }
            }
        });
    }
    public void updateCoverStore(final Bitmap bitmap, final String idStore) {
        showProgressDialog();
        StorageReference mountainsRef = mStorage.child(Constain.STORES).child(idStore).child(Constain.LINKCOVERSTORE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String linkCoverStore = String.valueOf(downloadUrl);
                if (!linkCoverStore.equals("")) {
                    updateLinkCoverStore(idStore, linkCoverStore, bitmap);
                }
                else {
                    hideProgressDialog();
                    showToast("Cập nhật ảnh bìa không thành công!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                showToast("Cập nhật ảnh bìa không thành công!");
            }
        });
    }

    public void updateLinkCoverStore(String idStore, String linkCoverStore, final Bitmap bitmap) {
        mData.child(Constain.STORES).child(idStore).child(Constain.LINKCOVERSTORE).setValue(linkCoverStore).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    hideProgressDialog();
                    showToast("Cập nhật ảnh bìa thành công!");
                    fragment.imgCover.setImageBitmap(bitmap);
                }
                else {
                    hideProgressDialog();
                    showToast("Cập nhật ảnh bìa không thành công!");
                }
            }
        });
    }

    public void updateAvataStore(final Bitmap bitmap, final String idStore) {
        showProgressDialog();
        StorageReference mountainsRef = mStorage.child(Constain.STORES).child(idStore).child(Constain.LINKPHOTOSTORE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String linkPhotoStore = String.valueOf(downloadUrl);
                if (!linkPhotoStore.equals("")) {
                    updateLinkPhotoStore(idStore, linkPhotoStore, bitmap);
                }
                else {
                    hideProgressDialog();
                    showToast("Câp nhật ảnh đại diện không thành công, vui lòng thử lại");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                showToast("Câp nhật ảnh đại diện không thành công, vui lòng thử lại");
            }
        });
    }

    public void updateLinkPhotoStore(String idStore, String linkPhotoStore, final Bitmap bitmap) {
        mData.child(Constain.STORES).child(idStore).child(Constain.LINKPHOTOSTORE).setValue(linkPhotoStore).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    hideProgressDialog();
                    showToast("Cập nhật ảnh đại diện thành công");
                    fragment.imgAvata.setImageBitmap(bitmap);
                }
                else {
                    hideProgressDialog();
                    showToast("Câp nhật ảnh đại diện không thành công, vui lòng thử lại");
                }
            }
        });
    }
    public void updateSumOrderedStore (String idStore, int sumOrdered){
        mData.child(Constain.STORES).child(idStore).child(Constain.SUM_ORDERED).setValue(sumOrdered);
    }
    public void updateSumShippedStore (String idStore, int sumShipped){
        mData.child(Constain.STORES).child(idStore).child(Constain.SUM_SHIPPED).setValue(sumShipped);
    }
    public void updateSumProduct (String idStore, int sumProduct) {
        mData.child(Constain.STORES).child(idStore).child(Constain.SUM_PRODUCT).setValue(sumProduct);
    }
    public void showProgressDialog (){
        mProgress.setMessage("Loading...");
        mProgress.setCancelable(false);
        mProgress.create();
        mProgress.show();
    }
    public void hideProgressDialog (){
        mProgress.dismiss();
    }
    public void showToast (String s){
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

}
