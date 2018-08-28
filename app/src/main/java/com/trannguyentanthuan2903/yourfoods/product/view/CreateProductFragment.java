package com.trannguyentanthuan2903.yourfoods.product.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.category.model.Category;
import com.trannguyentanthuan2903.yourfoods.main.view.MainStoreActivity;
import com.trannguyentanthuan2903.yourfoods.oop.BaseFragment;
import com.trannguyentanthuan2903.yourfoods.product.model.Product;
import com.trannguyentanthuan2903.yourfoods.product.model.SpinnerAdapter;
import com.trannguyentanthuan2903.yourfoods.product.presenter.ProductPresenter;
import com.trannguyentanthuan2903.yourfoods.profile_store.presenter.UpdateStorePresenter;
import com.trannguyentanthuan2903.yourfoods.profile_store.view.Profile_Store_Fragment;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 10/17/2017.
 */

public class CreateProductFragment extends BaseFragment implements View.OnClickListener {

    private EditText edtProductName, edtDescribeProduct, edtPrice;
    private Button btnChooseImage, btnCreateProduct;
    public ImageView imgProduct,imgSpinnerCategory;
    private Spinner spinnerCategory;
    private  int sumProduct;
    private ProductPresenter presenter;
    private UpdateStorePresenter storePresenter;

    private SpinnerAdapter adapter;
    private ArrayList<Category> arrCategory;
    private DatabaseReference mData;
    private String idStore, idCategory, idProduct;
    private Bitmap bitmap = null;

    public  CreateProductFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initInfo() {
        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            arrCategory.clear();
                            for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                Category category = dt.getValue(Category.class);
                                arrCategory.add(category);
                                adapter.notifyDataSetChanged();
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
            //get sumProduct
            mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    sumProduct = 0;
                    if (dataSnapshot.getValue() != null) {
                        try {
                            for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(dt.getKey()).child(Constain.PRODUCTS).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue() != null){
                                            sumProduct += dataSnapshot.getChildrenCount();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
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

    private void addEvent() {
        btnChooseImage.setOnClickListener(this);
        btnCreateProduct.setOnClickListener(this);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category = arrCategory.get(position);
                idCategory = category.getIdCategory();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
    }

    private void addControl() {
        edtProductName = (EditText) getActivity().findViewById(R.id.edtProductName);
        edtDescribeProduct = (EditText) getActivity().findViewById(R.id.edtDescribeProduct);
        edtPrice = (EditText) getActivity().findViewById(R.id.edtPrice);
        btnChooseImage = (Button) getActivity().findViewById(R.id.btnChooseimg);
        btnCreateProduct = (Button) getActivity().findViewById(R.id.btnCreateProduct);
        imgProduct = (ImageView) getActivity().findViewById(R.id.imgProduct);
        imgSpinnerCategory = (ImageView) getActivity().findViewById(R.id.imgSpinnerCategory);
        spinnerCategory = (Spinner) getActivity().findViewById(R.id.spinnerCategory);
        mData = FirebaseDatabase.getInstance().getReference();
        presenter = new ProductPresenter((MainStoreActivity) (this.getActivity()));
        idStore = getActivity().getIntent().getStringExtra(Constain.ID_STORE);
        arrCategory = new ArrayList<>();
        adapter = new SpinnerAdapter(arrCategory, getActivity());
        spinnerCategory.setAdapter(adapter);
        Profile_Store_Fragment fragment = new Profile_Store_Fragment();
        storePresenter = new UpdateStorePresenter(getContext(), fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_product, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControl();
        initInfo();
        addEvent();
    }
    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.btnChooseimg) {
            showImage();
        }
        if (view == R.id.btnCreateProduct) {
            createProduct();
        }
    }

    private void createProduct() {
        String flag_nameProduct = edtProductName.getText().toString();
        final String price = edtPrice.getText().toString();
        final String describeProduct = edtDescribeProduct.getText().toString();
        boolean isVail = true;
        if (TextUtils.isEmpty(flag_nameProduct)) {
            isVail = false;
            edtProductName.setError("Bắt Buộc");
        } else {
            edtProductName.setError(null);
        }
        if (TextUtils.isEmpty(price)) {
            isVail = false;
            edtPrice.setError("Bắt Buộc");
        } else {
            edtPrice.setError(null);
        }
        if (bitmap == null){
            isVail = false;
            showToast("Bạn chưa chọn hình!");
        }
        if (isVail) {
            String[] tu = flag_nameProduct.trim().split(" ");
            String flag_nameProduct2 = "";
            for (int i = 0; i < tu.length; i++) {
                tu[i] = Character.toUpperCase(tu[i].charAt(0)) + tu[i].substring(1) + " ";
                flag_nameProduct2 += tu[i];
            }
            final String productName = flag_nameProduct2.trim();
            try {
                mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.getValue() != null) {
                                boolean flag_id = true;
                                StringBuilder mBuilder = new StringBuilder();
                                while (flag_id == true) {
                                    Random ra = new Random();
                                    for (int i = 0; i <= 10; i++) {
                                        String number = String.valueOf(ra.nextInt(10));
                                        mBuilder.append(number);
                                    }
                                    idProduct = mBuilder.toString();
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
                                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                        Product product = dt.getValue(Product.class);
                                        if (productName.equals(product.getProductName())) {
                                            flag_name = false;
                                            showToast("Tên sản phẩm đã có,vui lòng thử lại");
                                            hideProgressDialog();
                                            edtProductName.requestFocus();
                                            break;
                                        }
                                    }
                                    if (flag_name) {
                                        sumProduct += 1;
                                        presenter.createProduct(bitmap, idStore, idCategory, idProduct, productName, describeProduct, Float.parseFloat(price), sumProduct);
                                        edtProductName.setText("");
                                        edtPrice.setText("");
                                        edtDescribeProduct.setText("");
                                        bitmap = null;
                                        imgProduct.setImageResource(R.drawable.store);
                                    }
                                }
                                catch (Exception ex){

                                }
                            } else {
                                StringBuilder mBuilder = new StringBuilder();
                                Random ra = new Random();
                                for (int i = 0; i <= 10; i++) {
                                    String number = String.valueOf(ra.nextInt(10));
                                    mBuilder.append(number);
                                }
                                idProduct = mBuilder.toString();
                                sumProduct += 1;
                                presenter.createProduct(bitmap, idStore, idCategory, idProduct, productName, describeProduct, Float.parseFloat(price), sumProduct);
                                edtProductName.setText("");
                                edtPrice.setText("");
                                edtDescribeProduct.setText("");
                                bitmap = null;
                                imgProduct.setImageResource(R.drawable.store);
                            }
                        } catch (Exception ex) {
                            hideProgressDialog();
                            showToast("Tạo sản phẩm không thành công,vui lòng thử lại");
                            ex.printStackTrace();
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

    private void showImage() {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_PICK);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Take or select a photo";
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhotoIntent });
        startActivityForResult(chooserIntent, Constain.REQUEST_CODE_LOAD_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constain.REQUEST_CODE_LOAD_IMAGE && resultCode == getActivity().RESULT_OK ){
            if (data.getAction() != null){
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = cropImage(bitmap);
                imgProduct.setImageBitmap(bitmap);
            }
            else {
                Uri filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    bitmap = cropImage(bitmap);
                    imgProduct.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public Bitmap cropImage (Bitmap dstBmp) {
        Bitmap srcBmp = null;
        if (dstBmp.getWidth() >= dstBmp.getHeight()) {

            srcBmp = Bitmap.createBitmap(dstBmp, dstBmp.getWidth() / 2 - dstBmp.getHeight() / 2, 0, dstBmp.getHeight(), dstBmp.getHeight()
            );
        } else {
            srcBmp = Bitmap.createBitmap(dstBmp, 0, dstBmp.getHeight() / 2 - dstBmp.getWidth() / 2, dstBmp.getWidth(), dstBmp.getWidth()
            );
        }
        return  srcBmp;
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
