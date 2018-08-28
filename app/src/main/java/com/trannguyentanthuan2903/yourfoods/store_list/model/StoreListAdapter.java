package com.trannguyentanthuan2903.yourfoods.store_list.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.comment.view.CommentActivity;
import com.trannguyentanthuan2903.yourfoods.main.view.MainUser2Activity;
import com.trannguyentanthuan2903.yourfoods.main.view.MainUserActivity;
import com.trannguyentanthuan2903.yourfoods.profile_store.model.Store;
import com.trannguyentanthuan2903.yourfoods.store_list.presenter.StoreListPresenter;
import com.trannguyentanthuan2903.yourfoods.store_list.view.Store_List_Fragment;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 10/17/2017.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListViewHolder> {

    private ArrayList<Store> arrStore;
    private String sumShipped;
    private long sumFavorite;
    private long sumComment;
    private String idStore;
    private String idUser;
    private String emailUser;
    private String emailStore;
    private Context mContext;
    private DatabaseReference mData;
    private String linkPhotoStore, timeWork = "";
    private Store_List_Fragment store_list_fragment;
    private boolean flagVisibility = false;
    private StoreListPresenter presenter;
    private double loUser = 0, laUser = 0, loStore = 0, laStore = 0, distance;

    public StoreListAdapter(ArrayList<Store> arrStore, Store_List_Fragment store_list_fragment, String idUser, Context mContext) {
        this.arrStore = arrStore;
        this.store_list_fragment = store_list_fragment;
        this.idUser = idUser;
        this.mContext = mContext;
        presenter = new StoreListPresenter();
        mData = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public StoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new StoreListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StoreListViewHolder holder, final int position) {
        final Store store = arrStore.get(position);
        idStore = store.getIdStore();
        sumShipped = String.valueOf(store.getSumShipped());
        holder.txtStoreName.setText(store.getStoreName());
        holder.txtSumShipped.setText("Đã giao thành công " + sumShipped + " lần");
        holder.txtSumProduct.setText("Tổng cộng " + store.getSumProduct() + " sản phẩm");
        if (store.getIsOpen() == 0) {
            holder.txtStatus.setText("Mở cửa");
        } else if (store.getIsOpen() == 1) {
            holder.txtStatus.setText("Đóng cửa");
        }
        //get LinkPhotoStore and Opent with Glide
        linkPhotoStore = store.getLinkPhotoStore();
        if (!linkPhotoStore.equals("")) {
            /*Glide.with(store_list_fragment.getActivity())
                    .load(linkPhotoStore)
                    .preload(225, 225)
                    .into(holder.imgPhotoStore);*/
            Picasso.with(mContext)
                    .load(linkPhotoStore)
                    .resize(225, 225)
                    .centerCrop()
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.imgPhotoStore);
        }
        try {
            //get Address Store
            mData.child(Constain.STORES).child(idStore).child(Constain.LOCATION).child(Constain.ADDRESS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    holder.txtAddress.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            //get Timework
            mData.child(Constain.STORES).child(idStore).child(Constain.TIME_WORK).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    timeWork = dataSnapshot.getValue().toString();
                    holder.txtTimeWork.setText(timeWork);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            //get Email
            mData.child(Constain.STORES).child(idStore).child(Constain.EMAIL).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    emailStore = dataSnapshot.getValue().toString();
                    holder.txtEmailStore.setText(emailStore);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            //get sumFavorite
            mData.child(Constain.STORES).child(idStore).child(Constain.FAVORITE_LIST).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        sumFavorite = dataSnapshot.getChildrenCount();
                        holder.txtSumfavorite.setText(String.valueOf(sumFavorite));
                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                            if (idUser.equals(dt.getKey().toString())) {
                                holder.imgHeart.setImageResource(R.drawable.heart);
                            }
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
        try {
            //get Lo and la User
            mData.child(Constain.USERS).child(idUser).child(Constain.LOCATION).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            HashMap<String, Object> location = new HashMap<String, Object>();
                            location = (HashMap<String, Object>) dataSnapshot.getValue();
                            loUser = (double) location.get(Constain.LO);
                            laUser = (double) location.get(Constain.LA);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (
                Exception ex)

        {
            ex.printStackTrace();
        }

        try
        {
            //get Longtitude and Latitude store and Caculator Distance
            mData.child(Constain.STORES).child(idStore).child(Constain.LOCATION).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            HashMap<String, Object> location = new HashMap<>();
                            location = (HashMap<String, Object>) dataSnapshot.getValue();
                            loStore = (double) location.get(Constain.LO);
                            laStore = (double) location.get(Constain.LA);
                            if (loUser != 0 && laUser != 0 && loStore != 0 && laStore != 0) {
                                distance = calculationByDistance(laUser, loUser, laStore, loStore);
                                distance = Math.round(distance);
                                holder.txtDistance.setText(String.valueOf(distance) + " Km");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex)

        {
            ex.printStackTrace();
        }
        //set Item click
        holder.cardView.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (flagVisibility == true) {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    flagVisibility = false;
                } else {
                    holder.linearLayout.setVisibility(View.GONE);
                    flagVisibility = true;
                }
            }
        });
        //imgHeart Click
        holder.imgHeart.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                try {
                    Store store1 = arrStore.get(position);
                    final String idStore1 = store1.getIdStore();
                    mData.child(Constain.STORES).child(idStore1).child(Constain.FAVORITE_LIST).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                boolean flag = true;
                                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                    if (idUser.equals(dt.getKey())) {
                                        flag = false;
                                    }
                                }
                                if (!flag) {
                                    //remove and change img
                                    sumFavorite = dataSnapshot.getChildrenCount() - 1;
                                    holder.txtSumfavorite.setText(String.valueOf(sumFavorite));
                                    holder.imgHeart.setImageResource(R.drawable.non_heart);
                                    presenter.removeHeart(idStore1, idUser);
                                } else {
                                    //add and change img
                                    holder.imgHeart.setImageResource(R.drawable.heart);
                                    sumFavorite = dataSnapshot.getChildrenCount() + 1;
                                    holder.txtSumfavorite.setText(String.valueOf(sumFavorite));
                                    presenter.addHeart(idStore1, idUser, emailUser);
                                }
                            } else {
                                holder.imgHeart.setImageResource(R.drawable.heart);
                                holder.txtSumfavorite.setText("1");
                                presenter.addHeart(idStore1, idUser, emailUser);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (Exception ex) {

                }
            }
        });
        // txtHeart Click
        holder.txtSumfavorite.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                try {
                    Store store1 = arrStore.get(position);
                    final String idStore1 = store1.getIdStore();
                    mData.child(Constain.STORES).child(idStore1).child(Constain.FAVORITE_LIST).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                boolean flag = true;
                                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                    if (idUser.equals(dt.getKey())) {
                                        flag = false;
                                    }
                                }
                                if (!flag) {
                                    //remove and change img
                                    sumFavorite = dataSnapshot.getChildrenCount() - 1;
                                    holder.txtSumfavorite.setText(String.valueOf(sumFavorite));
                                    holder.imgHeart.setImageResource(R.drawable.non_heart);
                                    presenter.removeHeart(idStore1, idUser);
                                } else {
                                    //add and change img
                                    holder.imgHeart.setImageResource(R.drawable.heart);
                                    sumFavorite = dataSnapshot.getChildrenCount() + 1;
                                    holder.txtSumfavorite.setText(String.valueOf(sumFavorite));
                                    presenter.addHeart(idStore1, idUser, emailUser);
                                }
                            } else {
                                holder.imgHeart.setImageResource(R.drawable.heart);
                                holder.txtSumfavorite.setText("1");
                                presenter.addHeart(idStore1, idUser, emailUser);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (Exception ex) {

                }
            }
        });
        //btn Callnow click
        holder.btnViewProfileStore.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Store store1 = arrStore.get(position);
                ((MainUserActivity) mContext).moveToProfileStoreFragment(store1.getIdStore());
            }
        });
        //btnView Order click
        holder.btnViewOrder.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Store store2 = arrStore.get(position);
                Intent intent = new Intent(mContext, MainUser2Activity.class);
                intent.putExtra(Constain.ID_STORE, store2.getIdStore());
                intent.putExtra(Constain.ID_USER, idUser);
                intent.putExtra(Constain.IS_STORE, false);
                mContext.startActivity(intent);

            }
        });

        //comment click

        holder.imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cmtIntent = new Intent(mContext, CommentActivity.class);
                cmtIntent.putExtra("id_store",store.getIdStore());
                mContext.startActivity(cmtIntent);

            }
        });

        //sum comment

        try {
            //get sumComment
            mData.child(Constain.STORES).child(idStore).child(Constain.COMMENTS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        sumComment = dataSnapshot.getChildrenCount();
                        holder.txtSumComment.setText(String.valueOf(sumComment));
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
    public int getItemCount() {
        return arrStore.size();
    }

    //set UserName

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    //Caculator Distance
    public final double calculationByDistance(double lat1, double lon1, double lat2, double lon2) {
        int Radius = 6371;// radius of earth in Km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }


}
