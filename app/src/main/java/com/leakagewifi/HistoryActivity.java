package com.leakagewifi;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private static ListView historylist;
    private static ArrayList<BookmarkModel> arrayList;
    private String[] HISTORY_PROJECTION;
    final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");
    Button Btnencry;
    TextView checkingTxt;
    String ouputString;
    String AES = "AES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Btnencry = (Button) findViewById(R.id.encrypbtn);
        checkingTxt = (TextView) findViewById(R.id.txtchecking);
      /*  btnDecrypt = (Button) findViewById(R.id.btndecrypt);
        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        Btnencry.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {


                                            /* StringBuilder builder = new StringBuilder();
                                             for (BookmarkModel details : arrayList) {
                                                 builder.append(details + "\n");
                                             }*/

                                             checkingTxt.setText("");
                                             for (int j = 0; j < arrayList.size(); j++){
                                                 checkingTxt.append( arrayList.get(j) + "\n");
                                             }



                                             Intent i = new Intent(HistoryActivity.this, EncryptionActivity.class);
                                             String str  = checkingTxt.getText().toString();
                                             Log.i("k",str);
                                             i.putExtra("message", str);
                                             startActivity(i);
                                             finish();

                                         }
                                     });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

    }

    private void init() {
        arrayList = new ArrayList<BookmarkModel>();
        historylist = (ListView) findViewById(R.id.bookmark_list);

        // Cursor to get Bookmar information

                Cursor cursor = this.managedQuery(BOOKMARKS_URI, HISTORY_PROJECTION, null,   null,null);
        this.startManagingCursor(cursor );
        cursor .moveToFirst();

        // Note : " Browser.BookmarkColumns.BOOKMARK " - this will return 0 or
        // 1. '1' indicates a bookmark and '0' indicates HistoryActivity item.

        try {
            // Now loop to all items using cursor
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    // Add Bookmark title and Bookmark Url
                    arrayList.add(new BookmarkModel(cursor.getString(0), cursor.getString(1)));

                } while (cursor.moveToNext()); // Move to next
            }
        } finally {

            // Close the cursor after use
            cursor.close();
        }

        Bookmark_Adapter adapter = new Bookmark_Adapter(HistoryActivity.this, arrayList);
        historylist.setAdapter(adapter);// Set adapter
    }

    private class BOOKMARKS_URI {
    } public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), ResourcesActivity.class);
                startActivity(i);
                finish();
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), ResourcesActivity.class);
        startActivity(i);
        finish();
    }


}