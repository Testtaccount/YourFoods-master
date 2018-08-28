package com.trannguyentanthuan2903.yourfoods.history_ship_store.model;

import android.support.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by Administrator on 10/16/2017.
 */


public class ParentHistoryStore implements Parent<ListOrderProduct>, Comparable<ParentHistoryStore> {

    private List<ListOrderProduct> mChildrenProduct;

    public ParentInfoUserOrder getInfoUserOrder() {
        return infoUserOrder;
    }

    public ParentHistoryStore(List<ListOrderProduct> mChildrenProduct, ParentInfoUserOrder infoUserOrder) {
        this.mChildrenProduct = mChildrenProduct;
        this.infoUserOrder = infoUserOrder;
    }

    public void setInfoUserOrder(ParentInfoUserOrder infoUserOrder) {
        this.infoUserOrder = infoUserOrder;
    }

    private ParentInfoUserOrder infoUserOrder;


    @Override
    public List<ListOrderProduct> getChildList() {
        return mChildrenProduct;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    @Override
    public int compareTo(@NonNull ParentHistoryStore o) {
        return getInfoUserOrder().getStatus() - o.getInfoUserOrder().getStatus();
    }
}
