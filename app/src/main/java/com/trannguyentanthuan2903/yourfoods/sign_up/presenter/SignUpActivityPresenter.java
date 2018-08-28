package com.trannguyentanthuan2903.yourfoods.sign_up.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.sign_up.model.SignUpActvitySubmitter;
import com.trannguyentanthuan2903.yourfoods.sign_up.view.SignupUserActivity;

import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */

public class SignUpActivityPresenter {
    private DatabaseReference mData;
    private SignupUserActivity view;
    private SignUpActvitySubmitter submitter;
    private FirebaseAuth mAuth;

    public SignUpActivityPresenter(SignupUserActivity view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new SignUpActvitySubmitter(mData, view);
    }
    public void signUpWithEmail (String password, String userName, String email, String phoneNumber, String linkPhotoUser, String birthDay, boolean isStore, HashMap<String, Object> location, HashMap<String, Object> favorite_drink){
        submitter.signUpWithEmail(password, userName, email, phoneNumber, linkPhotoUser, birthDay, isStore, location, favorite_drink);
    }
    public void signUp (String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    view.hideProgressDialog();
                    view.showToast("Đăng ký thành công!");
                }
                else {
                    view.showToast("Email đã đăng ký");
                }
            }
        });
    }
}

