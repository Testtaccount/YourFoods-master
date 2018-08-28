package com.trannguyentanthuan2903.yourfoods.category.model;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.category.presenter.CategoryPresenter;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/16/2017.
 */

public class CategoryListAdapter extends RecyclerSwipeAdapter<CategoryListViewHolder> {

    private ArrayList<Category> arrCategory;
    private Activity activity;
    private String idStore;
    private DatabaseReference mData;
    private CategoryPresenter presenter;

    public CategoryListAdapter(ArrayList<Category> arrCategory, Activity activity, String idStore) {
        this.arrCategory = arrCategory;
        this.activity = activity;
        this.idStore = idStore;
        mData = FirebaseDatabase.getInstance().getReference();
        presenter = new CategoryPresenter();
    }

    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_category, parent, false);
        return new CategoryListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryListViewHolder viewHolder, final int position) {
        Category category = arrCategory.get(position);
        int stt = position + 1;
        if (stt % 2 == 0) {
            viewHolder.txtSTT.setBackgroundColor(activity.getResources().getColor(R.color.stt1));
            viewHolder.txtSTTSwipe.setBackgroundColor(activity.getResources().getColor(R.color.stt1));
        } else {
            viewHolder.txtSTT.setBackgroundColor(activity.getResources().getColor(R.color.stt2));
            viewHolder.txtSTTSwipe.setBackgroundColor(activity.getResources().getColor(R.color.stt2));
        }
        //get sumProduct
        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(category.getIdCategory()).child(Constain.PRODUCTS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long sumProduct = dataSnapshot.getChildrenCount();
                    viewHolder.txtSumProduct.setText(String.valueOf(sumProduct));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception ex){

        }
        viewHolder.txtSTT.setText(String.valueOf(stt));
        viewHolder.txtSTTSwipe.setText(String.valueOf(stt));
        viewHolder.txtCategoryName.setText(category.getCategoryName());
        viewHolder.txtSumProduct.setText(String.valueOf(category.getSumProduct()));
        viewHolder.txtTimeCreate.setText(category.getTimeCreateCategory());
        viewHolder.txtDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Category category1 = arrCategory.get(position);
                AlertDialog.Builder aler = new AlertDialog.Builder(activity);
                aler.setMessage("Xóa Category bao gồm xóa toàn bộ sản phẩm trong category ?");
                aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteCategory(idStore, category1.getIdCategory());
                        Toast.makeText(activity, "Bạn đã xóa Category", Toast.LENGTH_SHORT).show();

                    }
                });
                aler.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                aler.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCategory.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return 0;
    }
}
