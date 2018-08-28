package com.trannguyentanthuan2903.yourfoods.history_ship_store.model;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.trannguyentanthuan2903.yourfoods.R;

/**
 * Created by Administrator on 10/16/2017.
 */


public class ParentOrderViewHolder extends ParentViewHolder {

    private ImageView imgAvataUserItemOrderHistory;
    private ImageView imgStatusShip;
    private TextView txtUserNameItemOrderList;
    private TextView txtPhoneNumberItemOrderList;
    private TextView txtAddressUser;
    private TextView txtTimeOrder;

    public ParentOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        imgAvataUserItemOrderHistory = (ImageView) itemView.findViewById(R.id.imgAvataUser_itemOrderHistory);
        imgStatusShip = (ImageView) itemView.findViewById(R.id.imgStatusShip);
        txtUserNameItemOrderList = (TextView) itemView.findViewById(R.id.txtUserName_itemOrderList);
        txtPhoneNumberItemOrderList = (TextView) itemView.findViewById(R.id.txtPhoneNumber_itemOrderList);
        txtAddressUser = (TextView) itemView.findViewById(R.id.txtAddress_itemOrderList);
        txtTimeOrder = (TextView) itemView.findViewById(R.id.txtTimeOrder);
    }

    public ImageView getImgAvataUserItemOrderHistory() {
        return imgAvataUserItemOrderHistory;
    }

    public ImageView getImgStatusShip() {
        return imgStatusShip;
    }

    public void setNameTxtUserNameItemOrderList(String txtUserNameItemOrderList) {
        this.txtUserNameItemOrderList.setText(txtUserNameItemOrderList);
    }

    public void setNameTxtPhoneNumberItemOrderList(String txtPhoneNumberItemOrderList) {
        this.txtPhoneNumberItemOrderList.setText(txtPhoneNumberItemOrderList);
    }

    public void setNameTxtAddressUser(String txtAddressUser) {
        this.txtAddressUser.setText(txtAddressUser);
    }

    public void setNameTxtTimeOrder(String txtTimeOrder) {
        this.txtTimeOrder.setText(txtTimeOrder);
    }
}
