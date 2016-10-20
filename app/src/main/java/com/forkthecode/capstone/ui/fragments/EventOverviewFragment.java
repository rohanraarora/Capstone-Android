package com.forkthecode.capstone.ui.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forkthecode.capstone.R;
import com.forkthecode.capstone.data.models.Event;
import com.forkthecode.capstone.rest.Constants;
import com.forkthecode.capstone.utilities.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rohanarora on 20/10/16.
 */

public class EventOverviewFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static Event mEvent;

    public EventOverviewFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EventOverviewFragment newInstance(@NonNull Event event) {
        Util.checkNotNull(event);
        mEvent = event;
        EventOverviewFragment fragment = new EventOverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.IntentConstants.EVENT_KEY, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_overview, container, false);
        TextView durationTextView = (TextView)rootView.findViewById(R.id.event_overview_duration_text_view);
        TextView venueTextView = (TextView)rootView.findViewById(R.id.event_overview_venue_text_view);
        TextView descriptionTextView = (TextView)rootView.findViewById(R.id.event_overview_description_text_view);
        TextView ticketTextView = (TextView)rootView.findViewById(R.id.event_overview_ticket_text_view);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, kk:mm");
        String duration = dateFormat.format(new Date(mEvent.getFromTimestamp()*1000L));
        if(mEvent.getToTimestamp() > mEvent.getFromTimestamp()){
            duration = duration + " to " + dateFormat.format(new Date(mEvent.getToTimestamp()*1000L));
        }
        durationTextView.setText(duration);
        durationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDurationClicked();
            }
        });

        venueTextView.setText(mEvent.getVenueTitle());
        venueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVenueClicked();
            }
        });

        descriptionTextView.setText(mEvent.getDescription());

        if(mEvent.shouldShowGetTickets()){
            ticketTextView.setVisibility(View.VISIBLE);
            ticketTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGetTicketsClicked(v);
                }
            });
        }
        else ticketTextView.setVisibility(View.GONE);

        return rootView;
    }

    private void onGetTicketsClicked(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(mEvent.getTitcketURL()));
        if(browserIntent.resolveActivity(getContext().getPackageManager())!=null){
            startActivity(browserIntent);
        }
        else{
            Snackbar.make(view, R.string.error_no_browser,Snackbar.LENGTH_SHORT).show();
        }
    }

    private void onVenueClicked() {
        double latitude = mEvent.getVenueLat();
        double longitude = mEvent.getVenueLong();
        String geoString = "geo:" + latitude + "," + longitude + "?z=15";
        Uri gmmIntentUri = Uri.parse(geoString);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void onDurationClicked() {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, mEvent.getTitle());
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                mEvent.getFromTimestamp()*1000L);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                mEvent.getToTimestamp()*1000L);
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
        intent.putExtra(CalendarContract.Events.DESCRIPTION,mEvent.getDescription());
        if(intent.resolveActivity(getContext().getPackageManager())!=null){
            startActivity(intent);
        }
    }
}
