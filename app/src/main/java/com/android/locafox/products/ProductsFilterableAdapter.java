package com.android.locafox.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erno on 6/3/2015.
 */
public class ProductsFilterableAdapter extends ArrayAdapter<Product> {
    private LayoutInflater layoutInflater;
    List<Product> filteredProducts;

    private Filter filter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Product) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint != null) {
                ArrayList<Product> suggestions = new ArrayList<Product>();
                for (Product product : filteredProducts) {
                    for(String desc : product.getDesc().split(", ")){
                        if(desc.toLowerCase().contains(constraint.toString().toLowerCase())){
                            suggestions.add(product);
                            break;
                        }
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // the filtered products
                addAll((ArrayList<Product>) results.values);
            }
            notifyDataSetChanged();
        }
    };

    public ProductsFilterableAdapter(Context context, List<Product> products) {
        super(context, android.R.layout.simple_list_item_1, products);
        this.filteredProducts = new ArrayList<Product>(products);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        Product product = getItem(position);

        TextView name = (TextView) view.findViewById(android.R.id.text1);
        name.setText(product.getName());

        return view;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}
