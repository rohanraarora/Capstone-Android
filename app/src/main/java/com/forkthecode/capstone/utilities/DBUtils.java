package com.forkthecode.capstone.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.forkthecode.capstone.data.Contract;
import com.forkthecode.capstone.data.models.News;

/**
 * Created by rohanarora on 19/10/16.
 */

public class DBUtils {

    public static ContentValues cvFromNews(@NonNull News news){
        Util.checkNotNull(news);
        ContentValues newsValues = new ContentValues();
        newsValues.put(Contract.NewsEntry.COLUMN_SERVER_ID, news.getServerId());
        newsValues.put(Contract.NewsEntry.COLUMN_TITLE, news.getTitle());
        newsValues.put(Contract.NewsEntry.COLUMN_TIMESTAMP,news.getTimeStamp());
        newsValues.put(Contract.NewsEntry.COLUMN_CONTENT,news.getContent());
        newsValues.put(Contract.NewsEntry.COLUMN_COVER_IMAGE_URL,news.getCoverImageUrl());
        newsValues.put(Contract.NewsEntry.COLUMN_URL,news.getUrl());
        return newsValues;
    }

    public static News getNewsFromCursor(@NonNull Cursor cursor) {
        Util.checkNotNull(cursor);
        News news = new News();
        news.setContent(cursor.getString(cursor.getColumnIndex(Contract.NewsEntry.COLUMN_CONTENT)));
        news.setServerId(cursor.getLong(cursor.getColumnIndex(Contract.NewsEntry.COLUMN_SERVER_ID)));
        news.setTitle(cursor.getString(cursor.getColumnIndex(Contract.NewsEntry.COLUMN_TITLE)));
        news.setCoverImageUrl(cursor.getString(cursor.getColumnIndex(
                Contract.NewsEntry.COLUMN_COVER_IMAGE_URL)));
        news.setUrl(cursor.getString(cursor.getColumnIndex(Contract.NewsEntry.COLUMN_URL)));
        news.setTimeStamp(cursor.getLong(cursor.getColumnIndex(Contract.NewsEntry.COLUMN_TIMESTAMP)));
        return news;

    }
}
