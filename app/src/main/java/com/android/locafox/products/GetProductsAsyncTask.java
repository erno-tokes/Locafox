package com.android.locafox.products;

import android.content.Context;
import android.os.AsyncTask;

import com.android.locafox.ITask;
import com.android.locafox.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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

        OkHttpClient client = new OkHttpClient();
        Response response = null;
        try {
            response = client.newCall(new Request.Builder().url(url).build()).execute();
        }
        catch (Exception e){
            error = e;
            return null;
        }


        return null;
    }

    @Override
    public void onPostExecute(ArrayList<Product> result){
        if(taskListener != null){
            if(error != null){
                taskListener.onError(error);
                return;
            }
        }
    }
}
