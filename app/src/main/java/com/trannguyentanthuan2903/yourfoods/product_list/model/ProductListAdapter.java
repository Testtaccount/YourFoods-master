package com.trannguyentanthuan2903.yourfoods.product_list.model;

/**
 * Created by Administrator on 10/17/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.main.view.MainStoreActivity;
import com.trannguyentanthuan2903.yourfoods.my_cart.view.DisplayProduct;
import com.trannguyentanthuan2903.yourfoods.product.model.Product;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.List;

/**
 * Created by Nhan on 5/8/2017.
 */

public class ProductListAdapter extends ExpandableRecyclerAdapter<GroupProduct , Product ,GroupProductViewHolder , ViewHolders> {

    private static final int NOI_BAT = 1;
    private static final int BINH_THUONG = 2;
    private Context mContext;
    private String idStore;
    private boolean isStore;
    private LayoutInflater inflater;
    private List<GroupProduct> parentList;


    public ProductListAdapter(String idStore, boolean isStore, Context context , @NonNull List<GroupProduct> parentList) {
        super(parentList);
        this.idStore = idStore;
        this.isStore = isStore;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.parentList = parentList;
    }

    @Override
    public GroupProductViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.product_section_header, parentViewGroup, false);
        return new GroupProductViewHolder(recipeView);
    }


    @Override
    public ViewHolders onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.product_cardview, childViewGroup, false);
        return new ViewHolders(recipeView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull GroupProductViewHolder parentViewHolder, int parentPosition, @NonNull GroupProduct parent) {

        parentViewHolder.setTxtCategoryProductName(parent.getTitle());
    }

    @Override
    public void onBindChildViewHolder(@NonNull ViewHolders childViewHolder, final int parentPosition, final int childPosition, @NonNull Product child) {

        final Product product = child;
        childViewHolder.setTxtPriceName(Math.round(child.getPrice()) + " VNĐ");
        childViewHolder.setTxtNameName(child.getProductName());
        if (isStore == true){
            childViewHolder.btnGetit.setVisibility(View.INVISIBLE);
            childViewHolder.txtEdit.setVisibility(View.VISIBLE);
        }
        else {
            childViewHolder.btnGetit.setVisibility(View.VISIBLE);
            childViewHolder.txtEdit.setVisibility(View.INVISIBLE);
        }
        childViewHolder.btnGetit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStore == false) {
                    GroupProduct groupProduct = parentList.get(parentPosition);
                    displayProduct(product, groupProduct.getTitle());
                }
                else {
                    editProduct(product);
                }
            }
        });
        String linkPhoto = child.getLinkPhotoProduct();
        if(!linkPhoto.equals("")){
            Glide.with(mContext).load(linkPhoto).into(childViewHolder.getImg());
        }
        //set event itemclick
        childViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStore == false) {
                    GroupProduct groupProduct = parentList.get(parentPosition);
                    displayProduct(product, groupProduct.getTitle());
                }
                else {
                    editProduct(product);
                }
            }
        });
    }

    private void displayProduct(Product product, String categoryName){
        Intent intent = new Intent(mContext, DisplayProduct.class);
        intent.putExtra(Constain.PRODUCTS, product);
        intent.putExtra(Constain.CATEGORY_NAME, categoryName);
        intent.putExtra(Constain.ID_STORE, idStore);
        mContext.startActivity(intent);
    }
    private void editProduct (Product product){
        ((MainStoreActivity) mContext).moveToEditProductFragment(product);
    }

}


