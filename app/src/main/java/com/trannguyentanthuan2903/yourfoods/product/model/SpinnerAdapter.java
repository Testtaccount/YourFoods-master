package com.trannguyentanthuan2903.yourfoods.product.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.category.model.Category;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/17/2017.
 */

public class SpinnerAdapter extends BaseAdapter {

    private ArrayList<Category> arrCategory;
    private Activity activity;

    public SpinnerAdapter(ArrayList<Category> arrCategory, Activity activity) {
        this.arrCategory = arrCategory;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return arrCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(activity).inflate(R.layout.item_category_spinner, parent, false);
        TextView txtCategoryName = (TextView) convertView.findViewById(R.id.txtCateoryName_itemSpinner);
        Category category = arrCategory.get(position);
        txtCategoryName.setText(category.getCategoryName().toString());
        return convertView;
    }
}
