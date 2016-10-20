package com.forkthecode.capstone.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.forkthecode.capstone.R;
import com.forkthecode.capstone.data.Contract;
import com.forkthecode.capstone.data.models.Event;
import com.forkthecode.capstone.rest.Constants;
import com.forkthecode.capstone.ui.EventDetailActivity;
import com.forkthecode.capstone.utilities.DBUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rohanarora on 20/10/16.
 */
public class CapstoneWidgetListProvider implements RemoteViewsService.RemoteViewsFactory {


    Context mContext;
    Cursor mCursor;
    public CapstoneWidgetListProvider(Context context, Intent intent){
        mContext = context;

    }

    public void updateCursor(){
        if(mCursor!=null){
            mCursor.close();
            mCursor = null;
        }
        final long identityToken = Binder.clearCallingIdentity();
        mCursor = mContext.getContentResolver().query(Contract.EventEntry.CONTENT_URI,
                new String[]{Contract.EventEntry._ID, Contract.EventEntry.COLUMN_SERVER_ID,
                        Contract.EventEntry.COLUMN_TITLE, Contract.EventEntry.COLUMN_DESCRIPTION,
                        Contract.EventEntry.COLUMN_FROM_TIMESTAMP, Contract.EventEntry.COLUMN_TO_TIMESTAMP,
                        Contract.EventEntry.COLUMN_TICKET_URL, Contract.EventEntry.COLUMN_TICKET_PRICE,
                        Contract.EventEntry.COLUMN_VENUE_TITLE, Contract.EventEntry.COLUMN_COVER_IMAGE_LINK,
                        Contract.EventEntry.COLUMN_VENUE_LONG, Contract.EventEntry.COLUMN_VENUE_LAT},
                Contract.EventEntry.COLUMN_FROM_TIMESTAMP + " > ?",
                new String[]{String.valueOf(System.currentTimeMillis()/1000L)},
                Contract.EventEntry.COLUMN_FROM_TIMESTAMP + " ASC");
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onCreate() {
        updateCursor();
    }

    @Override
    public void onDataSetChanged() {
        mCursor.close();
        updateCursor();
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_row_layout_event);
        mCursor.moveToPosition(i);
        Event event = DBUtils.getEventFromCursor(mCursor);
        remoteViews.setTextViewText(R.id.widget_event_row_title,event.getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, kk:mm");
        String duration = dateFormat.format(new Date(event.getFromTimestamp()*1000L));
        remoteViews.setTextViewText(R.id.widget_event_row_start_datetime,duration);
        remoteViews.setTextViewText(R.id.widget_event_row_venue,event.getVenueTitle());
        remoteViews.setTextViewText(R.id.widget_event_row_description,event.getDescription());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}