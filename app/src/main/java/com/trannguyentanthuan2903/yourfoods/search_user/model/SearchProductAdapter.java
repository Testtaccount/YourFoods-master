package com.trannguyentanthuan2903.yourfoods.search_user.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trannguyentanthuan2903.yourfoods.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/17/2017.
 */

public class SearchProductAdapter extends ArrayAdapter<SearchProduct> {
    private final String MY_DEBUG_TAG = "SearchStoreAdapter";
    private ArrayList<SearchProduct> items;
    private ArrayList<SearchProduct> itemsAll;
    private ArrayList<SearchProduct> suggestions;
    private int viewResourceId;
    public SearchProductAdapter(Context context, int viewResourceId, ArrayList<SearchProduct> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<SearchProduct>) items.clone();
        this.suggestions = new ArrayList<SearchProduct>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        SearchProduct searchProduct = items.get(position);
        TextView txtProductName = (TextView) v.findViewById(R.id.txtProductName_itemsearchProduct);
        TextView txtPrice = (TextView) v.findViewById(R.id.txtPrice_itemsearchProduct);
        ImageView imgProduct = (ImageView) v.findViewById(R.id.imgPhotoProduct_itemsearchProduct);
        txtProductName.setText(searchProduct.getProduct().getProductName());
        txtPrice.setText(Math.round(searchProduct.getProduct().getPrice()) + " VND");
        if (searchProduct.getProduct().getLinkPhotoProduct() != null){
            Glide.with(getContext())
                    .load(searchProduct.getProduct().getLinkPhotoProduct())
                    .into(imgProduct);
        }
        return v;
    }
    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((SearchProduct) (resultValue)).getProduct().getProductName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (SearchProduct searchProduct : itemsAll) {
                    if (searchProduct.getProduct().getProductName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(searchProduct);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<SearchProduct> filteredList = (ArrayList<SearchProduct>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (SearchProduct c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }

    };
}
