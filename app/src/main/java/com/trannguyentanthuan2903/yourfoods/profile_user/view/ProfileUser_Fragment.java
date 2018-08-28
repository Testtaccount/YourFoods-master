package com.trannguyentanthuan2903.yourfoods.profile_user.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.oop.BaseFragment;
import com.trannguyentanthuan2903.yourfoods.profile_user.model.User;
import com.trannguyentanthuan2903.yourfoods.profile_user.presenter.UserProfilePresenter;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 10/17/2017.
 */

public class ProfileUser_Fragment extends BaseFragment implements View.OnClickListener {

    private TextView txtSumOrder, txtSumShipped, txtUserName, txtEmail, txtPhoneNumber, txtAddress;
    private ImageView imgPhotoUser, imgBlur;
    private LinearLayout layoutChangePhoto, layoutEditUserName, layoutEditPhoneNumber, layoutEditEmail, layoutEditAddress, layoutEditPassword, layoutLocation;
    private String idUser, userName, email, phoneNumber, address;
    private boolean isStore;
    private DatabaseReference mData;
    private Bitmap bitmap;
    private boolean flag;
    private UserProfilePresenter presenter;

    public ProfileUser_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
        initInfo();
        addEvents();
    }

    private void initInfo() {
        if (isStore == true) {
            layoutChangePhoto.setVisibility(View.GONE);
            layoutEditUserName.setVisibility(View.INVISIBLE);
            layoutEditPhoneNumber.setVisibility(View.INVISIBLE);
            layoutEditEmail.setVisibility(View.INVISIBLE);
            layoutEditAddress.setVisibility(View.GONE);
            layoutLocation.setVisibility(View.VISIBLE);
            layoutEditPassword.setVisibility(View.INVISIBLE);
            imgBlur.setVisibility(View.GONE);
        }
        //getInfo User
        try {
            mData.child(Constain.USERS).child(idUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            User user = dataSnapshot.getValue(User.class);
                            if (user.getUserName() != null) {
                                txtUserName.setText(user.getUserName());
                                userName = user.getUserName();
                            }
                            if (user.getPhoneNumber() != null) {
                                txtPhoneNumber.setText(user.getPhoneNumber());
                                phoneNumber = user.getPhoneNumber();
                            }
                            if (user.getEmail() != null) {
                                txtEmail.setText(user.getEmail());
                                email = user.getEmail();
                            }
                            if (!user.getLinkPhotoUser().equals(" ")) {
                                Glide.with(getContext())
                                        .load(user.getLinkPhotoUser())
                                        .fitCenter()
                                        .into(imgPhotoUser);
                            }
                            txtSumShipped.setText(String.valueOf(user.getSumShipped()));
                            txtSumOrder.setText(String.valueOf(user.getSumOrdered()));
                            if (user.getLocation() != null) {
                                HashMap<String, Object> location = user.getLocation();
                                address = (String) location.get(Constain.ADDRESS);
                                if (address != null) {
                                    txtAddress.setText(address);
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
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

    private void addEvents() {
        if (isStore == true) {
            layoutChangePhoto.setOnClickListener(null);
            layoutEditUserName.setOnClickListener(null);
            layoutEditEmail.setOnClickListener(null);
            layoutEditPhoneNumber.setOnClickListener(null);
            layoutEditAddress.setOnClickListener(null);
            layoutEditPassword.setOnClickListener(null);
            layoutLocation.setOnClickListener(this);
        } else {
            layoutChangePhoto.setOnClickListener(this);
            layoutEditUserName.setOnClickListener(this);
            layoutEditEmail.setOnClickListener(this);
            layoutEditPhoneNumber.setOnClickListener(this);
            layoutEditAddress.setOnClickListener(this);
            layoutEditPassword.setOnClickListener(this);
            layoutLocation.setOnClickListener(null);
        }
    }

    private void addControls() {
        txtSumOrder = (TextView) getActivity().findViewById(R.id.txtSumOredered_profileUser);
        txtSumShipped = (TextView) getActivity().findViewById(R.id.txtSumShipped_profileUser);
        txtUserName = (TextView) getActivity().findViewById(R.id.txtUserName_profileUser);
        txtEmail = (TextView) getActivity().findViewById(R.id.txtEmail_profileUser);
        txtPhoneNumber = (TextView) getActivity().findViewById(R.id.txtPhoneNumber_profileUser);
        txtAddress = (TextView) getActivity().findViewById(R.id.txtAddress_profileUser);
        imgPhotoUser = (ImageView) getActivity().findViewById(R.id.imgPhotoUser);
        imgBlur = (ImageView) getActivity().findViewById(R.id.imgBlur);
        layoutChangePhoto = (LinearLayout) getActivity().findViewById(R.id.layoutChangePhotoUser);
        layoutEditEmail = (LinearLayout) getActivity().findViewById(R.id.layoutEditEmail);
        layoutEditAddress = (LinearLayout) getActivity().findViewById(R.id.layoutEditAdress);
        layoutEditPhoneNumber = (LinearLayout) getActivity().findViewById(R.id.layoutEditPhoneNumber);
        layoutEditUserName = (LinearLayout) getActivity().findViewById(R.id.layoutEditUserName);
        layoutEditPassword = (LinearLayout) getActivity().findViewById(R.id.layoutEditPasswordUser);
        layoutLocation = (LinearLayout) getActivity().findViewById(R.id.layoutLocation);
        //get  Intent
        Intent intent = getActivity().getIntent();
        idUser = intent.getStringExtra(Constain.ID_USER);
        isStore = intent.getBooleanExtra(Constain.IS_STORE, false);
        flag = intent.getBooleanExtra("FLAG", false);
        mData = FirebaseDatabase.getInstance().getReference();
        presenter = new UserProfilePresenter(getContext());

    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.layoutChangePhotoUser) {
            changePhoto();
        }
        if (view == R.id.layoutEditUserName) {
            editUserName();
        }
        if (view == R.id.layoutEditEmail) {
            editEmail();
        }
        if (view == R.id.layoutEditPhoneNumber) {
            editPhoneNumber();
        }
        if (view == R.id.layoutEditAdress) {
            editAddress();
        }
        if (view == R.id.layoutLocation) {
            showGoogleMap();
        }
        if (view == R.id.layoutEditPasswordUser) {
            editPassword();
        }
    }

    private void editPassword() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtPassword = new EditText(getContext());
        final EditText edtnewPassword = new EditText(getContext());
        final EditText edtConfirmPassword = new EditText(getContext());
        edtPassword.setHint("Enter your Password");
        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtPassword.setHintTextColor(R.color.black);
        edtPassword.setTextColor(R.color.black);
        edtnewPassword.setHint("Enter your new password");
        edtnewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtnewPassword.setHintTextColor(R.color.black);
        edtnewPassword.setTextColor(R.color.black);
        edtConfirmPassword.setHint("Confirm your new password");
        edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtConfirmPassword.setHintTextColor(R.color.black);
        edtConfirmPassword.setTextColor(R.color.black);
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10, 0, 10, 0);
        layout.addView(edtPassword);
        layout.addView(edtnewPassword);
        layout.addView(edtConfirmPassword);
        aler.setView(layout);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = edtPassword.getText().toString().trim();
                String newPassword = edtnewPassword.getText().toString().trim();
                String confirmPasswrod = edtConfirmPassword.getText().toString().trim();
                boolean flag = true;
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPasswrod)){
                    showToast("Password không được trống");
                    flag = false;
                }
                if (newPassword.length() > 0 && newPassword.length() < 6){
                    flag = false;
                    showToast("Mật khẩu mới phải lớn hơn 6 ký tự");
                }
                if (!newPassword.equals(confirmPasswrod)){
                    flag = false;
                    showToast("Mật khẩu mới không trùng khớp, vui lòng thử lại!");
                }
                if (flag){
                    presenter.updatePassword(email, password, newPassword);
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void showGoogleMap() {
    }

    private void editAddress() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtAddress = new EditText(getContext());
        edtAddress.setText(address);
        edtAddress.setHint("Enter your address");
        aler.setView(edtAddress);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtAddress.getText().toString())) {
                    showToast("username không được trống!");
                } else {
                    presenter.updateAdress(idUser, edtAddress.getText().toString());
                    txtAddress.setText(edtAddress.getText());
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void editPhoneNumber() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtPhoneNumber = new EditText(getContext());
        edtPhoneNumber.setText(phoneNumber);
        edtPhoneNumber.setHint("Enter your Phonenumber");
        aler.setView(edtPhoneNumber);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtPhoneNumber.getText().toString())) {
                    showToast("Phonenumber không được trống!");
                } else {
                    presenter.updatePhoneNumber(idUser, edtPhoneNumber.getText().toString());
                    txtPhoneNumber.setText(edtPhoneNumber.getText());
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void editEmail() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtEmail = new EditText(getContext());
        edtEmail.setText(email);
        edtEmail.setHint("Enter your Email");
        final EditText edtPassword = new EditText(getContext());
        edtPassword.setHint("Enter your password");
        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtPassword.setHintTextColor(R.color.black);
        edtPassword.setTextColor(R.color.black);
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10, 0, 10, 0);
        layout.addView(edtEmail);
        layout.addView(edtPassword);
        aler.setView(layout);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newEmail = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(newEmail) || TextUtils.isEmpty(password)) {
                    showToast("email hoặc password trống!");
                } else {

                    presenter.updateEmail(idUser, email, password, newEmail);
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void editUserName() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtUserName = new EditText(getContext());
        edtUserName.setText(userName);
        edtUserName.setHint("Enter your name");
        aler.setView(edtUserName);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtUserName.getText().toString())) {
                    showToast("username không được trống!");
                } else {
                    presenter.updateUserName(idUser, edtUserName.getText().toString());
                    txtUserName.setText(edtUserName.getText());
                }
            }
        });
        aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aler.create().show();
    }

    private void changePhoto() {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_PICK);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Take or select a photo";
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});
        startActivityForResult(chooserIntent, Constain.REQUEST_CODE_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constain.REQUEST_CODE_LOAD_IMAGE && resultCode == getActivity().RESULT_OK) {
            if (data.getAction() != null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = cropImage(bitmap);
                presenter.updatePhoto(bitmap, idUser);
                imgPhotoUser.setImageBitmap(bitmap);
            } else {
                Uri filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    bitmap = cropImage(bitmap);
                    presenter.updatePhoto(bitmap, idUser);
                    imgPhotoUser.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public Bitmap cropImage(Bitmap dstBmp) {
        Bitmap srcBmp = null;
        if (dstBmp.getWidth() >= dstBmp.getHeight()) {

            srcBmp = Bitmap.createBitmap(dstBmp, dstBmp.getWidth() / 2 - dstBmp.getHeight() / 2, 0, dstBmp.getHeight(), dstBmp.getHeight()
            );
        } else {
            srcBmp = Bitmap.createBitmap(dstBmp, 0, dstBmp.getHeight() / 2 - dstBmp.getWidth() / 2, dstBmp.getWidth(), dstBmp.getWidth()
            );
        }
        return srcBmp;
    }
}
