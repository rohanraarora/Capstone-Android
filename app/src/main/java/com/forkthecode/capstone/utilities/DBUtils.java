package com.forkthecode.capstone.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.forkthecode.capstone.data.Contract;
import com.forkthecode.capstone.data.models.Event;
import com.forkthecode.capstone.data.models.News;
import com.forkthecode.capstone.data.models.Speaker;

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

    public static ContentValues cvFromEvent(@NonNull Event event){
        Util.checkNotNull(event);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.EventEntry.COLUMN_SERVER_ID,event.getServerId());
        contentValues.put(Contract.EventEntry.COLUMN_TITLE,event.getTitle());
        contentValues.put(Contract.EventEntry.COLUMN_DESCRIPTION,event.getDescription());
        contentValues.put(Contract.EventEntry.COLUMN_FROM_TIMESTAMP,event.getFromTimestamp());
        contentValues.put(Contract.EventEntry.COLUMN_TO_TIMESTAMP,event.getToTimestamp());
        contentValues.put(Contract.EventEntry.COLUMN_TICKET_PRICE,event.getTicketPrice());
        contentValues.put(Contract.EventEntry.COLUMN_TICKET_URL,event.getTitcketURL());
        contentValues.put(Contract.EventEntry.COLUMN_VENUE_TITLE,event.getVenueTitle());
        contentValues.put(Contract.EventEntry.COLUMN_VENUE_LAT,event.getVenueLat());
        contentValues.put(Contract.EventEntry.COLUMN_VENUE_LONG,event.getVenueLong());
        contentValues.put(Contract.EventEntry.COLUMN_COVER_IMAGE_LINK,event.getCoverImageURL());
        return contentValues;
    }

    public static Event getEventFromCursor(@NonNull Cursor cursor){
        Util.checkNotNull(cursor);
        Event event = new Event();
        event.setServerId(cursor.getLong(cursor.getColumnIndex(Contract.EventEntry.COLUMN_SERVER_ID)));
        event.setTitle(cursor.getString(cursor.getColumnIndex(Contract.EventEntry.COLUMN_TITLE)));
        event.setDescription(cursor.getString(cursor.getColumnIndex(Contract.EventEntry.COLUMN_DESCRIPTION)));
        event.setFromTimestamp(cursor.getLong(cursor.getColumnIndex(Contract.EventEntry.COLUMN_FROM_TIMESTAMP)));
        event.setToTimestamp(cursor.getLong(cursor.getColumnIndex(Contract.EventEntry.COLUMN_TO_TIMESTAMP)));
        event.setTicketPrice(cursor.getInt(cursor.getColumnIndex(Contract.EventEntry.COLUMN_TICKET_PRICE)));
        event.setTitcketURL(cursor.getString(cursor.getColumnIndex(Contract.EventEntry.COLUMN_TICKET_URL)));
        event.setVenueTitle(cursor.getString(cursor.getColumnIndex(Contract.EventEntry.COLUMN_VENUE_TITLE)));
        event.setVenueLat(cursor.getDouble(cursor.getColumnIndex(Contract.EventEntry.COLUMN_VENUE_LAT)));
        event.setVenueLong(cursor.getDouble(cursor.getColumnIndex(Contract.EventEntry.COLUMN_VENUE_LONG)));
        event.setCoverImageURL(cursor.getString(cursor.getColumnIndex(Contract.EventEntry.COLUMN_COVER_IMAGE_LINK)));
        return event;
    }

    public static ContentValues cvFromSpeaker(@NonNull Speaker speaker){
        Util.checkNotNull(speaker);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.SpeakerEntry.COLUMN_SERVER_ID,speaker.getServerId());
        contentValues.put(Contract.SpeakerEntry.COLUMN_EVENT_SERVER_ID,speaker.getEventId());
        contentValues.put(Contract.SpeakerEntry.COLUMN_NAME,speaker.getName());
        contentValues.put(Contract.SpeakerEntry.COLUMN_BIO,speaker.getBio());
        contentValues.put(Contract.SpeakerEntry.COLUMN_PROFILE_URL,speaker.getProfileURL());
        contentValues.put(Contract.SpeakerEntry.COLUMN_PROFILE_PIC_URL,speaker.getProfilePicURL());
        return contentValues;
    }

    public static Speaker getSpeakerFromCursor(Cursor cursor) {
        Speaker speaker = new Speaker();
        speaker.setServerId(cursor.getLong(cursor.getColumnIndex(Contract.SpeakerEntry.COLUMN_SERVER_ID)));
        speaker.setEventId(cursor.getLong(cursor.getColumnIndex(Contract.SpeakerEntry.COLUMN_EVENT_SERVER_ID)));
        speaker.setBio(cursor.getString(cursor.getColumnIndex(Contract.SpeakerEntry.COLUMN_BIO)));
        speaker.setName(cursor.getString(cursor.getColumnIndex(Contract.SpeakerEntry.COLUMN_NAME)));
        speaker.setProfileURL(cursor.getString(cursor.getColumnIndex(Contract.SpeakerEntry.COLUMN_PROFILE_URL)));
        speaker.setProfilePicURL(cursor.getString(cursor.getColumnIndex(Contract.SpeakerEntry.COLUMN_PROFILE_PIC_URL)));
        return speaker;
    }
}
