package com.rupantalwar.prolific;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by rupantalwar on 1/7/15.
 */
public class AddCustomBook extends Activity{


    private Button update;
    private EditText editcbook;
    private EditText editcAuth;
    private EditText editcPub;
    private EditText editcCat;
    private EditText editcYourName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_books);


        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String author = bundle.getString("author");
        String publisher = bundle.getString("publisher");
        String categories = bundle.getString("categories");

        Log.d("APP",title);
        Log.d("APP",author);
        Log.d("APP",publisher);
        Log.d("APP",categories);

        editcbook = (EditText) findViewById(R.id.editcbook);
        editcAuth = (EditText) findViewById(R.id.editcAuth);
        editcPub = (EditText) findViewById(R.id.editcPub);
        editcCat = (EditText) findViewById(R.id.editcCat);
        editcYourName = (EditText) findViewById(R.id.editcYourName);


        editcbook.setHint(title);
        editcAuth.setHint(author);
        editcPub.setHint(publisher);
        editcCat.setHint(categories);
        View v= (View) findViewById(R.id.addCustomBooks);
        v.invalidate();

        addListenerOnButton();

    }


    public void addListenerOnButton() {

        final Context context = this;
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                if (editcYourName.getText().toString().length() == 0) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddCustomBook.this);
                    alertDialogBuilder.setTitle("Error");
                    alertDialogBuilder
                            .setMessage("Please enter all fields")
                            .setCancelable(false);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    final Handler handler = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (alertDialog.isShowing()) {
                                alertDialog.dismiss();
                            }
                        }
                    };

                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            handler.removeCallbacks(runnable);
                        }
                    });

                    handler.postDelayed(runnable, 3000);

                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());

                    Bundle bundle = getIntent().getExtras();
                    String title = bundle.getString("title");
                    String author = bundle.getString("author");
                    String publisher = bundle.getString("publisher");
                    String categories = bundle.getString("categories");
                    String cTitle=null;
                    String cAuthor=null;
                    String cPublisher=null;
                    String cCategories=null;


                    if(editcbook.getText().toString().length()==0)
                        cTitle=title;
                    else
                        cTitle=editcbook.getText().toString();

                    if(editcAuth.getText().toString().length()==0)
                        cAuthor=author;
                    else
                        cAuthor=editcAuth.getText().toString();

                    if(editcPub.getText().toString().length()==0)
                        cPublisher=publisher;
                    else
                        cPublisher=editcPub.getText().toString();

                    if(editcCat.getText().toString().length()==0)
                        cCategories=categories;
                    else
                        cCategories=editcCat.getText().toString();

                    Result result = new Result(cTitle, cAuthor, cPublisher,cCategories, currentDateandTime, editcYourName.getText().toString());
                    int id = bundle.getInt("id");

                    RestClient.get().updateBook(id, result, new Callback<Result>() {

                        @Override
                        public void success(Result result, Response response) {
                            // success!
                            Toast.makeText(getApplicationContext(), "Book Updated Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddCustomBook.this, Screen1.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("App", "ERROR");
                            // something went wrong
                        }
                    });

                }

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_books, menu);
        return true;
    }



    @Override
    public void onBackPressed() {

        if(editcbook.getText().toString().length()!= 0 || editcAuth.getText().toString().length()!= 0 || editcPub.getText().toString().length()!= 0 || editcCat.getText().toString().length()!= 0  )
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddCustomBook.this);
            alertDialogBuilder.setTitle("Leave this page");
            alertDialogBuilder
                    .setMessage("Are you sure?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(AddCustomBook.this, Screen1.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else{
            Intent intent = new Intent(AddCustomBook.this, Screen1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }


    }


}
