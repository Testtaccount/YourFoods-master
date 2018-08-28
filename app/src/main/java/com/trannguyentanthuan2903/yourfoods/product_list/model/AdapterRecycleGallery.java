package com.trannguyentanthuan2903.yourfoods.product_list.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.main.view.MainStoreActivity;
import com.trannguyentanthuan2903.yourfoods.my_cart.view.DisplayProduct;
import com.trannguyentanthuan2903.yourfoods.product.model.Product;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.List;

/**
 * Created by Administrator on 10/17/2017.
 */

public class AdapterRecycleGallery extends RecyclerView.Adapter<AdapterRecycleGallery.ViewHolder> {

    private List<Product> listProducts;
    private Context mContext;
    private String idStore;
    private boolean isStore;

    public AdapterRecycleGallery(String idStore, boolean isStore, Context mContext , List<Product> listProducts) {
        this.idStore = idStore;
        this.isStore = isStore;
        this.listProducts = listProducts;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_slider_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = listProducts.get(position);
        String linkPhotoUser = "";
        if(product.getLinkPhotoProduct() != null)
            linkPhotoUser = product.getLinkPhotoProduct();
        if(!linkPhotoUser.equals("")){
            Glide.with(mContext).load(linkPhotoUser).into(holder.img);
        }
        if (isStore == true){
            holder.btnGetit.setVisibility(View.INVISIBLE);
        }
        holder.txtPrice.setText(Math.round(product.getPrice()) + " VNĐ");
        holder.txtName.setText(product.getProductName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStore == false) {
                    displayProduct(product);
                }
                else {
                    editProduct(product);
                }
            }
        });
    }

    private void displayProduct(Product product){
        Intent intent = new Intent(mContext, DisplayProduct.class);
        intent.putExtra(Constain.PRODUCTS, product);
        intent.putExtra(Constain.ID_STORE, idStore);
        mContext.startActivity(intent);
    }
    private void editProduct (Product product){
        ((MainStoreActivity) mContext).moveToEditProductFragment(product);
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txtName;
        TextView txtPrice;
        Button btnGetit;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgSpecial);
            btnGetit = (Button) itemView.findViewById(R.id.btnGetitSpecial);
            txtName = (TextView) itemView.findViewById(R.id.txtNameSpecial);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPriceSpecial);
        }
    }
}
