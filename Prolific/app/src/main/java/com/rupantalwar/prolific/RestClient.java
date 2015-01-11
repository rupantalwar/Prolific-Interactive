////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                        //
//  RestClient.java - Prolific                                                            //
//  (Source file containing RestClient class containing RestAdapter and OkHttpClient()    //
//                                                                                        //
//  Language:        Java                                                                 //
//  Platform:        Android SDK                                                          //
//  Author:          Rupan Talwar, Email:rupantalwar@gmail.com, Phone: 315 751-2860       //
//  Created On:      1/8/2015                                                             //
////////////////////////////////////////////////////////////////////////////////////////////


package com.rupantalwar.prolific;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClient {



        private static MyApi REST_CLIENT;
        private static String ROOT =
                "http://prolific-interview.herokuapp.com/54ad929a65c1140007259856/";

        static {
            setupRestClient();
        }

        private RestClient() {}

        public static MyApi get() {
            return REST_CLIENT;
        }

        private static void setupRestClient() {
            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(ROOT)
                    .setClient(new OkClient(new OkHttpClient()))
                    .setLogLevel(RestAdapter.LogLevel.FULL);

            RestAdapter restAdapter = builder.build();
            REST_CLIENT = restAdapter.create(MyApi.class);
        }
    }

