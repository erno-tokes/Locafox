package com.android.locafox;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.locafox.products.GetProductsAsyncTask;
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

        GetProductsAsyncTask getProducts = new GetProductsAsyncTask(this, new ITask<ArrayList<Product>>() {
            @Override
            public void onCompleted(ArrayList<Product> result) {
                Toast.makeText(SearchActivity.this, result == null ? "null" : String.valueOf(result.size()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception ex) {
                Toast.makeText(SearchActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        getProducts.execute();
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
