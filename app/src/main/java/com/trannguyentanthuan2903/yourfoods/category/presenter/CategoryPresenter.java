package com.trannguyentanthuan2903.yourfoods.category.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.category.model.CategorySubmitter;

/**
 * Created by Administrator on 10/16/2017.
 */

public class CategoryPresenter {
    private DatabaseReference mData;
    private CategorySubmitter submitter;

    public CategoryPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new CategorySubmitter(mData);
    }
    public void addCategoryOnData (String idStore, String idCategory, String categoryName, int sumProduct, String timeCreate){
        submitter.addCategoryOnData(idStore, idCategory, categoryName, sumProduct, timeCreate);
    }
    public void deleteCategory (String idStore, String idCategory){
        submitter.deleteCategory(idStore, idCategory);
    }
}
