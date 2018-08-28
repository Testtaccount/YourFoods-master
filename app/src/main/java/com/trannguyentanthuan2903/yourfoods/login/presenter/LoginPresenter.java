package com.trannguyentanthuan2903.yourfoods.login.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.login.model.LoginSubmitter;
import com.trannguyentanthuan2903.yourfoods.login.view.LoginActivity;

/**
 * Created by Administrator on 10/16/2017.
 */

public class LoginPresenter {
    private DatabaseReference mData;
    private LoginSubmitter submitter;
    private LoginActivity view;

    public LoginPresenter(LoginActivity view) {
        this.view = view;
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new LoginSubmitter(mData, view);
    }
    public void login (String email, String password){
        submitter.login(email, password);
    }

}
