////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                        //
//  AddCustomBook.java - Prolific                                                         //
//  (Source file containing AddCustomBook class used for editing Book details)            //
//                                                                                        //
//  Language:        Java                                                                 //
//  Platform:        Android SDK                                                          //
//  Author:          Rupan Talwar, Email:rupantalwar@gmail.com, Phone: 315 751-2860       //
//  Created On:      1/9/2015                                                             //
////////////////////////////////////////////////////////////////////////////////////////////


package com.rupantalwar.prolific;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;



public class AddCustomBook extends Activity{


    //Declaring edit text fields and button from the layout add_custom_books
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

        //Getting parameters from the previous activity in the form of Bundle
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String author = bundle.getString("author");
        String publisher = bundle.getString("publisher");
        String categories = bundle.getString("categories");

        //Co-relating layout fields to EditText object types
        editcbook = (EditText) findViewById(R.id.editcbook);
        editcAuth = (EditText) findViewById(R.id.editcAuth);
        editcPub = (EditText) findViewById(R.id.editcPub);
        editcCat = (EditText) findViewById(R.id.editcCat);
        editcYourName = (EditText) findViewById(R.id.editcYourName);

        //Setting values of book details into edit text fields as hints
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

                //Check for required "You Name" edit text field
                if (editcYourName.getText().toString().length() == 0) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddCustomBook.this);
                    alertDialogBuilder.setTitle("Error");
                    alertDialogBuilder
                            .setMessage("Please enter Your Name")
                            .setCancelable(false);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    //Making the Alert dialog box appear for 3000ms or 3 seconds
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

                    //Setting values entered in edit text fields into the book details,
                    // if nothing entered, the existing book details values are passed to the server
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

                    //Calling RestClient , that would call API to make HTTP PUT request to the server
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


    //Handling Back/Return Soft touch key
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
                            //If clicked yes, start Screen1 activity
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
