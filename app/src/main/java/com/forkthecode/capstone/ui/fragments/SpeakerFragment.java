package com.forkthecode.capstone.ui.fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.forkthecode.capstone.data.Contract;
import com.forkthecode.capstone.data.models.Event;
import com.forkthecode.capstone.data.models.Speaker;
import com.forkthecode.capstone.rest.Constants;
import com.forkthecode.capstone.rest.EventCursorAdapter;
import com.forkthecode.capstone.rest.SpeakerCursorAdapter;
import com.forkthecode.capstone.ui.EventDetailActivity;
import com.forkthecode.capstone.utilities.DBUtils;
import com.forkthecode.capstone.utilities.RecyclerViewItemClickListener;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeakerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private FirebaseAnalytics mFirebaseAnalytics;

    public static final int SPEAKER_CURSOR_LOADER_ID = 2;

    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private Cursor mCursor;
    private SpeakerCursorAdapter mAdapter;
    private static Event mEvent;


    public SpeakerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    public static SpeakerFragment newInstance(@NonNull Event event){
        mEvent = event;
        return new SpeakerFragment();
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
                        Speaker speaker = DBUtils.getSpeakerFromCursor(mCursor);

                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(speaker.getServerId()));
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, speaker.getName());
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "speaker");
                        mFirebaseAnalytics.logEvent("Open Speaker Profile", bundle);

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(speaker.getProfileURL()));
                        try{
                            startActivity(browserIntent);
                        }
                        catch (ActivityNotFoundException e){
                            Snackbar.make(recyclerView, R.string.error_no_browser,Snackbar.LENGTH_SHORT).show();
                        }

                    }
                }));
        mAdapter = new SpeakerCursorAdapter(getContext(),null);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(SPEAKER_CURSOR_LOADER_ID,null,this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(SPEAKER_CURSOR_LOADER_ID,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Contract.SpeakerEntry.CONTENT_URI,
                new String[]{Contract.SpeakerEntry._ID, Contract.SpeakerEntry.COLUMN_SERVER_ID,
                        Contract.SpeakerEntry.COLUMN_EVENT_SERVER_ID, Contract.SpeakerEntry.COLUMN_BIO,
                        Contract.SpeakerEntry.COLUMN_NAME, Contract.SpeakerEntry.COLUMN_PROFILE_URL,
                        Contract.SpeakerEntry.COLUMN_PROFILE_PIC_URL},
                Contract.SpeakerEntry.COLUMN_EVENT_SERVER_ID + " = ?",
                new String[]{String.valueOf(mEvent.getServerId())}, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
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
        mAdapter.swapCursor(null);
    }
}
