package com.forkthecode.capstone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.forkthecode.capstone.data.Contract.NewsEntry;

/**
 * Created by rohanarora on 19/10/16.
 */

public class OpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "capstone_db";

    public OpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + NewsEntry.TABLE_NAME +
                " (" + NewsEntry.COLUMN_SERVER_ID + " INTEGER PRIMARY UNIQUE, " +
                NewsEntry.COLUMN_TITLE + " TEXT, " + NewsEntry.COLUMN_CONTENT + " TEXT, " +
                NewsEntry.COLUMN_URL + " TEXT, " + NewsEntry.COLUMN_COVER_IMAGE_URL + " TEXT, " +
                NewsEntry.COLUMN_TIMESTAMP + " TEXT)";

        db.execSQL(SQL_CREATE_NEWS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
