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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private ShareActionProvider provider;

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
        final int ide=intent.getIntExtra("id",0);

        RestClient.get().getBook(ide,new Callback<Result>() {

            @Override
            public void success(Result result, Response response) {
                // success!
                Log.d("App","Success:Book Details");
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

                Log.d("App","userInput:"+userInput.getText().toString());

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //userInput.getText();

                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        String currentDateandTime = sdf.format(new Date());
                                        Result result=new Result(book_details_title.getText().toString(),book_details_author.getText().toString(),book_details_pub.getText().toString(),book_details_tag.getText().toString(),currentDateandTime,userInput.getText().toString());

                                        Intent intent = getIntent();
                                        final int ide=intent.getIntExtra("id",0);

                                        RestClient.get().updateBook(ide,result,new Callback<Result>() {

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

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_details, menu);

        provider = (ShareActionProvider) menu.findItem(R.id.menu_share).getActionProvider();

        provider.setShareIntent(getDefaultShareIntent());

        return super.onCreateOptionsMenu(menu);
    }

        /** Returns a share intent */
        private Intent getDefaultShareIntent(){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
            intent.putExtra(Intent.EXTRA_TEXT,"Extra Text");
            return intent;
        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Screen1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.edit:
                Intent intent1 = getIntent();
                final int ide=intent1.getIntExtra("id",0);
                Intent intent2 = new Intent(BookDetails.this, AddCustomBook.class);
                Bundle bundle = new Bundle();
                Log.d("BookDetails",book_details_title.getText().toString());
                Log.d("BookDetails",book_details_author.getText().toString());
                Log.d("BookDetails",book_details_pub.getText().toString());
                Log.d("BookDetails",book_details_tag.getText().toString());

                bundle.putString("title",book_details_title.getText().toString());
                bundle.putString("author",book_details_author.getText().toString());
                bundle.putString("publisher",book_details_pub.getText().toString());
                bundle.putString("categories",book_details_tag.getText().toString());
                bundle.putInt("id",ide);
                intent2.putExtras(bundle);
                startActivity(intent2);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
