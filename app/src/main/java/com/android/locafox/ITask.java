package com.android.locafox;

/**
 * Created by Erno on 5/31/2015.
 */
public interface ITask<T> {

    void onCompleted(T result);
    void onError(Exception ex);
}
