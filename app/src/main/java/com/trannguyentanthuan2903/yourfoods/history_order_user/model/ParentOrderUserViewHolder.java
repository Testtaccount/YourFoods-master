package com.trannguyentanthuan2903.yourfoods.history_order_user.model;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.trannguyentanthuan2903.yourfoods.R;

/**
 * Created by Administrator on 10/16/2017.
 */

public class ParentOrderUserViewHolder extends ParentViewHolder {

    private ImageView imgAvataShopItemOrderHistory;
    private ImageView imgStatusUser;
    private TextView txtUserNameShopItemOrderList;
    private TextView txtPhoneNumberShopItemOrderList;
    private TextView txtAddressShop;
    private TextView txtTimeOrderShop;

    public ParentOrderUserViewHolder(@NonNull View itemView) {
        super(itemView);

        imgAvataShopItemOrderHistory = (ImageView) itemView.findViewById(R.id.imgAvataShop_itemOrderHistory);
        imgStatusUser = (ImageView) itemView.findViewById(R.id.imgStatusShipShop);
        txtUserNameShopItemOrderList = (TextView) itemView.findViewById(R.id.txtUserShopName_itemOrderList);
        txtPhoneNumberShopItemOrderList = (TextView) itemView.findViewById(R.id.txtPhoneNumberShop_itemOrderList);
        txtAddressShop = (TextView) itemView.findViewById(R.id.txtAddressShop_itemOrderList);
        txtTimeOrderShop = (TextView) itemView.findViewById(R.id.txtTimeOrderShop);
    }

    public ImageView getImgAvataShopItemOrderHistory() {
        return imgAvataShopItemOrderHistory;
    }

    public ImageView getImgStatusUser() {
        return imgStatusUser;
    }

    public void setNameTxtShopUserNameItemOrderList(String txtUserNameItemOrderList) {
        this.txtUserNameShopItemOrderList.setText(txtUserNameItemOrderList);
    }

    public void setNameTxtPhoneNumberShopItemOrderList(String txtPhoneNumberItemOrderList) {
        this.txtPhoneNumberShopItemOrderList.setText(txtPhoneNumberItemOrderList);
    }

    public void setNameTxtAddressShop(String txtAddressUser) {
        this.txtAddressShop.setText(txtAddressUser);
    }

    public void setNameTxtTimeOrderShop(String txtTimeOrder) {
        this.txtTimeOrderShop.setText(txtTimeOrder);
    }
}
