package com.trannguyentanthuan2903.yourfoods.my_cart.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.my_cart.presenter.MyCartPresenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/17/2017.
 */

public class MyCartAdapter extends RecyclerView.Adapter<MyCartViewHolder> {

    private ArrayList<MyCart> arrMyCart;
    private Context mContext;
    private MyCartPresenter presenter;
    private String idUser;
    private String idStore;

    public MyCartAdapter(ArrayList<MyCart> arrMyCart, Context mContext, String idUser, String idStore) {
        this.arrMyCart = arrMyCart;
        this.mContext = mContext;
        this.idUser = idUser;
        this.idStore = idStore;
        presenter = new MyCartPresenter();
    }

    @Override
    public MyCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_mycart, parent, false);
        return new MyCartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyCartViewHolder holder, int position) {
        final MyCart myCart = arrMyCart.get(position);
        holder.txtProductName.setText(myCart.getProductName());
        holder.txtCountProduct.setText(String.valueOf(myCart.getCount()));
        holder.txtPrice.setText(Math.round(myCart.getPrice()) + " VNĐ");
        String linkPhotoProduct = myCart.getLinkPhotoProduct();
        if (!linkPhotoProduct.equals("")) {
            Glide.with(mContext)
                    .load(linkPhotoProduct)
                    .fitCenter()
                    .into(holder.imgPhotoProduct);
        }
        //set Click Buy Product
        holder.txtDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder aler = new AlertDialog.Builder(mContext);
                aler.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
                aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteProductOrder(idUser, myCart.getIdMyOrder(), idStore);
                        Toast.makeText(mContext, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    }
                });
                aler.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        holder.swipeLayout.close();
                    }
                });
                aler.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrMyCart.size();
    }
}
