package com.trannguyentanthuan2903.yourfoods.sign_up.model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.trannguyentanthuan2903.yourfoods.main.view.MainActivity;
import com.trannguyentanthuan2903.yourfoods.profile_user.model.User;
import com.trannguyentanthuan2903.yourfoods.sign_up.view.SignupUserActivity;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */


public class SignUpActvitySubmitter {
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private SignupUserActivity view;

    public SignUpActvitySubmitter(DatabaseReference mData, SignupUserActivity view) {
        this.mData = mData;
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUpWithEmail(String password, final String userName, final String email, final String phoneNumber, final String linkPhotoUser, final String birthDay, final boolean isStore, final HashMap<String, Object> location, final HashMap<String, Object> favorite_drink) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    view.hideProgressDialog();
                    view.showToast("Đăng ký thành công");
                    addUser(task.getResult().getUser().getUid(), userName, task.getResult().getUser().getEmail(), true, phoneNumber, linkPhotoUser, birthDay, isStore, 0, location, favorite_drink);
                    sendVerificationEmail();
                    view.startActivity(new Intent(view, MainActivity.class));
                } else {
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

    //send verify
    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(view, "Couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //create new user on firebase
    public void addUser(String idUser, String userName, String email, boolean gender, String phoneNumber, String linkPhotoUser, String birthDay, boolean isStore, int sumOrdered, HashMap<String, Object> location, HashMap<String, Object> favorite_drink) {
        User user = new User(idUser, userName, email, gender, phoneNumber, linkPhotoUser, birthDay, false, sumOrdered, 0, location, favorite_drink);
        HashMap<String, Object> myMap = new HashMap<>();
        myMap = user.putMap();
        mData.child(Constain.USERS).child(idUser).setValue(myMap);
    }
}

