package com.android.locafox;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.locafox.common.AppKeys;
import com.android.locafox.common.ITask;
import com.android.locafox.products.GetProductsAsyncTask;
import com.android.locafox.products.Product;
import com.android.locafox.products.ProductFragment;
import com.android.locafox.products.ProductsFilterableAdapter;
import com.android.locafox.ui.LogoFragment;
import com.android.locafox.ui.StoresFragment;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private ArrayList<Product> products;
    private Product selectedProduct;
    private GetProductsAsyncTask getProductsTask;
    private ProgressBar searchProgress;
    private AutoCompleteTextView searchText;
    private ImageButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(savedInstanceState != null) {
            if (savedInstanceState.containsKey(AppKeys.EXTRA_PRODUCTS_DATA)) {
                products = savedInstanceState.getParcelableArrayList(AppKeys.EXTRA_PRODUCTS_DATA);
            }
            if (savedInstanceState.containsKey(AppKeys.EXTRA_SELECTED_PRODUCT)) {
                selectedProduct = savedInstanceState.getParcelable(AppKeys.EXTRA_SELECTED_PRODUCT);
            }
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
                if(s.length() > 0){
                    cancelButton.setVisibility(View.VISIBLE);
                }
                else{
                    cancelButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    showStoreCounts();
                    handled = true;
                }
                return handled;
            }
        });
        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(products != null){
                    startActivityForProduct(products.get(position));
                }
            }
        });
        if(products != null){
            searchText.setAdapter(new ProductsFilterableAdapter(this, products));
        }

        cancelButton = (ImageButton)findViewById(R.id.search_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText.setText("");
            }
        });

        if(selectedProduct == null) {
            showLogo();
        }else{
            showProduct();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (intent != null){
            if(intent.hasExtra(AppKeys.EXTRA_PRODUCTS_DATA)) {
                products = intent.getExtras().getParcelableArrayList(AppKeys.EXTRA_PRODUCTS_DATA);
                searchText.setAdapter(new ProductsFilterableAdapter(this, products));
            }
            if(intent.hasExtra(AppKeys.EXTRA_SELECTED_PRODUCT)) {
                selectedProduct = intent.getExtras().getParcelable(AppKeys.EXTRA_SELECTED_PRODUCT);
            }
            if(selectedProduct != null){
                showProduct();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        if(this.products != null) {
            bundle.putParcelableArrayList(AppKeys.EXTRA_PRODUCTS_DATA, products);
        }
        if(selectedProduct != null){
            bundle.putParcelable(AppKeys.EXTRA_SELECTED_PRODUCT, selectedProduct);
        }
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onStop(){
        cancelProductsTask();
        super.onStop();
    }

    /**
     * Changes the current view to logo
     */
    private void showLogo(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, LogoFragment.newInstance()).commit();
    }

    /**
     * Changes the current view to the number of stores view
     */
    private void showStoreLocations(int storesCount){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, StoresFragment.newInstance(storesCount)).commit();
    }

    /**
     * Changes the current view to the product detail view
     */
    private void showProduct(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, ProductFragment.newInstance(selectedProduct)).commit();
    }

    /**
     * Shows the number of all stores if products have availability
     */
    private void showStoreCounts(){
        if(getProductsTask == null){
            return;
        }
        else if(isTaskRunning()) {
            Toast.makeText(SearchActivity.this, R.string.search_loading, Toast.LENGTH_SHORT).show();
            return;
        }
        else if(products == null){
            Toast.makeText(SearchActivity.this, R.string.search_error, Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            int storeCount = 0;
            for(int i = 0; i < products.size(); i++){
                storeCount += products.get(i).getAvailability();
            }
            showStoreLocations(storeCount);
        }
    }

    /**
     * Start this activity but onNewIntent() will be called cause of launchMode:singleTask
     * @param product The product details will be shown
     */
    private void startActivityForProduct(Product product){
        if(product == null){
            return;
        }
        Intent intent = new Intent(this, SearchActivity.class);
        if(products != null) {
            intent.putExtra(AppKeys.EXTRA_PRODUCTS_DATA, products);
        }
        intent.putExtra(AppKeys.EXTRA_SELECTED_PRODUCT, product);
        this.startActivity(intent);
    }

    /**
     * Starts fetching products data if already was not started
     */
    private void getProducts(){
        if(isTaskRunning()){
            return;
        }
        searchProgress.setVisibility(View.VISIBLE);
        getProductsTask = new GetProductsAsyncTask(this, new ITask<ArrayList<Product>>() {
            @Override
            public void onCompleted(ArrayList<Product> result) {
                SearchActivity.this.products = result;
                searchText.setAdapter(new ProductsFilterableAdapter(SearchActivity.this, products));
                searchProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception ex) {
                searchProgress.setVisibility(View.GONE);
                Toast.makeText(SearchActivity.this, R.string.search_error, Toast.LENGTH_SHORT).show();
            }
        });
        getProductsTask.execute();
    }

    /**
     * Cancels the running task of getting products
     */
    private void cancelProductsTask(){
        if(isTaskRunning()){
            this.getProductsTask.cancel(true);
            searchProgress.setVisibility(View.GONE);
        }
    }

    private boolean isTaskRunning(){
        return getProductsTask != null && getProductsTask.getStatus() != AsyncTask.Status.FINISHED;
    }
}
