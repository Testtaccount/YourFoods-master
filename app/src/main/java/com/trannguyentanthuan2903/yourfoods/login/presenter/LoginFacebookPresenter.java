package com.trannguyentanthuan2903.yourfoods.login.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.login.model.LoginSubmitter;
import com.trannguyentanthuan2903.yourfoods.login.view.LoginActivity;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */


public class LoginFacebookPresenter {
    private LoginSubmitter submitter;
    private FirebaseAuth mAuth;
    private LoginActivity view;
    private DatabaseReference mData;

    public LoginFacebookPresenter(LoginActivity view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new LoginSubmitter(mData, view);

    }
    public void loginFacebook (Context context, CallbackManager callbackManager){
        //khởi tạo Facebook SDK
        FacebookSdk.sdkInitialize(context);
        String [] listPermisstion = {"email", "public_profile", "user_friends"};
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(view, Arrays.asList(listPermisstion));
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                view.showToast("Đăng nhập thành công!");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                view.showToast("Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                view.showToast("Error");
            }
        });

    }
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //creare new user on firebase
                    try {
                        mData.child(Constain.USERS).child(task.getResult().getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null){
                                    view.moveToMainActivity();
                                }
                                else {
                                    HashMap<String, Object> location = new HashMap<>();
                                    HashMap<String, Object> favorite_drink = new HashMap<>();
                                    submitter.addUser(task.getResult().getUser().getUid(), task.getResult().getUser().getDisplayName(), task.getResult().getUser().getEmail(), true, "", task.getResult().getUser().getPhotoUrl().toString(), "", location, favorite_drink);
                                    view.moveToMainActivity();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                else {
                    view.moveToMainActivity();
                }
            }
        });
    }
}
