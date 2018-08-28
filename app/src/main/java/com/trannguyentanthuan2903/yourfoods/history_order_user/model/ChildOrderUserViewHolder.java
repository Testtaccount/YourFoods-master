package com.trannguyentanthuan2903.yourfoods.history_order_user.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.trannguyentanthuan2903.yourfoods.R;

/**
 * Created by Administrator on 10/16/2017.
 */

public class ChildOrderUserViewHolder extends ChildViewHolder {

    public RecyclerView recyclerView;

    public ChildOrderUserViewHolder(@NonNull View itemView) {
        super(itemView);

        recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerViewFlagProductShopOrder);
    }

}