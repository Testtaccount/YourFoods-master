package com.trannguyentanthuan2903.yourfoods.history_ship_store.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.history_ship_store.presenter.HistoryShipPresenter;
import com.trannguyentanthuan2903.yourfoods.profile_store.model.Store;
import com.trannguyentanthuan2903.yourfoods.profile_user.model.User;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.List;

/**
 * Created by Administrator on 10/16/2017.
 */

public class OrderListStoreAdapter extends ExpandableRecyclerAdapter<ParentHistoryStore, ListOrderProduct, ParentOrderViewHolder, ChildOrderViewHolder> {

    List<ParentHistoryStore> parentList;
    LayoutInflater inflater;
    HistoryShipPresenter presenter;
    String idStore;
    int sumShippedStore = 0, sumShippedUser = 0;
    Context mContext;

    public OrderListStoreAdapter(Context mContext, String idStore, @NonNull List<ParentHistoryStore> parentList) {
        super(parentList);
        this.mContext = mContext;
        this.idStore = idStore;
        inflater = LayoutInflater.from(mContext);
        this.parentList = parentList;
        presenter = new HistoryShipPresenter();
    }

    @Override
    public ParentOrderViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.item_store_history_parent, parentViewGroup, false);
        return new ParentOrderViewHolder(recipeView);
    }

    @Override
    public ChildOrderViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View recipeView = inflater.inflate(R.layout.item_store_history_child, childViewGroup, false);
        return new ChildOrderViewHolder(recipeView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull ParentOrderViewHolder parentViewHolder, int parentPosition, @NonNull ParentHistoryStore parent) {
        parentViewHolder.setNameTxtAddressUser(parent.getInfoUserOrder().getAddressUser());
        parentViewHolder.setNameTxtUserNameItemOrderList(parent.getInfoUserOrder().getNameUser());
        parentViewHolder.setNameTxtPhoneNumberItemOrderList(parent.getInfoUserOrder().getPhoneNumberUser());
        parentViewHolder.setNameTxtTimeOrder(parent.getInfoUserOrder().getTimeOrder());

        String linkPhotoUser = parent.getInfoUserOrder().getLinkPhotoUser();
        if (!linkPhotoUser.equals("")) {
            Glide.with(mContext).load(linkPhotoUser).into(parentViewHolder.getImgAvataUserItemOrderHistory());
        }
        if (parent.getInfoUserOrder().getStatus() == 0) {
            parentViewHolder.getImgStatusShip().setVisibility(View.INVISIBLE);
        } else if (parent.getInfoUserOrder().getStatus() == 1) {
            parentViewHolder.getImgStatusShip().setVisibility(View.VISIBLE);
            parentViewHolder.getImgStatusShip().setImageResource(R.drawable.imgshipped);
        } else if (parent.getInfoUserOrder().getStatus() == 2) {
            parentViewHolder.getImgStatusShip().setVisibility(View.VISIBLE);
            parentViewHolder.getImgStatusShip().setImageResource(R.drawable.imgcanceled);
        }
        //get SumShipped Store
        try {
            final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
            mData.child(Constain.STORES).child(idStore).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        try {
                            Store store = dataSnapshot.getValue(Store.class);
                            sumShippedStore = store.getSumShipped();
                        }
                        catch (Exception ex){

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildOrderViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull ListOrderProduct child) {

        childViewHolder.recyclerView.setAdapter(new OrderItemAdapter(child.getOrderProductList()));
        childViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        final ParentHistoryStore parentHistoryStore = parentList.get(parentPosition);
        //get Status Ship
        if (parentHistoryStore.getInfoUserOrder().getStatus() == 0) {
            childViewHolder.layoutStatusShip.setVisibility(View.VISIBLE);
        } else if (parentHistoryStore.getInfoUserOrder().getStatus() == 1) {
            childViewHolder.layoutStatusShip.setVisibility(View.GONE);
        } else if (parentHistoryStore.getInfoUserOrder().getStatus() == 2) {
            childViewHolder.layoutStatusShip.setVisibility(View.GONE);
        }
        //get SumShipped User
        try {
            final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
            mData.child(Constain.USERS).child(parentHistoryStore.getInfoUserOrder().getIdUser()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        try {
                            User user = dataSnapshot.getValue(User.class);
                            sumShippedUser = user.getSumShipped();
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //set Click
        childViewHolder.btnCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder aler = new AlertDialog.Builder(mContext);
                aler.setMessage("Bạn chắc chắn muốn hủy hóa đơn này?");
                aler.setCancelable(false);
                aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.updateStatusOrderStore(idStore, parentHistoryStore.getInfoUserOrder().getIdUser(), parentHistoryStore.getInfoUserOrder().getIdHistoryShip(), 2);
                        presenter.updateStatusOrderUser(idStore, parentHistoryStore.getInfoUserOrder().getIdUser(), parentHistoryStore.getInfoUserOrder().getIdHistoryShip(), 2);
                        Toast.makeText(mContext, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
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
        childViewHolder.btnShipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder aler = new AlertDialog.Builder(mContext);
                aler.setMessage("Bạn chắc chắn đã giao hóa đơn này?");
                aler.setCancelable(false);
                aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.updateStatusOrderStore(idStore, parentHistoryStore.getInfoUserOrder().getIdUser(), parentHistoryStore.getInfoUserOrder().getIdHistoryShip(), 1);
                        presenter.updateStatusOrderUser(idStore, parentHistoryStore.getInfoUserOrder().getIdUser(), parentHistoryStore.getInfoUserOrder().getIdHistoryShip(), 1);
                        sumShippedStore += 1;
                        sumShippedUser += 1;
                        presenter.updateSumShippedStore(idStore, sumShippedStore);
                        presenter.updateSumShippedUser(parentHistoryStore.getInfoUserOrder().getIdUser(), sumShippedUser);
                        Toast.makeText(mContext, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
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

}
