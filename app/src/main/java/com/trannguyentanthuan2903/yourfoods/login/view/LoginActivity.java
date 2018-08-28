package com.trannguyentanthuan2903.yourfoods.login.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.login.presenter.LoginFacebookPresenter;
import com.trannguyentanthuan2903.yourfoods.login.presenter.LoginPresenter;
import com.trannguyentanthuan2903.yourfoods.main.view.MainActivity;
import com.trannguyentanthuan2903.yourfoods.oop.BaseActivity;
import com.trannguyentanthuan2903.yourfoods.reset_password.view.ResetPassword;
import com.trannguyentanthuan2903.yourfoods.sign_up.view.ChooseSignnup;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    public static final int RC_SIGN_IN = 10;

    private EditText edtEmail, edtPassword;
    private TextView txtSignUp, txtForgotPassword;
    private Button btnLogin;
    private ImageView imgLoginFb, imgLoginGoogle;
    private LoginPresenter presenter;
    private LoginFacebookPresenter fbPresenter;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        googleSingIn();
        addControls();
        addEvents();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder aler = new AlertDialog.Builder(this);
        aler.setCancelable(false);
        aler.setMessage("Bạn thực sự muốn thoát?");
        aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        aler.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void addEvents() {
        txtSignUp.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        imgLoginFb.setOnClickListener(this);
        imgLoginGoogle.setOnClickListener(this);

    }

    private void addControls() {
        //Edittext
        edtEmail = (EditText) findViewById(R.id.edtemail_login);
        edtPassword = (EditText) findViewById(R.id.edtpassword_login);
        //TextView
        txtSignUp = (TextView) findViewById(R.id.txtsignup_login);
        txtForgotPassword = (TextView) findViewById(R.id.txtforgotpassword);
        //Button
        btnLogin = (Button) findViewById(R.id.btnlogin);
        //ImageView
        imgLoginFb = (ImageView) findViewById(R.id.imgloginfb);
        imgLoginGoogle = (ImageView) findViewById(R.id.imglogingoogle);
        //presenter
        presenter = new LoginPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        fbPresenter = new LoginFacebookPresenter(this);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.txtsignup_login) {
            sign_Up();
        }
        if (view == R.id.txtforgotpassword) {
            progressForgotPassword();
        }
        if (view == R.id.btnlogin) {
            logIn();
        }
        if (view == R.id.imgloginfb) {
            loginFb();

        }
        if (view == R.id.imglogingoogle) {
            logInGoogle();

        }

    }

    private void logInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void loginFb() {
        fbPresenter.loginFacebook(this, callbackManager);
    }

    private void logIn() {
        boolean isVail = true;
        String email = (edtEmail.getText().toString()).trim();
        String password = (edtPassword.getText().toString()).trim();
        if (TextUtils.isEmpty(email)) {
            isVail = false;
            edtEmail.setError("Bắt Buộc");
        } else {
            edtEmail.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            isVail = false;
            edtPassword.setError("Bắt Buộc");
        } else {
            edtPassword.setError(null);
        }
        if (isVail) {
            showProgressDialog();
            presenter.login(email, password);

        }
    }

    private void progressForgotPassword() {
        startActivity(new Intent(this, ResetPassword.class));
    }

    private void sign_Up() {
        startActivity(new Intent(LoginActivity.this, ChooseSignnup.class));
    }

    public void moveToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }


    private void googleSingIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(LoginActivity.this, "Auth wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            moveToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
