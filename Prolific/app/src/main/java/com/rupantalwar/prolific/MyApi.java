////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                        //
//  MyApi.java - Prolific                                                                 //
//  (Source file containing MyApi Interface used to make HTTP REQUEST calls to            //
//   the server employing RETROFIT api)                                                   //
//                                                                                        //
//  Language:        Java                                                                 //
//  Platform:        Android SDK                                                          //
//  Author:          Rupan Talwar, Email:rupantalwar@gmail.com, Phone: 315 751-2860       //
//  Created On:      1/7/2015                                                             //
////////////////////////////////////////////////////////////////////////////////////////////


package com.rupantalwar.prolific;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;


public interface MyApi {

    @GET("/books")
    public void listBooks(Callback<List<Result>> callback);

    @GET("/books/{id}")
    public void getBook(@Path("id") int id,Callback<Result> callback);

    @PUT("/books/{id}/")
    public void updateBook(@Path("id") int id,@Body Result result,Callback<Result> callback);

    @POST("/books/")
    public void addBook(@Body Result result,Callback<Result> callback);

    @DELETE("/books/{id}/")
    public void deleteBook(@Path("id") int id,Callback<Result> callback);

    @DELETE("/clean/")
    public void deleteAllBooks(Callback<Result> callback);



}