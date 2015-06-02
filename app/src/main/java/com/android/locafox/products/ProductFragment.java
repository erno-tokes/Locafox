package com.android.locafox.products;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.locafox.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    private static final String ARG_PRODUCT = "product";

    private Product product;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param product Parameter.
     * @return A new instance of fragment ProductFragment.
     */
    public static ProductFragment newInstance(Product product) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = getArguments().getParcelable(ARG_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        if(product != null) {
            TextView nameText = (TextView) v.findViewById(R.id.product_name_text);
            nameText.setText(product.getName());

            ImageView image = (ImageView)v.findViewById(R.id.product_image);
            if(!TextUtils.isEmpty(product.getImageUrl())) {
                Picasso.with(getActivity()).load(Uri.parse(product.getImageUrl())).into(image);
            }

            TextView storeText = (TextView)v.findViewById(R.id.product_availability_text);
            storeText.setText(String.format(getResources().getString(R.string.product_availability), product.getAvailability()));
        }

        return v;
    }
}
