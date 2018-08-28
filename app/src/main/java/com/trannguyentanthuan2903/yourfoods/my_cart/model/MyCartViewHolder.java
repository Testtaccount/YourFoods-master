package com.trannguyentanthuan2903.yourfoods.my_cart.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.trannguyentanthuan2903.yourfoods.R;

/**
 * Created by Administrator on 10/17/2017.
 */

public class MyCartViewHolder extends RecyclerView.ViewHolder {
    public TextView txtProductName, txtPrice, txtCountProduct, txtDeleteProduct;
    public ImageView imgPhotoProduct;
    public SwipeLayout swipeLayout;
    public MyCartViewHolder(View itemView) {
        super(itemView);
        txtDeleteProduct = (TextView) itemView.findViewById(R.id.txtDeleteProduct);
        txtCountProduct = (TextView) itemView.findViewById(R.id.txtCountProduct_itemmyorder);
        txtPrice = (TextView) itemView.findViewById(R.id.txtPrice_itemmyorder);
        txtProductName = (TextView) itemView.findViewById(R.id.txtProductName_itemmyorder);
        imgPhotoProduct = (ImageView) itemView.findViewById(R.id.imgPhotoProduct_itemcart);

        swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe_myorder);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, itemView.findViewById(R.id.layout_left_myorder));
        swipeLayout.setRightSwipeEnabled(false);
        swipeLayout.setLeftSwipeEnabled(true);
    }
}
