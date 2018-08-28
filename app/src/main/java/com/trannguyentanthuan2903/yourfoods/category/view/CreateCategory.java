package com.trannguyentanthuan2903.yourfoods.category.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.category.model.Category;
import com.trannguyentanthuan2903.yourfoods.category.presenter.CategoryPresenter;
import com.trannguyentanthuan2903.yourfoods.oop.BaseActivity;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.Calendar;
import java.util.Random;

public class CreateCategory extends BaseActivity {

    private Button btnCancel, btnOk;
    private EditText edtCategoryName;
    private String idStore;
    private DatabaseReference mData;
    private String idCategory, timeCreate;
    private CategoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flag = edtCategoryName.getText().toString();
                if (!TextUtils.isEmpty(flag)) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CreateCategory.this);
                    alert.setMessage("Bạn đang nhập dở,Thoát có thể làm mất dữ liệu?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setCancelable(false);
                    alert.show();
                } else {
                    finish();
                }
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCategory();
            }
        });
    }

    private void createCategory() {
        String flag_CategoryName1 = edtCategoryName.getText().toString();
        boolean isVail = true;
        if (TextUtils.isEmpty(flag_CategoryName1)) {
            isVail = false;
            edtCategoryName.setError("Bắt Buộc");
        } else {
            edtCategoryName.setError(null);
        }
        if (isVail) {
            String[] tu = flag_CategoryName1.trim().split(" ");
            String flag_Category2 = "";
            for (int i = 0; i < tu.length; i++) {
                tu[i] = Character.toUpperCase(tu[i].charAt(0)) + tu[i].substring(1) + " ";
                flag_Category2 += tu[i];
            }
            final String categoryName = flag_Category2.trim();
            try {
                mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        StringBuilder mBuilder = new StringBuilder();
                        if (dataSnapshot.getValue() != null) {
                            boolean flag_id = true;
                            while (flag_id == true) {
                                Random ra = new Random();
                                for (int i = 0; i <= 10; i++) {
                                    String number = String.valueOf(ra.nextInt(10));
                                    mBuilder.append(number);
                                }
                                idCategory = mBuilder.toString();
                                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                    if (idCategory.equals(dt.getKey())) {
                                        flag_id = true;
                                        break;
                                    } else {
                                        flag_id = false;
                                    }
                                }
                            }
                            try {
                                boolean flag_name = true;
                                for (DataSnapshot dt : dataSnapshot.getChildren()){
                                    Category category = dt.getValue(Category.class);
                                    if (categoryName.equals(category.getCategoryName())){
                                        flag_name = false;
                                        showToast("Tên thư mục đã tồn tại, vui lòng chọn tên khác");
                                        edtCategoryName.requestFocus();
                                        break;
                                    }
                                }
                                if (flag_name == true ){
                                    presenter.addCategoryOnData(idStore, idCategory, categoryName, 0, timeCreate);
                                    Toast.makeText(CreateCategory.this, "Tạo Thư Mục Thành Công!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (Exception ex) {

                            }
                        } else {
                            Random ra = new Random();
                            for (int i = 0; i <= 10; i++) {
                                String number = String.valueOf(ra.nextInt(10));
                                mBuilder.append(number);
                            }
                            idCategory = mBuilder.toString();
                            presenter.addCategoryOnData(idStore, idCategory, categoryName, 0, timeCreate);
                            Toast.makeText(CreateCategory.this, "Tạo Thư Mục Thành Công!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addControls() {
        btnCancel = (Button) findViewById(R.id.btnCancelCreateCategory);
        btnOk = (Button) findViewById(R.id.btnOkCreateCategory);
        edtCategoryName = (EditText) findViewById(R.id.edtCategoryName_createCategory);
        mData = FirebaseDatabase.getInstance().getReference();
        presenter = new CategoryPresenter();
        //get idStore
        Intent intent = getIntent();
        idStore = intent.getStringExtra(Constain.ID_STORE);
        //get Timenow
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        int mouth = now.get(Calendar.MONTH) + 1;
        int year = now.get(Calendar.YEAR);
        timeCreate = day + "\\" + mouth + "\\" + year;
    }


}
