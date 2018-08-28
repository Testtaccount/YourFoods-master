package com.trannguyentanthuan2903.yourfoods.product_list.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.product_list.model.OrderProductSubmitter;

import java.util.HashMap;

/**
 * Created by Administrator on 10/17/2017.
 */

public class OrderProductPresenter {
    private DatabaseReference mData;
    private OrderProductSubmitter submitter;

    public OrderProductPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new OrderProductSubmitter(mData);
    }
    public void orderProduct (String idStore, String idUser, String idCategory, String idProduct,  HashMap<String, Object> myMap){
        submitter.orderProduct(idStore, idUser, idCategory, idProduct, myMap);
    }
}
