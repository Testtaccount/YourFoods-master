package com.trannguyentanthuan2903.yourfoods.category.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.category.model.Category;
import com.trannguyentanthuan2903.yourfoods.category.model.CategoryListAdapter;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/16/2017.
 */

public class CategoryListFragment extends Fragment {


    private RecyclerView recyclerCategory;
    private ArrayList<Category> arrCategory;
    private CategoryListAdapter adapter;
    private LinearLayoutManager mManager;
    private DatabaseReference mData;
    private String idStore;
    private ImageView imgCreate;
    //Contructor
    public CategoryListFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void addEvents() {
        imgCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateCategory.class);
                intent.putExtra(Constain.ID_STORE, idStore);
                startActivity(intent);
            }
        });
    }

    private void initInfo() {
        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        arrCategory.clear();
                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                            try {
                                Category category = dt.getValue(Category.class);
                                arrCategory.add(category);
                                adapter.notifyDataSetChanged();
                            } catch (Exception ex) {

                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            recyclerCategory.setLayoutManager(mManager);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addControls() {
        imgCreate = (ImageView) getActivity().findViewById(R.id.imgCreateCategoryFrag);
        mData = FirebaseDatabase.getInstance().getReference();
        Intent intent = getActivity().getIntent();
        idStore = intent.getStringExtra(Constain.ID_STORE);
        //List Category
        mManager = new LinearLayoutManager(getActivity());
        recyclerCategory = (RecyclerView) getActivity().findViewById(R.id.recyclerCategory);
        arrCategory = new ArrayList<>();
        adapter = new CategoryListAdapter(arrCategory, getActivity(), idStore);
        recyclerCategory.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
        initInfo();
        addEvents();
    }
}
