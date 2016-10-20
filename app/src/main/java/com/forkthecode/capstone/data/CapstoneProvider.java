package com.forkthecode.capstone.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by rohanarora on 19/10/16.
 *
 */

public class CapstoneProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private OpenHelper mOpenHelper;

    static final int NEWS = 100;
    static final int EVENTS = 200;
    static final int SPEAKERS = 300;

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Contract.PATH_NEWS, NEWS);
        matcher.addURI(authority, Contract.PATH_EVENTS, EVENTS);
        matcher.addURI(authority, Contract.PATH_SPEAKERS, SPEAKERS);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new OpenHelper(getContext());
        return true;
    }


    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case NEWS:
                return Contract.NewsEntry.CONTENT_TYPE;
            case EVENTS:
                return Contract.EventEntry.CONTENT_TYPE;
            case SPEAKERS:
                return Contract.SpeakerEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case NEWS: {
                retCursor = mOpenHelper.getReadableDatabase().query(Contract.NewsEntry.TABLE_NAME,
                        projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            case EVENTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(Contract.EventEntry.TABLE_NAME,
                        projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            case SPEAKERS: {
                retCursor = mOpenHelper.getReadableDatabase().query(Contract.SpeakerEntry.TABLE_NAME,
                        projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case NEWS: {
                long _id = db.insert(Contract.NewsEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = Contract.NewsEntry.buildNewsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case EVENTS: {
                long _id = db.insert(Contract.EventEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = Contract.EventEntry.buildEventUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case SPEAKERS: {
                long _id = db.insert(Contract.SpeakerEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = Contract.SpeakerEntry.buildSpeakerUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case NEWS:
                rowsDeleted = db.delete(
                        Contract.NewsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EVENTS:
                rowsDeleted = db.delete(
                        Contract.EventEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case SPEAKERS:
                rowsDeleted = db.delete(
                        Contract.SpeakerEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }



    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case NEWS:
                rowsUpdated = db.update(Contract.NewsEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case EVENTS:
                rowsUpdated = db.update(Contract.EventEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case SPEAKERS:
                rowsUpdated = db.update(Contract.SpeakerEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NEWS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        try {
                            long _id = db.insertOrThrow(Contract.NewsEntry.TABLE_NAME, null, value);
                            if (_id != -1) {
                                returnCount++;
                            }
                        }
                        catch (SQLiteConstraintException e){
                            db.update(Contract.NewsEntry.TABLE_NAME,value,
                                    Contract.NewsEntry.COLUMN_SERVER_ID + " = ?",
                                    new String[]{value.getAsString(Contract.NewsEntry.COLUMN_SERVER_ID)});
                        }

                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case EVENTS:
                db.beginTransaction();
                int returnEventCount = 0;
                try {
                    for (ContentValues value : values) {
                        try {
                            long _id = db.insertOrThrow(Contract.EventEntry.TABLE_NAME, null, value);
                            if (_id != -1) {
                                returnEventCount++;
                            }
                        }
                        catch (SQLiteConstraintException e){
                            db.update(Contract.EventEntry.TABLE_NAME,value,
                                    Contract.NewsEntry.COLUMN_SERVER_ID + " = ?",
                                    new String[]{value.getAsString(Contract.EventEntry.COLUMN_SERVER_ID)});
                        }

                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnEventCount;
            case SPEAKERS:
                db.beginTransaction();
                int returnSpeakerCount = 0;
                try {
                    for (ContentValues value : values) {
                        try {
                            long _id = db.insertOrThrow(Contract.SpeakerEntry.TABLE_NAME, null, value);
                            if (_id != -1) {
                                returnSpeakerCount++;
                            }
                        }
                        catch (SQLiteConstraintException e){
                            db.update(Contract.SpeakerEntry.TABLE_NAME,value,
                                    Contract.SpeakerEntry.COLUMN_SERVER_ID + " = ?",
                                    new String[]{value.getAsString(Contract.SpeakerEntry.COLUMN_SERVER_ID)});
                        }

                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnSpeakerCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}

