package com.trannguyentanthuan2903.yourfoods.favorites.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trannguyentanthuan2903.yourfoods.R;

/**
 * Created by Administrator on 10/31/2017.
 */

public class FavoriteViewHolder extends RecyclerView.ViewHolder {

    public TextView txtStoreName, txtSumfavorite, txtSumShipped, txtAddress, txtTimeWork, txtDistance, txtSumProduct, txtStatus,txtEmailStore,txtSumComment;
    public ImageView imgHeart, imgPhotoStore;
    public LinearLayout linearLayout,imgComment;
    public CardView cardView;
    public Button btnViewProfileStore, btnViewOrder;

    public FavoriteViewHolder(View itemView) {
        super(itemView);
        txtStoreName = (TextView) itemView.findViewById(R.id.txtStoreName_itemstore);
        txtSumfavorite = (TextView) itemView.findViewById(R.id.txtHeart);
        txtSumShipped = (TextView) itemView.findViewById(R.id.txtSumShipped);
        txtAddress = (TextView) itemView.findViewById(R.id.txtAddress_itemstore);
        txtTimeWork = (TextView) itemView.findViewById(R.id.txtTimeWork_itemstore);
        txtDistance = (TextView) itemView.findViewById(R.id.txtDistanceFromUserToStore);
        txtSumProduct = (TextView) itemView.findViewById(R.id.txtSumProduct);
        txtStatus = (TextView) itemView.findViewById(R.id.txtStatus_itemStore);
        txtEmailStore =(TextView) itemView.findViewById(R.id.txtEmail_itemStore);
        txtSumComment = (TextView) itemView.findViewById(R.id.txtSumShipped_itemstore);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutInfo);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
        btnViewProfileStore = (Button) itemView.findViewById(R.id.btnViewProfileStore);
        btnViewOrder = (Button) itemView.findViewById(R.id.btnViewOrder);
        imgHeart = (ImageView) itemView.findViewById(R.id.imgHeart);
        imgComment= (LinearLayout) itemView.findViewById(R.id.imgComment) ;
        imgPhotoStore = (ImageView) itemView.findViewById(R.id.imgPhotoStore_itemStore);
    }
}
