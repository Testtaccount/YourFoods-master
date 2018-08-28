package com.trannguyentanthuan2903.yourfoods.oop;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 10/15/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private FirebaseAuth mAuthencation;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgress = new ProgressDialog(this);
        mAuthencation = FirebaseAuth.getInstance();
    }

    public void showProgressDialog (){
        mProgress.setMessage("Loading...");
        mProgress.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgress.create();
        }
        mProgress.show();
    }
    public void hideProgressDialog (){
        mProgress.dismiss();
    }
    public String getIdUser (){
        String idUser = null;
        try {
            FirebaseUser user = mAuthencation.getCurrentUser();
            if (user != null) {
                return user.getUid();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return idUser;
    }
    public void showToast (String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    public  boolean isEmailVail (String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
