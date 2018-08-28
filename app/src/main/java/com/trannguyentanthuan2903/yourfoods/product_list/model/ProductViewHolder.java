package com.trannguyentanthuan2903.yourfoods.product_list.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trannguyentanthuan2903.yourfoods.R;

/**
 * Created by Administrator on 10/17/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public ImageView img;
    public TextView txtName;
    public TextView txtPrice;
    public ProductViewHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img);
        txtName = (TextView) itemView.findViewById(R.id.txtName);
        txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);

    }
}
