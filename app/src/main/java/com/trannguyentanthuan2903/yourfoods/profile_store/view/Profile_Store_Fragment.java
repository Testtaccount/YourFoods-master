package com.trannguyentanthuan2903.yourfoods.profile_store.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.comment.model.Comment;
import com.trannguyentanthuan2903.yourfoods.oop.BaseFragment;
import com.trannguyentanthuan2903.yourfoods.profile_store.model.Store;
import com.trannguyentanthuan2903.yourfoods.profile_store.presenter.UpdateStorePresenter;
import com.trannguyentanthuan2903.yourfoods.rating.model.Rate;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */

public class Profile_Store_Fragment extends BaseFragment implements View.OnClickListener {

    private String idStore, storeName, phoneNumber, address, email, sumRate;
    private FrameLayout layoutChangeCover, layoutChangeAvata;
    private TextView txtSumOrdered, txtSumShipped, txtStoreName, txtPhoneNumberStore, txtEmailStore, txtAddressStore;
    private LinearLayout layoutEditStoreName, layoutEditEmailStore, layoutEditPhoneNumberStore, layoutEditAddressStore, layoutEditPasswordStore, layoutPassword;
    public ImageView imgCover, imgAvata, imgEditAvata, imgEditCover;
    private RatingBar ratingBar;
    private DatabaseReference mData;
    private boolean isStore;
    private UpdateStorePresenter presenter;
    private Bitmap bitmapCover = null;
    private Bitmap bitmapAvata = null;
    private ArrayList<Float> arrRate;
    private int sum = 0;

    public Profile_Store_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isStore = getArguments().getBoolean(Constain.IS_STORE);
        idStore = getArguments().getString(Constain.ID_STORE);
        return inflater.inflate(R.layout.fragment_profile_store, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
        initInfo();
        addEvents();

    }

    private void addEvents() {
        layoutChangeCover.setOnClickListener(this);
        layoutChangeAvata.setOnClickListener(this);
        layoutEditStoreName.setOnClickListener(this);
        layoutEditEmailStore.setOnClickListener(this);
        layoutEditPhoneNumberStore.setOnClickListener(this);
        layoutEditAddressStore.setOnClickListener(this);
        layoutEditPasswordStore.setOnClickListener(this);
        imgEditAvata.setOnClickListener(this);
        imgEditCover.setOnClickListener(this);
    }

    private void initInfo() {
        if (isStore == false) {
            imgEditCover.setVisibility(View.INVISIBLE);
            imgEditAvata.setVisibility(View.INVISIBLE);
            layoutPassword.setVisibility(View.INVISIBLE);
            layoutEditStoreName.setVisibility(View.INVISIBLE);
            layoutEditEmailStore.setVisibility(View.INVISIBLE);
            layoutEditPhoneNumberStore.setVisibility(View.INVISIBLE);
            layoutEditAddressStore.setVisibility(View.INVISIBLE);
            layoutEditPasswordStore.setVisibility(View.INVISIBLE);

        }
        //get Info Store
        try {
            mData.child(Constain.STORES).child(idStore).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            Store store = dataSnapshot.getValue(Store.class);
                            if (store.getStoreName() != null) {
                                storeName = store.getStoreName();
                                txtStoreName.setText(storeName);
                            }
                            if (store.getPhoneNumber() != null) {
                                phoneNumber = store.getPhoneNumber();
                                txtPhoneNumberStore.setText(phoneNumber);
                            }
                            if (store.getLocation() != null) {
                                HashMap<String, Object> location = new HashMap<String, Object>();
                                location = store.getLocation();
                                address = (String) location.get(Constain.ADDRESS);
                                txtAddressStore.setText(address);
                            }
                            if (store.getEmail() != null) {
                                email = store.getEmail();
                                txtEmailStore.setText(email);
                            }
                            if (!store.getLinkPhotoStore().equals("")) {
                                Glide.with(getActivity())
                                        .load(store.getLinkPhotoStore())
                                        .fitCenter()
                                        .into(imgAvata);
                            }
                            if (!store.getLinkCoverStore().equals("")) {
                                Glide.with(getActivity())
                                        .load(store.getLinkCoverStore())
                                        .fitCenter()
                                        .into(imgCover);
                            }
                            txtSumShipped.setText(String.valueOf(store.getSumShipped()));
                            txtSumOrdered.setText(String.valueOf(store.getSumOrdered()));

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

        //get sum rate
        try {
            //get sumComment
            mData.child(Constain.STORES).child(idStore).child(Constain.RATES).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        sumRate = String.valueOf(dataSnapshot.getChildrenCount());
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //get sum rate
        mData.child(Constain.STORES).child(idStore).child(Constain.RATES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    try {
                        for (DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
                            arrRate.add(Float.valueOf(dSnapshot.getValue(Rate.class).getRate()));
                        }
                        ratingBar.setRating(Float.valueOf(sumRating()/(Float.valueOf(sumRate))));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private float sumRating() {
        float tong = 0;
        for (int i = 0; i < arrRate.size(); i++) {
            tong += arrRate.get(i);
        }
        return tong;
    }

    private void addControls() {
        mData = FirebaseDatabase.getInstance().getReference();
        presenter = new UpdateStorePresenter(getContext(), this);
        arrRate = new ArrayList<Float>();
        //Ánh xạ
        layoutChangeAvata = (FrameLayout) getActivity().findViewById(R.id.layoutChangeAvataStore);
        layoutChangeCover = (FrameLayout) getActivity().findViewById(R.id.layoutChangeCoverStore);
        layoutEditAddressStore = (LinearLayout) getActivity().findViewById(R.id.layoutEditAdressStore);
        layoutEditEmailStore = (LinearLayout) getActivity().findViewById(R.id.layoutEditEmailStore);
        layoutEditStoreName = (LinearLayout) getActivity().findViewById(R.id.layoutEditStoreName);
        layoutEditPasswordStore = (LinearLayout) getActivity().findViewById(R.id.layoutEditPasswordStore);
        layoutPassword = (LinearLayout) getActivity().findViewById(R.id.layoutPasswordStore);
        layoutEditPhoneNumberStore = (LinearLayout) getActivity().findViewById(R.id.layoutEditPhoneNumberStore);
        txtAddressStore = (TextView) getActivity().findViewById(R.id.txtAddress_profileStore);
        txtEmailStore = (TextView) getActivity().findViewById(R.id.txtEmail_profileStore);
        txtPhoneNumberStore = (TextView) getActivity().findViewById(R.id.txtPhoneNumber_profileStore);
        txtSumOrdered = (TextView) getActivity().findViewById(R.id.txtSumOredered_profileStore);
        txtSumShipped = (TextView) getActivity().findViewById(R.id.txtSumShipped_profileStore);
        txtStoreName = (TextView) getActivity().findViewById(R.id.txtStoreName_profileStore);
        imgCover = (ImageView) getActivity().findViewById(R.id.imgCoverStore);
        imgAvata = (ImageView) getActivity().findViewById(R.id.imgAvataStore);
        imgEditAvata = (ImageView) getActivity().findViewById(R.id.imgEditAvataStore);
        imgEditCover = (ImageView) getActivity().findViewById(R.id.imgEditCoverStore);
        ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar);
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.imgEditCoverStore) {
            changeCover();
        }
        if (view == R.id.imgEditAvataStore) {
            changeAvata();
        }
        if (view == R.id.layoutEditStoreName) {
            updateStoreName();
        }
        if (view == R.id.layoutEditEmailStore) {
            updateEmailStore();
        }
        if (view == R.id.layoutEditPhoneNumberStore) {
            updatePhoneNumberStore();
        }
        if (view == R.id.layoutEditAdressStore) {
            updateAddressStore();
        }
        if (view == R.id.layoutEditPasswordStore) {
            updatePasswordStore();
        }
    }

    private void updatePasswordStore() {
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
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPasswrod)) {
                    showToast("Password không được trống");
                    flag = false;
                }
                if (newPassword.length() > 0 && newPassword.length() < 6) {
                    flag = false;
                    showToast("Mật khẩu mới phải lớn hơn 6 ký tự");
                }
                if (!newPassword.equals(confirmPasswrod)) {
                    flag = false;
                    showToast("Mật khẩu mới không trùng khớp, vui lòng thử lại!");
                }
                if (flag) {
                    presenter.updatePasswordStore(email, password, newPassword);
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

    private void updateAddressStore() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtAddress = new EditText(getContext());
        edtAddress.setText(address);
        edtAddress.setHint("Enter your Address store ");
        aler.setView(edtAddress);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtAddress.getText().toString())) {
                    showToast("Address không được trống!");
                } else {
                    presenter.updateAddressStore(idStore, edtAddress.getText().toString());
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

    private void updatePhoneNumberStore() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtPhoneNumber = new EditText(getContext());
        edtPhoneNumber.setText(phoneNumber);
        edtPhoneNumber.setHint("Enter your phonenumber store ");
        aler.setView(edtPhoneNumber);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtPhoneNumber.getText().toString())) {
                    showToast("Phonenumber không được trống!");
                } else {
                    presenter.updatephoneNumberStore(idStore, edtPhoneNumber.getText().toString());
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

    private void updateEmailStore() {
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
                    showToast("Email hoặc password trống!");
                } else {

                    presenter.updateEmailStore(idStore, email, password, newEmail);
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

    private void updateStoreName() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setTitle("Chỉnh Sửa Thông Tin");
        final EditText edtStoreName = new EditText(getContext());
        edtStoreName.setText(storeName);
        edtStoreName.setHint("Enter your store name ");
        aler.setView(edtStoreName);
        aler.setCancelable(false);
        aler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtStoreName.getText().toString())) {
                    showToast("Username không được trống!");
                } else {
                    presenter.updateStoreName(idStore, edtStoreName.getText().toString());
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

    private void changeAvata() {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_PICK);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Take or select a photo";
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});
        startActivityForResult(chooserIntent, Constain.REQUEST_CODE_LOAD_IMAGE_AVATASTORE);
    }

    private void changeCover() {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_PICK);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Take or select a photo";
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});
        startActivityForResult(chooserIntent, Constain.REQUEST_CODE_LOAD_IMAGE_COVERSTORE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constain.REQUEST_CODE_LOAD_IMAGE_COVERSTORE && resultCode == getActivity().RESULT_OK) {
            if (data.getAction() != null) {
                bitmapCover = (Bitmap) data.getExtras().get("data");
                presenter.updateCoverStore(bitmapCover, idStore);
                imgCover.setImageBitmap(bitmapCover);
            } else {
                Uri filePath = data.getData();
                try {
                    bitmapCover = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    presenter.updateCoverStore(bitmapCover, idStore);
                    imgCover.setImageBitmap(bitmapCover);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if (requestCode == Constain.REQUEST_CODE_LOAD_IMAGE_AVATASTORE && resultCode == getActivity().RESULT_OK) {
            if (data.getAction() != null) {
                bitmapAvata = (Bitmap) data.getExtras().get("data");
                bitmapAvata = getResizedBitmap(cropImage(bitmapAvata), 225, 225);
                presenter.updateAvataStore(bitmapAvata, idStore);
            } else {
                Uri filePath = data.getData();
                try {
                    bitmapAvata = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    bitmapAvata = getResizedBitmap(cropImage(bitmapAvata), 225, 225);
                    presenter.updateAvataStore(bitmapAvata, idStore);
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

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}

