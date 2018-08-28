package com.trannguyentanthuan2903.yourfoods.history_order_user.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.trannguyentanthuan2903.yourfoods.history_ship_store.model.ListOrderProduct;

import java.util.List;

/**
 * Created by Administrator on 10/16/2017.
 */

public class ParentHistoryOrderUser implements Parent<ListOrderProduct>, Comparable<ParentHistoryOrderUser>{

    private List<ListOrderProduct> mChildrenProduct;

    private ParentInfoShopOrder infoShopOrder;

    public ParentHistoryOrderUser(List<ListOrderProduct> mChildrenProduct, ParentInfoShopOrder infoShopOrder) {
        this.mChildrenProduct = mChildrenProduct;
        this.infoShopOrder = infoShopOrder;
    }

    public ParentInfoShopOrder getInfoShopOrder() {
        return infoShopOrder;
    }

    public void setInfoShopOrder(ParentInfoShopOrder infoShopOrder) {
        this.infoShopOrder = infoShopOrder;
    }

    @Override
    public List<ListOrderProduct> getChildList() {
        return mChildrenProduct;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    @Override
    public int compareTo(ParentHistoryOrderUser parentHistoryOrderUser) {
        return  - infoShopOrder.getStatusShop() + parentHistoryOrderUser.getInfoShopOrder().getStatusShop();
    }
}
