package com.trannguyentanthuan2903.yourfoods.comment.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.trannguyentanthuan2903.yourfoods.R;

public class CommentActivity extends AppCompatActivity {

    private String idStore;
    private FrameLayout mFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mFrameLayout = (FrameLayout) findViewById(R.id.container);

        ViewCommentsFragment fragment= new ViewCommentsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();

        getIdStore();
    }

    private void getIdStore(){
        Intent intent = getIntent();
        idStore = intent.getStringExtra("id_store");
    }
}
