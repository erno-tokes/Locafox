package com.android.locafox;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.locafox.products.Product;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(savedInstanceState != null && savedInstanceState.containsKey(AppKeys.EXTRA_PRODUCTS_DATA)){
            products = savedInstanceState.getParcelableArrayList(AppKeys.EXTRA_PRODUCTS_DATA);
        }
    }

    @Override
    public void onNewIntent(Intent intent){
        if(intent != null && intent.hasExtra(AppKeys.EXTRA_PRODUCTS_DATA)){
            products = intent.getExtras().getParcelableArrayList(AppKeys.EXTRA_PRODUCTS_DATA);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        bundle.putParcelableArrayList(AppKeys.EXTRA_PRODUCTS_DATA, products);
    }
}
