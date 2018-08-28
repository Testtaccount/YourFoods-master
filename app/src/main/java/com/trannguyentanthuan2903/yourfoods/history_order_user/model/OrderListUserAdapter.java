package com.trannguyentanthuan2903.yourfoods.history_order_user.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.history_ship_store.model.ListOrderProduct;
import com.trannguyentanthuan2903.yourfoods.history_ship_store.model.OrderItemAdapter;

import java.util.List;

/**
 * Created by Administrator on 10/16/2017.
 */

public class OrderListUserAdapter extends ExpandableRecyclerAdapter<ParentHistoryOrderUser , ListOrderProduct, ParentOrderUserViewHolder,
        ChildOrderUserViewHolder> {

    List<ParentHistoryOrderUser> parentList;
    LayoutInflater inflater ;
    Context mContext;

    public OrderListUserAdapter(Context mContext , @NonNull List<ParentHistoryOrderUser> parentList) {
        super(parentList);
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.parentList = parentList;
    }


    @Override
    public ParentOrderUserViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.item_user_history_order_parent, parentViewGroup, false);
        return new ParentOrderUserViewHolder(recipeView);
    }


    @Override
    public ChildOrderUserViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.item_user_history_order_child, childViewGroup, false);
        return new ChildOrderUserViewHolder(recipeView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull ParentOrderUserViewHolder parentViewHolder, int parentPosition, @NonNull ParentHistoryOrderUser parent) {
        parentViewHolder.setNameTxtAddressShop(parent.getInfoShopOrder().getAddressShop());
        parentViewHolder.setNameTxtShopUserNameItemOrderList(parent.getInfoShopOrder().getNameShop());
        parentViewHolder.setNameTxtPhoneNumberShopItemOrderList(parent.getInfoShopOrder().getPhoneNumberShop());
        parentViewHolder.setNameTxtTimeOrderShop(parent.getInfoShopOrder().getTimeOrderShop());
        String linkPhotoUser = "";
        if(parent.getInfoShopOrder().getLinkPhotoShop() != null)
            linkPhotoUser = parent.getInfoShopOrder().getLinkPhotoShop();
        if(!linkPhotoUser.equals("")){
            Glide.with(mContext).load(linkPhotoUser).into(parentViewHolder.getImgAvataShopItemOrderHistory());
        }
        if (parent.getInfoShopOrder().getStatusShop() == 0){
            parentViewHolder.getImgStatusUser().setVisibility(View.INVISIBLE);
        }
        if (parent.getInfoShopOrder().getStatusShop() == 1){
            parentViewHolder.getImgStatusUser().setVisibility(View.VISIBLE);
            parentViewHolder.getImgStatusUser().setImageResource(R.drawable.imgshipped);
        }
        if (parent.getInfoShopOrder().getStatusShop() == 2){
            parentViewHolder.getImgStatusUser().setVisibility(View.VISIBLE);
            parentViewHolder.getImgStatusUser().setImageResource(R.drawable.imgcanceled);
        }
    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildOrderUserViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull ListOrderProduct child) {
        childViewHolder.recyclerView.setAdapter(new OrderItemAdapter(child.getOrderProductList()));
        childViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }
}
