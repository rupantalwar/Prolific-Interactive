package com.rupantalwar.prolific;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import retrofit.Callback;
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


        RestClient.get().listBooks(new Callback<List<Result>>() {

            @Override
            public void success(final List<Result> result, Response response) {
                // success!
                Log.d("App", "In success");
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                fillList(result);

                booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(Screen1.this, BookDetails.class);

                        String bookName = ((TextView) view.findViewById(R.id.bookLabel)).getText().toString();
                        Log.d("App","bookName:"+bookName);

                        Map<String,Integer> map = new HashMap<String,Integer>();
                        int ide=0;
                        String title=null;
                        for(int j=0;j<result.size();j++){
                            ide=result.get(j).getId();
                            title=result.get(j).getTitle();
                            map.put(title,ide);
                        }
                        Log.d("App","Hash map values: "+map);

                        int bookId=0;
                        for (Map.Entry<String, Integer> entry : map.entrySet()){
                            if(entry.getKey().contains(bookName)){
                                bookId=entry.getValue();
                            }
                        }

                        i.putExtra("id", bookId);
                        startActivity(i);
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("App","ERROR");
                // something went wrong
            }
        });





    }

    public void fillList(List<Result> result){

        MyAdapter adapter = new MyAdapter(this, generateData(result));
        booksList.setAdapter(adapter);

    }

    private ArrayList<Item> generateData(List<Result> res){

        ArrayList<Item> items = new ArrayList<Item>();
        for (int i=0;i<res.size();i++) {
            items.add(new Item(res.get(i).getTitle(),res.get(i).getAuthor()));

        }
        return items;
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
