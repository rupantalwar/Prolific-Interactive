package com.rupantalwar.prolific;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by rupantalwar on 1/8/15.
 */
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

