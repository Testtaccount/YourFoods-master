package com.trannguyentanthuan2903.yourfoods.product_list.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.trannguyentanthuan2903.yourfoods.product.model.Product;

import java.util.List;

/**
 * Created by Administrator on 10/17/2017.
 */

public class GroupProduct implements Parent<Product> {

    private List<Product> mChildrenList;
    private String title;

    public GroupProduct(String title , List<Product> mChildrenList) {
        this.title = title;
        this.mChildrenList = mChildrenList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Product> getChildList() {
        return mChildrenList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }
}