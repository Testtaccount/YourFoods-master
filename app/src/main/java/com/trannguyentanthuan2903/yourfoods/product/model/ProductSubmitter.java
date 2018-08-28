package com.trannguyentanthuan2903.yourfoods.product.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.trannguyentanthuan2903.yourfoods.main.view.MainStoreActivity;
import com.trannguyentanthuan2903.yourfoods.product.view.CreateProductFragment;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by Administrator on 10/17/2017.
 */

public class ProductSubmitter {
    private DatabaseReference mData;
    private StorageReference mStorage;
    private String linkPhotoProduct = "";
    private MainStoreActivity view;
    private CreateProductFragment createProductFragment;

    public ProductSubmitter(MainStoreActivity view, DatabaseReference mData, StorageReference mStorage) {
        this.view = view;
        this.mData = mData;
        this.mStorage = mStorage;
        createProductFragment = new CreateProductFragment();
    }

    public void createProduct(Bitmap bitmap, final String idStore, final String idCategory, final String idProduct, final String productName, final String describeProduct, final float price, final int sumProduct) {
        view.showProgressDialog();
        StorageReference mountainsRef = mStorage.child(Constain.STORES).child(idStore).child(idCategory).child(idProduct);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                linkPhotoProduct = String.valueOf(downloadUrl);
                Product product = new Product(idProduct, idCategory, productName, linkPhotoProduct, 0, price, describeProduct, true);
                if (!linkPhotoProduct.equals("")) {
                    HashMap<String, Object> myMap = product.putMap();
                    mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).setValue(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                view.hideProgressDialog();
                                view.showToast("Tạo sản phẩm thành công");
                                updateSumProductStore(idStore, sumProduct);
                            }
                            else {
                                view.hideProgressDialog();
                                view.showToast("Tạo sản phẩm không thành công, vui lòng thử lại");
                            }
                        }
                    });
                } else {
                    view.hideProgressDialog();
                    view.showToast("Tạo sản phẩm không thành công, vui lòng thử lại");
                }
            }
        });
    }
    public void deleteProduct (final String idStore, final String idCategory, final String idProduct, final int sumProduct){
        view.showProgressDialog();
        mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    view.hideProgressDialog();
                    StorageReference mountainsRef = mStorage.child(Constain.STORES).child(idStore).child(idCategory).child(idProduct);
                    mountainsRef.delete();
                    updateSumProductStore(idStore, sumProduct);
                    view.showToast("Xóa sản phẩm thành công!");
                    view.moveToProductFragment(idStore);
                }
                else {
                    view.hideProgressDialog();
                    view.showToast("Xóa sản không phẩm thành công!");
                }
            }
        });
    }
    public void updateProductNonLink (final String idStore, String idCategory, String idProduct, Product product){
        view.showProgressDialog();
        HashMap<String, Object> myMap = new HashMap<>();
        myMap = product.putMap();
        mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).setValue(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    view.hideProgressDialog();
                    view.showToast("Cập nhật sản phẩm thành công!");
                    view.moveToProductFragment(idStore);
                }
                else {
                    view.hideProgressDialog();
                    view.showToast("Cập nhật sản không phẩm thành công!");
                }
            }
        });
    }
    public void updateProduct (final String idStore, final String idCategory, final String idProduct, Bitmap bitmap, final String productName, final float price, final String describeProduct){
        view.showProgressDialog();
        StorageReference mountainsRef = mStorage.child(Constain.STORES).child(idStore).child(idCategory).child(idProduct);
        mountainsRef.delete();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                linkPhotoProduct = "";
                linkPhotoProduct = String.valueOf(downloadUrl);
                if (!linkPhotoProduct.equals("")){
                    Product product = new Product(idProduct, idCategory, productName, linkPhotoProduct, 0, price, describeProduct, true);
                    mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).child(Constain.PRODUCTS).child(idProduct).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                view.hideProgressDialog();
                                view.showToast("Cập nhật sản phẩm thành công!");
                                view.moveToProductFragment(idStore);
                            }
                            else {
                                view.hideProgressDialog();
                                view.showToast("Cập nhật sản phẩm không thành công!");
                            }
                        }
                    });
                }
            }
        });
    }
    public void updateSumProductStore (String idStore, int sumProduct){
        mData.child(Constain.STORES).child(idStore).child(Constain.SUM_PRODUCT).setValue(sumProduct);
    }
}
