package com.rupantalwar.prolific;

import android.app.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by rupantalwar on 1/7/15.
 */
public interface MyApi {

    @GET("/books")
    public void listBooks(Callback<List<String>> callback);


}