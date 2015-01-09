package com.rupantalwar.prolific;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by rupantalwar on 1/7/15.
 */
public class AddBook extends Activity{


    private Button submit;
    private EditText editbook;
    private EditText editAuth;
    private EditText editPub;
    private EditText editCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_books);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        editbook = (EditText) findViewById(R.id.editbook);
        editAuth = (EditText) findViewById(R.id.editAuth);
        editPub = (EditText) findViewById(R.id.editPub);
        editCat = (EditText) findViewById(R.id.editCat);




        addListenerOnButton();


    }



    public void addListenerOnButton() {
        final Context context = this;
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                Gson gson = new Gson();
                JsonObject json = new JsonObject();
                String t= gson.toJson(new Result(editbook.getText().toString(),editAuth.getText().toString(),editPub.getText().toString(),editCat.getText().toString()));


                Log.d("App","JSON:"+t);


                RestClient.get().addBook(t, new Callback<Result>() {

                    @Override
                    public void success(Result result, Response response) {
                        // success!
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("App", "ERROR");
                        // something went wrong
                    }
                });

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_books, menu);
        return true;
    }






}
