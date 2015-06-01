package com.android.locafox;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.locafox.common.AppKeys;
import com.android.locafox.common.ITask;
import com.android.locafox.products.GetProductsAsyncTask;
import com.android.locafox.products.Product;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private ArrayList<Product> products;
    private GetProductsAsyncTask getProductsTask;
    private ProgressBar searchProgress;
    private AutoCompleteTextView searchText;
    private ImageButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(savedInstanceState != null && savedInstanceState.containsKey(AppKeys.EXTRA_PRODUCTS_DATA)){
            products = savedInstanceState.getParcelableArrayList(AppKeys.EXTRA_PRODUCTS_DATA);
        }

        searchProgress = (ProgressBar)findViewById(R.id.search_progr);
        searchText = (AutoCompleteTextView)findViewById(R.id.search_text);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(products == null){
                    getProducts();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cancelButton = (ImageButton)findViewById(R.id.search_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelProductsTask();
            }
        });
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

    private void getProducts(){
        cancelButton.setVisibility(View.VISIBLE);
        searchProgress.setVisibility(View.VISIBLE);
        getProductsTask = new GetProductsAsyncTask(this, new ITask<ArrayList<Product>>() {
            @Override
            public void onCompleted(ArrayList<Product> result) {
                SearchActivity.this.products = result;
                searchProgress.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception ex) {
                searchProgress.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                Toast.makeText(SearchActivity.this, R.string.search_error, Toast.LENGTH_SHORT).show();
            }
        });
        getProductsTask.execute();
    }

    private void cancelProductsTask(){
        if(this.getProductsTask != null){
            this.getProductsTask.cancel(true);
            searchProgress.setVisibility(View.GONE);
        }
    }
}
