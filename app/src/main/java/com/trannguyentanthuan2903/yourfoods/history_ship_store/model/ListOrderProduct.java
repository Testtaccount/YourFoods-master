package com.trannguyentanthuan2903.yourfoods.history_ship_store.model;

import com.trannguyentanthuan2903.yourfoods.product.model.OrderProduct;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/16/2017.
 */

public class ListOrderProduct {
    public ArrayList<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(ArrayList<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    private ArrayList<OrderProduct> orderProductList;

    public ListOrderProduct(ArrayList<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }
}