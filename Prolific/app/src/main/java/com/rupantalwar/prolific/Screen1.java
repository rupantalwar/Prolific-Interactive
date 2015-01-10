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
import android.view.ViewGroup;
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
    //private  MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();
        setContentView(R.layout.activity_screen1);
        booksList = (ListView) findViewById(R.id.bookList);
        final SwipeDetector swipeDetector = new SwipeDetector();
        booksList.setOnTouchListener(swipeDetector);
        RestClient.get().listBooks(new Callback<List<Result>>() {

            @Override
            public void success(final List<Result> result, Response response) {
                // success!
                Log.d("App", "In success");
                final ArrayList<Item> items = new ArrayList<Item>();
                for (int i=0;i<result.size();i++) {
                    items.add(new Item(result.get(i).getTitle(),result.get(i).getAuthor()));

                }

                final MyAdapter adapter = new MyAdapter(context, items);
                booksList.setAdapter(adapter);

                booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            final int position, long id) {
                        if (swipeDetector.swipeDetected()){
                            // do the onSwipe action
                            Display display = getWindowManager().getDefaultDisplay();
                            TextView bookName = (TextView) view.findViewById(R.id.bookLabel);
                            TextView value = (TextView) view.findViewById(R.id.value);

                            bookName.clearAnimation();
                            value.clearAnimation();

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

                                    adapter.remove(items.get(position));
                                    adapter.notifyDataSetChanged();



                                }
                            });
                            bookName.startAnimation(translateAnim);

                        } else {
                            // do the onItemClick action


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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

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

    /**
     * Launching addBook activity
     * */
    private void addBook() {
        Intent i = new Intent(Screen1.this, AddBook.class);
        startActivity(i);
    }


    private void deleteAll() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Screen1.this);
        alertDialogBuilder.setTitle("Delete All Books");
        alertDialogBuilder
                .setMessage("Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

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
