package com.trannguyentanthuan2903.yourfoods.category.model;

import com.google.firebase.database.DatabaseReference;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 10/16/2017.
 */

public class CategorySubmitter  {
    private DatabaseReference mData;

    public CategorySubmitter(DatabaseReference mData) {
        this.mData = mData;
    }
    public void addCategoryOnData (String idStore, String idCategory, String categoryName, int sumProduct, String timeCreate){
        Category category = new Category(idCategory, categoryName, sumProduct, timeCreate);
        HashMap<String, Object> myMap = new HashMap<>();
        myMap = category.putMap();
        mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).setValue(myMap);
    }
    public void deleteCategory (String idStore, String idCategory){
        mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(idCategory).removeValue();
    }
}