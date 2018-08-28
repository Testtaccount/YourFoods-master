package com.trannguyentanthuan2903.yourfoods.product.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.trannguyentanthuan2903.yourfoods.product.presenter.ProductPresenter;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 10/17/2017.
 */

public class EditProductFragment extends BaseFragment implements View.OnClickListener {

    private EditText edtProductName, edtDescribeProduct, edtPrice;
    private Button btnChooseImage, btnEditProduct, btnDeleteProduct;
    public ImageView imgProduct;
    private ArrayList<Category> arrCategory;
    private TextView txtCategory;
    private DatabaseReference mData;
    private Product product;
    private ProductPresenter presenter;
    private String idStore, idCategory, idProduct, productName, describeProduct, priceProduct;
    private String linkPhotoProduct;
    private int sumProduct;
    private Bitmap bitmap = null;

    public EditProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getArguments().getSerializable(Constain.PRODUCTS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_product, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControl();
        initInfo();
        addEvent();
    }

    private void addEvent() {
        btnEditProduct.setOnClickListener(this);
        btnChooseImage.setOnClickListener(this);
        btnDeleteProduct.setOnClickListener(this);
    }

    private void initInfo() {
        try {
            //get all category
            mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            arrCategory.clear();
                            for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                Category category = dt.getValue(Category.class);
                                arrCategory.add(category);
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
            //get Info Product
            if (product != null) {
                productName = product.getProductName();
                edtProductName.setText(productName);
                describeProduct = product.getInfoProduct();
                edtDescribeProduct.setText(describeProduct);
                priceProduct = String.valueOf(Math.round(product.getPrice()));
                edtPrice.setText(priceProduct);
                idCategory = product.getIdCategory();
                idProduct = product.getIdProduct();
                linkPhotoProduct = product.getLinkPhotoProduct();
                if (product.getLinkPhotoProduct() != null) {
                    Glide.with(getContext())
                            .load(linkPhotoProduct)
                            .into(imgProduct);
                }
            }
            //get Category
            mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            Category category = dataSnapshot.getValue(Category.class);
                            txtCategory.setText(category.getCategoryName());
                        }
                        catch (Exception ex){

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //get SumProduct
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

    private void addControl() {
        edtProductName = (EditText) getActivity().findViewById(R.id.edtProductName_editproduct);
        edtDescribeProduct = (EditText) getActivity().findViewById(R.id.edtDescribeProduct_editproduct);
        edtPrice = (EditText) getActivity().findViewById(R.id.edtPrice_editproduct);
        btnChooseImage = (Button) getActivity().findViewById(R.id.btnChooseimg_editproduct);
        btnEditProduct = (Button) getActivity().findViewById(R.id.btnEditProduct);
        btnDeleteProduct = (Button) getActivity().findViewById(R.id.btnDeleteProduct);
        imgProduct = (ImageView) getActivity().findViewById(R.id.product_editproduct);
        txtCategory = (TextView) getActivity().findViewById(R.id.txtCategory_editproduct);
        arrCategory = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference();
        idStore = getActivity().getIntent().getStringExtra(Constain.ID_STORE);
        presenter = new ProductPresenter((MainStoreActivity) getContext());
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.btnChooseimg_editproduct) {
            showImage();
        }
        if (view == R.id.btnEditProduct) {
            updateProduct();
        }
        if (view == R.id.btnDeleteProduct){
            deleteProduct ();
        }
    }

    private void deleteProduct() {
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setCancelable(false);
        aler.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
        aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sumProduct +=1;
                presenter.deleteProduct(idStore, idCategory, idProduct, sumProduct);
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

    private void updateProduct (){
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setCancelable(false);
        aler.setMessage("Bạn có chắc chắn muốn thay đổi?");
        aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editProduct();
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
    private void editProduct() {
        boolean flag_isVail = true;
        String productName_update = edtProductName.getText().toString().trim();
        String describeProduct_update = edtDescribeProduct.getText().toString().trim();
        String priceProduct_update = edtPrice.getText().toString().trim();
        if (TextUtils.isEmpty(productName_update)) {
            flag_isVail = false;
            edtProductName.setError("Bắt buộc");
        } else {
            edtProductName.setError(null);
        }
        if (TextUtils.isEmpty(describeProduct_update)) {
            flag_isVail = false;
            edtDescribeProduct.setError("Bắt buộc");
        } else {
            edtDescribeProduct.setError(null);
        }
        if (TextUtils.isEmpty(priceProduct_update)) {
            flag_isVail = false;
            edtPrice.setError("Bắt buộc");
        } else {
            edtPrice.setError(null);
        }
        //Edit Product
        if (flag_isVail) {
            if (bitmap != null){
                presenter.updateProduct(idStore, idCategory, idProduct, bitmap, productName_update, Float.parseFloat(priceProduct_update), describeProduct_update);
            }
            else {
                Product product_update = new Product(idProduct, idCategory, productName_update, linkPhotoProduct, 0, Float.parseFloat(priceProduct_update), describeProduct_update, true);
                presenter.updateProductNonLink(idStore, idCategory, idProduct, product_update);
            }
        }
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

    private void showImage() {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_PICK);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Take or select a photo";
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});
        startActivityForResult(chooserIntent, Constain.REQUEST_CODE_LOAD_IMAGE);
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
