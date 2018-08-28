package com.trannguyentanthuan2903.yourfoods.store_list.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.oop.BaseFragment;
import com.trannguyentanthuan2903.yourfoods.profile_store.model.Store;
import com.trannguyentanthuan2903.yourfoods.store_list.model.StoreListAdapter;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Administrator on 10/17/2017.
 */


public class Store_List_Fragment extends BaseFragment {
    private static final String TAG = "Store_List_Fragment";
    private RecyclerView recyclerView;
    private DatabaseReference mData;
    private ArrayList<Store> arrStore;
    private ArrayList<Store> arrayDefault;
    private StoreListAdapter adapter;
    private String idUser, emailUser;
    private LinearLayoutManager mManager;
    private double loUser, laUser;
    private int mResults;

    public Store_List_Fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idUser = getIdUser();
        emailUser = getEmailUser();
        addControls();
    }

    private void addControls() {
        mData = FirebaseDatabase.getInstance().getReference();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_list, container, false);
        arrStore = new ArrayList<>();
        arrayDefault = new ArrayList<Store>();
        adapter = new StoreListAdapter(arrStore, this, idUser, getContext());
        adapter.setEmailUser(emailUser);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewliststore);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public ArrayList<Store> getArrStore() {
        return arrStore;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

        try {
            mData.child(Constain.STORES).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            arrStore.clear();
                            for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                Store store = dt.getValue(Store.class);
                                arrayDefault.add(store);
                                Collections.sort(arrayDefault, new StoreComparator());
                                arrStore.clear();
                                arrStore.addAll(arrayDefault);
                                adapter.notifyDataSetChanged();
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
            recyclerView.setLayoutManager(mManager);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
//        double c = 2 * Math.asin(Math.sqrt(a));
        double c = 2* Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
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

    private class StoreComparator implements Comparator<Store> {
        public int compare(Store st1, Store st2) {
            HashMap<String, Object> loca1 = st1.getLocation();
            HashMap<String, Object> loca2 = st2.getLocation();
            return (int) (calculationByDistance(laUser, loUser, (Double) (loca2.get(Constain.LA)), (Double) (loca2.get(Constain.LO)))
                    - calculationByDistance(laUser, loUser, (Double) loca1.get(Constain.LA), (Double) loca1.get(Constain.LO)));
        }
    }

}
