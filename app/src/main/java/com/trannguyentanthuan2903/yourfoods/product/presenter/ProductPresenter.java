package com.trannguyentanthuan2903.yourfoods.product.presenter;

import android.graphics.Bitmap;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.trannguyentanthuan2903.yourfoods.main.view.MainStoreActivity;
import com.trannguyentanthuan2903.yourfoods.product.model.Product;
import com.trannguyentanthuan2903.yourfoods.product.model.ProductSubmitter;

/**
 * Created by Administrator on 10/17/2017.
 */

public class ProductPresenter {
    private DatabaseReference mData;
    private ProductSubmitter submitter;
    private StorageReference mStorage;
    private MainStoreActivity view;

    public ProductPresenter(MainStoreActivity view) {
        this.view = view;
        mData = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();;
        submitter = new ProductSubmitter(view, mData, mStorage);
    }
    public void createProduct (Bitmap bitmap, String idStore, String idCategory, String idProduct, String productName, String describeProduct, float price, int sumProduct){
        submitter.createProduct(bitmap, idStore, idCategory, idProduct, productName, describeProduct, price, sumProduct);
    }
    public void deleteProduct (String idStore, String idCategory, String idProduct, int sumProduct){
        submitter.deleteProduct(idStore, idCategory, idProduct, sumProduct);
    }
    public void updateProductNonLink (String idStore, String idCategory, String idProduct, Product product){
        submitter.updateProductNonLink(idStore, idCategory, idProduct, product);
    }
    public void updateProduct (String idStore, String idCategory, String idProduct, Bitmap bitmap, String productName, float price, final String describeProduct){
        submitter.updateProduct(idStore, idCategory, idProduct, bitmap, productName, price, describeProduct);
    }
}
