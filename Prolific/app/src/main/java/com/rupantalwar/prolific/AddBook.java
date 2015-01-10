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
import android.view.KeyEvent;
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


                if(editbook.getText().toString().length()== 0 || editAuth.getText().toString().length()== 0 || editPub.getText().toString().length()== 0 || editCat.getText().toString().length()== 0 )
                {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddBook.this);
                    alertDialogBuilder.setTitle("Error");
                    alertDialogBuilder
                            .setMessage("Please enter all fields")
                            .setCancelable(false);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

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

    //------------------------------------------------------------------------------
    // Clear the add book form
    //------------------------------------------------------------------------------
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
