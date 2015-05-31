package com.android.locafox;

/**
 * Created by Erno on 5/31/2015.
 */
public interface ITask {

    void onCompleted();
    void onError(Exception ex);
}
