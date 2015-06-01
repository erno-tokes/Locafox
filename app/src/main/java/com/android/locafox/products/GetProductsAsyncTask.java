package com.android.locafox.products;

import android.content.Context;
import android.os.AsyncTask;

import com.android.locafox.ITask;
import com.android.locafox.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by Erno on 5/31/2015.
 */
public class GetProductsAsyncTask extends AsyncTask<Void, Void, ArrayList<Product>> {

    private ITask taskListener;
    private String url;
    private Exception error;

    public GetProductsAsyncTask(Context context, ITask taskListener){
        this.taskListener = taskListener;
        url = context.getResources().getString(R.string.search_data_url);
    }

    @Override
    protected ArrayList<Product> doInBackground(Void... params) {

        error = null;
        Response response = null;
        try {
            if(isCancelled()) {
                return null;
            }
            response = makeRequest();
        }
        catch (Exception e){
            error = e;
            return null;
        }
        try {
            if(isCancelled()){
                return null;
            }
            return deserialize(response.body().charStream());
        }
        catch (Exception e){
            error = e;
            return null;
        }
    }

    @Override
    public void onPostExecute(ArrayList<Product> result){
        if(taskListener != null){
            if(error != null){
                taskListener.onError(error);
                return;
            }
            taskListener.onCompleted(result);
        }
    }

    private Response makeRequest() throws IOException{
        OkHttpClient client = new OkHttpClient();
        return client.newCall(new Request.Builder().url(url).build()).execute();
    }

    private ArrayList<Product> deserialize(Reader reader) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Product.class, new Product());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(reader, new TypeToken<ArrayList<Product>>() {}.getType());
    }
}
