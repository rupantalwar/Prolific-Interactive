package com.rupantalwar.prolific;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by rupantalwar on 1/7/15.
 */
public class BookDetails extends Activity{

    private Button checkout;
    private TextView book_details_title;
    private TextView book_details_author;
    private TextView book_details_pub;
    private TextView book_details_chk;
    private TextView book_details_tag;
    private TextView book_details_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        book_details_title = (TextView) findViewById(R.id.book_details_title);
        book_details_author = (TextView) findViewById(R.id.book_details_author);
        book_details_pub = (TextView) findViewById(R.id.book_details_pub);
        book_details_tag = (TextView) findViewById(R.id.book_details_tag);
        book_details_chk = (TextView) findViewById(R.id.book_details_chk);
        book_details_date = (TextView) findViewById(R.id.book_details_date);



        Intent intent = getIntent();
        int id=intent.getIntExtra("id",0);

        RestClient.get().getBook(id,new Callback<Result>() {

            @Override
            public void success(Result result, Response response) {
                // success!
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                displayBook(result);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("App","ERROR");
                // something went wrong
            }
        });

        addListenerOnButton();

    }

    public void displayBook(Result result)
    {
        book_details_title.setText(result.getTitle());
        book_details_author.setText(result.getAuthor());
        book_details_pub.setText(result.getPublisher());
        book_details_tag.setText(result.getCategories());
        book_details_chk.setText(result.getLastCheckedOutBy());
        book_details_date.setText(result.getLastCheckedOut());

    }


    public void addListenerOnButton() {
        final Context context = this;
        checkout = (Button) findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // get dialog_box.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialog_box, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //userInput.getText();

                                        RestClient.get().updateBook(id,new Callback<Result>() {

                                            @Override
                                            public void success(Result result, Response response) {
                                                // success!
                                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                                displayBook(result);

                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                Log.d("App","ERROR");
                                                // something went wrong
                                            }
                                        });


                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();




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
