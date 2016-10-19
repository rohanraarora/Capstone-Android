package com.forkthecode.capstone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.forkthecode.capstone.data.Contract.NewsEntry;
import com.forkthecode.capstone.data.models.Event;

import static com.forkthecode.capstone.data.Contract.*;

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
                " (" + NewsEntry._ID + " INTEGER PRIMARY KEY, " +
                NewsEntry.COLUMN_SERVER_ID + " INTEGER UNIQUE, " +
                NewsEntry.COLUMN_TITLE + " TEXT, " + NewsEntry.COLUMN_CONTENT + " TEXT, " +
                NewsEntry.COLUMN_URL + " TEXT, " + NewsEntry.COLUMN_COVER_IMAGE_URL + " TEXT, " +
                NewsEntry.COLUMN_TIMESTAMP + " TEXT)";

        final String SQL_CREATE_EVENT_TABLE = "CREATE TABLE " + EventEntry.TABLE_NAME +
                " (" + EventEntry._ID + " INTEGER PRIMARY KEY, " +
                EventEntry.COLUMN_SERVER_ID + " INTEGER UNIQUE, " +
                EventEntry.COLUMN_TITLE + " TEXT, " + EventEntry.COLUMN_DESCRIPTION + " TEXT, " +
                EventEntry.COLUMN_FROM_TIMESTAMP + " TEXT, " +
                EventEntry.COLUMN_TO_TIMESTAMP + " TEXT, " +
                EventEntry.COLUMN_TICKET_URL + " TEXT, " +
                EventEntry.COLUMN_TICKET_PRICE + " INTEGER, " +
                EventEntry.COLUMN_VENUE_TITLE + " TEXT, " +
                EventEntry.COLUMN_VENUE_LAT + " TEXT, " + EventEntry.COLUMN_VENUE_LONG + " TEXT, " +
                EventEntry.COLUMN_COVER_IMAGE_LINK + " TEXT)";

        db.execSQL(SQL_CREATE_NEWS_TABLE);
        db.execSQL(SQL_CREATE_EVENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
