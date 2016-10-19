package com.forkthecode.capstone.rest;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forkthecode.capstone.R;
import com.forkthecode.capstone.data.models.Event;
import com.forkthecode.capstone.network.URLConstant;
import com.forkthecode.capstone.utilities.CursorRecyclerViewAdapter;
import com.forkthecode.capstone.utilities.DBUtils;
import com.forkthecode.capstone.utilities.Util;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rohanarora on 19/10/16.
 */

public class EventCursorAdapter extends CursorRecyclerViewAdapter<EventCursorAdapter.ViewHolder> {

    Context mContext;

    public EventCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(EventCursorAdapter.ViewHolder viewHolder, Cursor cursor) {
        Event event = DBUtils.getEventFromCursor(cursor);
        viewHolder.title.setText(event.getTitle());
        viewHolder.description.setText(event.getDescription());
        viewHolder.venue.setText(event.getVenueTitle());
        if(event.isPastEvent()){
            viewHolder.pastStatus.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.pastStatus.setVisibility(View.GONE);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, kk:mm");
        String duration = dateFormat.format(new Date(event.getFromTimestamp()*1000L));
        if(event.getToTimestamp() > event.getFromTimestamp()){
            duration = duration + " to " + dateFormat.format(new Date(event.getToTimestamp()*1000L));
        }
        viewHolder.duration.setText(duration);
        Picasso.with(mContext).load(Util.getCompleteImageUrl(event.getCoverImageURL()))
                .into(viewHolder.image);
    }

    @Override
    public EventCursorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_event,parent,false);
        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView description;
        public final TextView duration;
        public final TextView venue;
        public final TextView pastStatus;
        public final ImageView image;
        public ViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.card_event_title_text_view);
            description = (TextView) itemView.findViewById(R.id.card_event_description);
            duration = (TextView)itemView.findViewById(R.id.card_event_duration_text_view);
            venue = (TextView)itemView.findViewById(R.id.card_event_venue_text_view);
            pastStatus = (TextView)itemView.findViewById(R.id.card_event_past_text_view);
            image = (ImageView) itemView.findViewById(R.id.card_event_image_view);
        }

    }
}
