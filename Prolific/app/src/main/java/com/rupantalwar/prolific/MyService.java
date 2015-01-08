package com.rupantalwar.prolific;

import android.app.Application;
import retrofit.RestAdapter;


/**
 * Created by rupantalwar on 1/7/15.
 */
public class MyService extends Application {

    private static final String APP_SERVER = "http://prolific-interview.herokuapp.com/54ad929a65c1140007259856/";

    private MyApi myApi;

    @Override public void onCreate() {
        super.onCreate();

        // Set up Retrofit.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(APP_SERVER)
                .build();
        myApi = restAdapter.create(MyApi.class);
    }

    public MyApi getBooks() {
        return myApi;
    }
}
