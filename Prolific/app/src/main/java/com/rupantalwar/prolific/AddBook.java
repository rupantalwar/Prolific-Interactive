////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                        //
//  AddBook.java - Prolific                                                               //
//  (Source file containing AddBook class used for adding Book details to the server      //
//                                                                                        //
//  Language:        Java                                                                 //
//  Platform:        Android SDK                                                          //
//  Author:          Rupan Talwar, Email:rupantalwar@gmail.com, Phone: 315 751-2860       //
//  Created On:      1/7/2015                                                             //
////////////////////////////////////////////////////////////////////////////////////////////


package com.rupantalwar.prolific;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddBook extends Activity{


    //Declaring edit text fields and button from the layout add_books
    private Button submit;
    private EditText editbook;
    private EditText editAuth;
    private EditText editPub;
    private EditText editCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_books);

        //Setting up "up" carat button/home/back button on the Action bar
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Co-relating layout fields to EditText object types
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


                //Check for all required edit text fields
                if(editbook.getText().toString().length()== 0 || editAuth.getText().toString().length()== 0 || editPub.getText().toString().length()== 0 || editCat.getText().toString().length()== 0 )
                {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddBook.this);
                    alertDialogBuilder.setTitle("Error");
                    alertDialogBuilder
                            .setMessage("Please enter all fields")
                            .setCancelable(false);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    //Making the Alert dialog box appear for 3000ms or 3 seconds
                    final Handler handler  = new Handler();
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

                }
                else
                {
                Result result= new Result(editbook.getText().toString(),editAuth.getText().toString(),editPub.getText().toString(),editCat.getText().toString(),null,null);

                //Calling RestClient , that would call API to make HTTP POST request to the server
                RestClient.get().addBook(result, new Callback<Result>() {

                    @Override
                    public void success(Result result, Response response) {
                        // success!
                        Toast.makeText(getApplicationContext(), "Book Added Successfully", Toast.LENGTH_LONG).show();
                        ViewGroup group = (ViewGroup) findViewById(R.id.addBooks);
                        clearForm(group);
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

    // Clear the add book form
    private void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }
            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_books, menu);
        return true;
    }

    //Handling "up" carat and "done" action bar button clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               backSure();
                break;

            case R.id.done:
               backSure();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void backSure(){
        if(editbook.getText().toString().length()!= 0 || editAuth.getText().toString().length()!= 0 || editPub.getText().toString().length()!= 0 || editCat.getText().toString().length()!= 0  )
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddBook.this);
            alertDialogBuilder.setTitle("Leave this page");
            alertDialogBuilder
                    .setMessage("Are you sure?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //If clicked yes, start Screen1 activity
                            Intent intent = new Intent(AddBook.this, Screen1.class);
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
            Intent intent = new Intent(AddBook.this, Screen1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

    }



    //Handling Back/Return Soft touch key
    @Override
    public void onBackPressed() {

        if(editbook.getText().toString().length()!= 0 || editAuth.getText().toString().length()!= 0 || editPub.getText().toString().length()!= 0 || editCat.getText().toString().length()!= 0  )
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddBook.this);
            alertDialogBuilder.setTitle("Leave this page");
            alertDialogBuilder
                    .setMessage("Are you sure?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //If clicked yes, start Screen1 activity
                            Intent intent = new Intent(AddBook.this, Screen1.class);
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
            Intent intent = new Intent(AddBook.this, Screen1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }


    }


}
