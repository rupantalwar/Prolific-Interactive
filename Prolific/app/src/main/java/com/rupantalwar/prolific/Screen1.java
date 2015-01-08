package com.rupantalwar.prolific;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Screen1 extends Activity {

    private ListView booksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

        booksList = (ListView) findViewById(R.id.bookList);

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X"
                 };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        booksList.setAdapter(adapter);


        load(getApp());

    }

    private MyService getApp()
    {
        return (MyService) getApplication();
    }

    public void load(MyService ser){

    MyApi myApi= ser.getBooks();
        myApi.listBooks(new Callback<List<String>>() {
            @Override public void success(List<String> books, Response response) {
                String[] bookArr = new String[books.size()];
                bookArr = books.toArray(bookArr);
                String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X"
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, values);
                booksList.setAdapter(adapter);

            }

            @Override public void failure(RetrofitError retrofitError) {



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_screen1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_add:
                // add action
                addBook();
                return true;
            case R.id.action_seed:
                // seed action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Launching addBook activity
     * */
    private void addBook() {
        Intent i = new Intent(Screen1.this, AddBook.class);
        startActivity(i);
    }






}
