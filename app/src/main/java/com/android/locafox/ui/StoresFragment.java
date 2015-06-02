package com.android.locafox.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.locafox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoresFragment extends Fragment {

    private static final String ARG_STORE_COUNT = "store_count";

    private int storeCount;

    public static StoresFragment newInstance(int storesCount) {
        StoresFragment fragment = new StoresFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STORE_COUNT, storesCount);
        fragment.setArguments(args);
        return fragment;
    }

    public StoresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeCount = getArguments().getInt(ARG_STORE_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stores, container, false);
        TextView text = (TextView) v.findViewById(R.id.stores_text);
        text.setText(String.format(getResources().getString(R.string.search_locations), storeCount));
        return v;
    }
}
