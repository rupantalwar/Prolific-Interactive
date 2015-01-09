package com.rupantalwar.prolific;

import android.app.ActivityManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by rupantalwar on 1/7/15.
 */
public interface MyApi {

    @GET("/books")
    public void listBooks(Callback<List<Result>> callback);

    @GET("/books/{id}")
    public void getBook(@Path("id") int id,Callback<Result> callback);

    @PUT("/books/{id}")
    public void updateBook(@Path("id") int id,Callback<Result> callback);

    @POST("/books")
    public void addBook(@Body String result,Callback<Result> callback);



}