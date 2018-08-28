package com.trannguyentanthuan2903.yourfoods.my_cart.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.my_cart.model.MyCart;
import com.trannguyentanthuan2903.yourfoods.my_cart.model.MyCartSubmitter;

/**
 * Created by Administrator on 10/17/2017.
 */

public class MyCartPresenter {
    private DatabaseReference mData;
    private MyCartSubmitter submitter;

    public MyCartPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new MyCartSubmitter(mData);
    }
    public void addProductToCart (String idUser, String idMyCart, String idStore, MyCart myCart){
        submitter.addProductToCart(idUser, idMyCart, idStore, myCart);
    }
    public void deleteProductOrder (String idUser, String idMyCart, String idStore){
        submitter.deleteProductOrder(idUser, idMyCart, idStore);
    }
    public void updateCountProduct (String idUser, String idMyCart, String idStore, int count){
        submitter.updateCountProduct(idUser, idMyCart, idStore, count);
    }
    public void updatePrice (String idUser, String idMyCart, String idStore, float price){
        submitter.updatePrice(idUser, idMyCart, idStore, price);
    }
}
