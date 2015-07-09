package com.star.providertest;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    public static final String AUTHORITY = "com.star.providerbestpractice.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static final String TABLE_BOOK_NAME = "book";

    public static final String BOOK_COLUMN_ID = "id";
    public static final String BOOK_COLUMN_AUTHOR = "author";
    public static final String BOOK_COLUMN_PRICE = "price";
    public static final String BOOK_COLUMN_PAGES = "pages";
    public static final String BOOK_COLUMN_NAME = "name";

    public static final Uri CONTENT_BOOK_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_BOOK_NAME);

    private long mNewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = CONTENT_BOOK_URI;

                ContentValues values = new ContentValues();
                values.put(BOOK_COLUMN_NAME, "A Clash of Kings");
                values.put(BOOK_COLUMN_AUTHOR, "George Martin");
                values.put(BOOK_COLUMN_PAGES, 1040);
                values.put(BOOK_COLUMN_PRICE, 22.85);

                Uri newUri = getContentResolver().insert(uri, values);

                mNewId = Long.parseLong(newUri.getPathSegments().get(1));
            }
        });

        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = CONTENT_BOOK_URI;

                Cursor cursor = getContentResolver().query(uri, null, null, null, null);

                try {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(
                                BOOK_COLUMN_NAME));
                        String author = cursor.getString(cursor.getColumnIndex(
                                BOOK_COLUMN_AUTHOR));
                        int pages = cursor.getInt(cursor.getColumnIndex(
                                BOOK_COLUMN_PAGES));
                        double price = cursor.getDouble(cursor.getColumnIndex(
                                BOOK_COLUMN_PRICE));
                        Log.d("MainActivity", "book name is " + name);
                        Log.d("MainActivity", "book author is " + author);
                        Log.d("MainActivity", "book pages is " + pages);
                        Log.d("MainActivity", "book price is " + price);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

            }
        });

        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = ContentUris.withAppendedId(CONTENT_BOOK_URI, mNewId);

                ContentValues values = new ContentValues();
                values.put(BOOK_COLUMN_NAME, "A Storm of Swords");
                values.put(BOOK_COLUMN_PAGES, 1216);
                values.put(BOOK_COLUMN_PRICE, 24.05);

                int updatedRows = getContentResolver().update(uri, values, null, null);

                Log.d("MainActivity", "updatedRows is " + updatedRows);
            }
        });

        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = ContentUris.withAppendedId(CONTENT_BOOK_URI, mNewId);

                int deletedRows = getContentResolver().delete(uri, null, null);

                Log.d("MainActivity", "deletedRows is " + deletedRows);
            }
        });
    }
}
