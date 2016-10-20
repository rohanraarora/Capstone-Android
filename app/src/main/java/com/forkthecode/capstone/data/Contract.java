package com.forkthecode.capstone.data;

/**
 * Created by rohanarora on 19/10/16.
 *
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {


    public static final String CONTENT_AUTHORITY = "com.forkthecode.capstone";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_NEWS = "news";
    public static final String PATH_EVENTS = "events";
    public static final String PATH_SPEAKERS = "speakers";


    public static final class NewsEntry implements BaseColumns  {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;

        public static final String TABLE_NAME = "news";
        public static final String COLUMN_SERVER_ID = "server_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_COVER_IMAGE_URL = "cover_image_url";
        public static final String COLUMN_TIMESTAMP = "timestamp";

        public static Uri buildNewsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class SpeakerEntry implements  BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPEAKERS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENTS;

        public static final String TABLE_NAME = "speakers";
        public static final String COLUMN_EVENT_SERVER_ID = "event_id";
        public static final String COLUMN_SERVER_ID = "server_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BIO = "bio";
        public static final String COLUMN_PROFILE_URL = "profile_url";
        public static final String COLUMN_PROFILE_PIC_URL = "profile_pic_url";

        public static Uri buildSpeakerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


    public static final class EventEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENTS;

        public static final String TABLE_NAME = "events";
        public static final String COLUMN_SERVER_ID = "server_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_FROM_TIMESTAMP = "from_timestamp";
        public static final String COLUMN_TO_TIMESTAMP = "to_timestamp";
        public static final String COLUMN_TICKET_URL = "ticker_url";
        public static final String COLUMN_TICKET_PRICE= "ticket_price";
        public static final String COLUMN_VENUE_TITLE = "venue_title";
        public static final String COLUMN_VENUE_LAT = "venue_lat";
        public static final String COLUMN_VENUE_LONG = "venue_long";
        public static final String COLUMN_COVER_IMAGE_LINK = "cover_image_link";

        public static Uri buildEventUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
