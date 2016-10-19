package com.forkthecode.capstone.ui.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.forkthecode.capstone.R;
import com.forkthecode.capstone.utilities.RecyclerViewItemClickListener;

import static com.forkthecode.capstone.data.Contract.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int EVENT_CURSOR_LOADER_ID = 1;

    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private Cursor mCursor;


    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance(){
        return new EventsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.home_list_recycler_view);
        emptyTextView = (TextView)view.findViewById(R.id.home_list_empty_text_view);
        progressBar = (ProgressBar)view.findViewById(R.id.home_list_progress_bar);
        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(),
                new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        mCursor.moveToPosition(position);

                    }
                }));
        //mAdapter = new NewsCursorAdapter(getContext(),null);
        //recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(EVENT_CURSOR_LOADER_ID,null,this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(EVENT_CURSOR_LOADER_ID,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), EventEntry.CONTENT_URI,
                new String[]{EventEntry._ID, EventEntry.COLUMN_SERVER_ID,
                        EventEntry.COLUMN_TITLE, EventEntry.COLUMN_DESCRIPTION,
                        EventEntry.COLUMN_FROM_TIMESTAMP, EventEntry.COLUMN_TO_TIMESTAMP,
                        EventEntry.COLUMN_TICKET_URL, EventEntry.COLUMN_TICKET_PRICE,
                        EventEntry.COLUMN_VENUE_TITLE, EventEntry.COLUMN_COVER_IMAGE_LINK,
                        EventEntry.COLUMN_VENUE_LONG, EventEntry.COLUMN_VENUE_LAT},
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //mAdapter.swapCursor(data);
        mCursor = data;
        progressBar.setVisibility(View.GONE);
        if(data.getCount() == 0){
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        }
        else {
            emptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //mAdapter.swapCursor(null);

    }
}
