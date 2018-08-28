package com.trannguyentanthuan2903.yourfoods.profile_user.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 10/17/2017.
 */

public class UserProfileSubmitter {
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private String linkPhotoUser = "";
    private ProgressDialog mProgress;
    private Context mContext;

    public UserProfileSubmitter(Context mContext, ProgressDialog mProgress, DatabaseReference mData, FirebaseAuth mAuth, StorageReference mStorage) {
        this.mContext = mContext;
        this.mProgress = mProgress;
        this.mData = mData;
        this.mAuth = mAuth;
        this.mStorage = mStorage;
    }

    public void updateUserName(String idUser, String userName) {
        showProgressDialog();
        mData.child(Constain.USERS).child(idUser).child(Constain.USER_NAME).setValue(userName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    hideProgressDialog();
                    showToast("Cập nhật Username thành công!");
                } else {
                    hideProgressDialog();
                    showToast("Cập nhật Username không thành công, vui lòng thử lại");
                }
            }
        });
    }

    public void updatePhoneNumber(String idUser, String phoneNumber) {
        showProgressDialog();
        mData.child(Constain.USERS).child(idUser).child(Constain.PHONENUMBER).setValue(phoneNumber).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    hideProgressDialog();
                    showToast("Cập nhật số điện thoại thành công!");
                } else {
                    hideProgressDialog();
                    showToast("Cập nhật số điện thoại không thành công, vui lòng thử lại");
                }
            }
        });
    }

    public void updateEmail (final String idUser, final String email, String password, final String newEmail) {
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    task.getResult().getUser().updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mData.child(Constain.USERS).child(idUser).child(Constain.EMAIL).setValue(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
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


    public void updatePassword (final String email, String password, final String newPassword) {
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

    public void updatePhoto(Bitmap bitmap, final String idUser) {
        showProgressDialog();
        StorageReference mountainsRef = mStorage.child(Constain.USERS).child(idUser);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                hideProgressDialog();
                showToast("Cập nhật ảnh đại diện không thành công");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests")  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                linkPhotoUser = String.valueOf(downloadUrl);
                if (!linkPhotoUser.equals("")) {
                    updateLinkPhotoUser(idUser, linkPhotoUser);
                }
            }
        });
    }

    public void updateAdress(String idUser, String address)  {
        showProgressDialog();
        mData.child(Constain.USERS).child(idUser).child(Constain.LOCATION).child(Constain.ADDRESS).setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    hideProgressDialog();
                    showToast("Cập nhật địa chỉ thành công!");
                } else {
                    hideProgressDialog();
                    showToast("Cập nhật địa chỉ không thành công, vui lòng thử lại");
                }
            }
        });
    }

    public void updateLinkPhotoUser(String idUser, String linkPhotoUser) {
        mData.child(Constain.USERS).child(idUser).child(Constain.LINKPHOTOUSER).setValue(linkPhotoUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    hideProgressDialog();
                    showToast("Cập nhật ảnh đại diện thành công!");
                } else {
                    hideProgressDialog();
                    showToast("Cập nhật ảnh đại diện không thành công, vui lòng thử lại");
                }
            }
        });
    }

    public void updateSumOrderUser(String idUser, int sumOrdered) {
        mData.child(Constain.USERS).child(idUser).child(Constain.SUM_ORDERED).setValue(sumOrdered);
    }

    public void showProgressDialog() {
        mProgress.setMessage("Loading...");
        mProgress.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgress.create();
        }
        mProgress.show();
    }

    public void hideProgressDialog() {
        mProgress.dismiss();
    }

    public void showToast(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }
}
