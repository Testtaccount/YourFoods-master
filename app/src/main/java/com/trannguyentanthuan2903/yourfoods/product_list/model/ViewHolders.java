package com.trannguyentanthuan2903.yourfoods.product_list.model;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.trannguyentanthuan2903.yourfoods.R;

/**
 * Created by Administrator on 10/17/2017.
 */

public class ViewHolders extends ChildViewHolder {


    ImageView img;
    TextView txtName;
    TextView txtPrice;
    TextView txtEdit;
    Button btnGetit;



    public ViewHolders(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img);
        btnGetit = (Button) itemView.findViewById(R.id.btnGetit);
        txtName = (TextView) itemView.findViewById(R.id.txtName);
        txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
        txtEdit = (TextView) itemView.findViewById(R.id.txtEditProduct);
    }

    public void setTxtNameName(String name){
        txtName.setText(name);
    }

    public void setTxtPriceName(String name){
        txtPrice.setText(name);
    }

    public ImageView getImg() {
        return img;
    }

}
