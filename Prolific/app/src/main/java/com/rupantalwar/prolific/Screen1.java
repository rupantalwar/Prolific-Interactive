////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                        //
//  Screen1.java - Prolific                                                               //
//  (Source file containing Screen1 class used for displaying the list of all books,      //
//   Deleting a book and Deleting all the books                                           //
//                                                                                        //
//  Language:        Java                                                                 //
//  Platform:        Android SDK                                                          //
//  Author:          Rupan Talwar, Email:rupantalwar@gmail.com, Phone: 315 751-2860       //
//  Created On:      1/7/2015                                                             //
////////////////////////////////////////////////////////////////////////////////////////////


package com.rupantalwar.prolific;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Screen1 extends Activity {

    private ListView booksList;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();
        setContentView(R.layout.activity_screen1);

        //Instantiating ListView object
        booksList = (ListView) findViewById(R.id.bookList);

        //Setting up Swipe Detector class, used to instantiate custom swipe gestures on a ListView item
        final SwipeDetector swipeDetector = new SwipeDetector();

        booksList.setOnTouchListener(swipeDetector);

        //Calling RestClient , that would call API to make HTTP GET request to the server
        RestClient.get().listBooks(new Callback<List<Result>>() {

            @Override
            public void success(final List<Result> result, Response response) {
                // success!
                Log.d("App", "In success");
                Toast.makeText(getApplicationContext(), "Swipe Left to Delete a Book", Toast.LENGTH_LONG).show();

                //Fetching data from the result and iterating it through the list to get book's title and author
                final ArrayList<Item> items = new ArrayList<Item>();
                for (int i=0;i<result.size();i++) {
                    items.add(new Item(result.get(i).getTitle(),result.get(i).getAuthor()));
                }

                //Setting up the custom adapter to populate the listView
                final MyAdapter adapter = new MyAdapter(context, items);
                booksList.setAdapter(adapter);

                booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            final int position, long id) {
                        if (swipeDetector.swipeDetected()){

                            //do the onSwipe Right-to-Left action
                            Display display = getWindowManager().getDefaultDisplay();
                            final TextView bookName = (TextView) view.findViewById(R.id.bookLabel);
                            final TextView value = (TextView) view.findViewById(R.id.value);

                            //Setting up custom swipe animation
                            TranslateAnimation translateAnim = new TranslateAnimation(0, -display.getWidth(), 0, 0);
                            translateAnim.setDuration(300);
                            translateAnim.setAnimationListener(new Animation.AnimationListener() {

                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

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

                                    //Calling RestClient , that would call API to make HTTP DELETE request to the server to delete the book
                                    RestClient.get().deleteBook(bookId, new Callback<Result>() {

                                        @Override
                                        public void success(Result result, Response response) {
                                            // success!
                                            Log.d("App", "Success: Screen1");

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.d("App", "ERROR");
                                            // something went wrong
                                        }
                                    });

                                    //Removing the book from the adapter and thus the ListView
                                    adapter.remove(items.get(position));
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            bookName.startAnimation(translateAnim);

                        } else {

                         // do the onItemClick action
                        Intent i = new Intent(Screen1.this, BookDetails.class);

                        //Fetching the book name from the selected Textview
                        String bookName = ((TextView) view.findViewById(R.id.bookLabel)).getText().toString();

                        //Storing values fetched from result into a HashMap
                        Map<String,Integer> map = new HashMap<String,Integer>();
                        int ide=0;
                        String title=null;
                        for(int j=0;j<result.size();j++){
                            ide=result.get(j).getId();
                            title=result.get(j).getTitle();
                            map.put(title,ide);
                        }

                        //Matching the selected book name with the hash map to fetch the correct book id
                        int bookId=0;
                        for (Map.Entry<String, Integer> entry : map.entrySet()){
                            if(entry.getKey().contains(bookName)){
                                bookId=entry.getValue();
                            }
                        }

                        //Passing the book id to the next activity
                        i.putExtra("id", bookId);
                        startActivity(i);
                    }
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_screen1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                // add action
                addBook();
                return true;
            case R.id.action_delete:
                // delete action
                deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Launching AddBook activity
    private void addBook() {
        Intent i = new Intent(Screen1.this, AddBook.class);
        startActivity(i);
    }

    //Deleting all the books
    private void deleteAll() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Screen1.this);
        alertDialogBuilder.setTitle("Delete All Books");
        alertDialogBuilder
                .setMessage("Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        //Calling RestClient , that would call API to make HTTP DELETE request to the server
                        RestClient.get().deleteAllBooks(new Callback<Result>() {
                            @Override
                            public void success(Result result, Response response) {
                                // success!
                                Toast.makeText(getApplicationContext(), "All Books Deleted", Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void failure(RetrofitError error) {
                                Log.d("App", "ERROR");
                                // something went wrong
                            }
                        });
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
