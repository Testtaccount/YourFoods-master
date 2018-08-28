package com.trannguyentanthuan2903.yourfoods.product_list.model;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.trannguyentanthuan2903.yourfoods.R;

/**
 * Created by Administrator on 10/17/2017.
 */

public class GroupProductViewHolder extends ParentViewHolder {

    private TextView txtCategoryProduct;
    public GroupProductViewHolder(View itemView) {
        super(itemView);
        txtCategoryProduct = (TextView) itemView.findViewById(R.id.categoryTitle);
    }

    public void setTxtCategoryProductName(String name){
        txtCategoryProduct.setText(name);
    }
}
