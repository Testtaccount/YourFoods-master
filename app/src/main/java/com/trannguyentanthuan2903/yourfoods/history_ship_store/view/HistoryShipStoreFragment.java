package com.trannguyentanthuan2903.yourfoods.history_ship_store.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.history_ship_store.model.HistoryShipStore;
import com.trannguyentanthuan2903.yourfoods.history_ship_store.model.ListOrderProduct;
import com.trannguyentanthuan2903.yourfoods.history_ship_store.model.OrderListStoreAdapter;
import com.trannguyentanthuan2903.yourfoods.history_ship_store.model.ParentHistoryStore;
import com.trannguyentanthuan2903.yourfoods.history_ship_store.model.ParentInfoUserOrder;
import com.trannguyentanthuan2903.yourfoods.oop.BaseFragment;
import com.trannguyentanthuan2903.yourfoods.profile_store.presenter.UpdateStorePresenter;
import com.trannguyentanthuan2903.yourfoods.profile_store.view.Profile_Store_Fragment;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 10/16/2017.
 */


public class HistoryShipStoreFragment extends BaseFragment {


    private DatabaseReference mData;
    private String idStore;
    private OrderListStoreAdapter adapter;
    private RecyclerView recyclerViewShipStore;
    private RecyclerView.LayoutManager mManager;
    private int sumShipped;
    private UpdateStorePresenter presenter;

    public HistoryShipStoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordered_history_store, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
    }

    private void addControls() {
        idStore = getActivity().getIntent().getStringExtra(Constain.ID_STORE);
        mData = FirebaseDatabase.getInstance().getReference();
        recyclerViewShipStore = (RecyclerView) getActivity().findViewById(R.id.recyclerShipStore);
        recyclerViewShipStore.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderListStoreAdapter(getContext(), idStore, initData());
        adapter.collapseAllParents();
        Profile_Store_Fragment fragment = new Profile_Store_Fragment();
        presenter = new UpdateStorePresenter(getContext(), fragment);
        recyclerViewShipStore.setAdapter(adapter);
    }

    private List<ParentHistoryStore> initData() {
        List<ParentHistoryStore> parentObjectList = new ArrayList<>();
        getListCategory(parentObjectList);
        return parentObjectList;
    }

    private void getListCategory(final List<ParentHistoryStore> parentObjectList) {
        mData.child(Constain.STORES).child(idStore).child(Constain.HISTORY_SHIP_STORE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parentObjectList.clear();
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                        if (dt.getValue() != null) {
                            mData.child(Constain.STORES).child(idStore).child(Constain.HISTORY_SHIP_STORE).child(dt.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                            try {
                                                HistoryShipStore historyShipStore = dt.getValue(HistoryShipStore.class);
                                                ParentInfoUserOrder parentInfoUserOrder = new ParentInfoUserOrder(historyShipStore.getUserName(), historyShipStore.getIdUser(), historyShipStore.getIdHistoryStore(), historyShipStore.getLinkPhotoUser(), historyShipStore.getPhoneNumber(), historyShipStore.getAddress(), historyShipStore.getTimeOrder(), historyShipStore.getStatusOrder());
                                                List<ListOrderProduct> listOrderProducts = new ArrayList<ListOrderProduct>();
                                                listOrderProducts.add(new ListOrderProduct(historyShipStore.getArrProduct()));
                                                ParentHistoryStore parentHistoryStore = new ParentHistoryStore(listOrderProducts, parentInfoUserOrder);
                                                parentObjectList.add(parentHistoryStore);
                                                Collections.sort(parentObjectList);
                                                adapter.notifyParentDataSetChanged(true);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}

