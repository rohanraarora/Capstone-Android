package com.forkthecode.capstone.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.forkthecode.capstone.R;
import com.forkthecode.capstone.data.CapstoneProvider;
import com.forkthecode.capstone.data.Contract;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{



    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(){
        return new NewsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Contract.NewsEntry.CONTENT_URI,
                new String[]{ Contract.NewsEntry.COLUMN_SERVER_ID, Contract.NewsEntry.COLUMN_TITLE,
                        Contract.NewsEntry.COLUMN_CONTENT, Contract.NewsEntry.COLUMN_URL,
                        Contract.NewsEntry.COLUMN_COVER_IMAGE_URL,
                        Contract.NewsEntry.COLUMN_TIMESTAMP}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Toast.makeText(getActivity(),"Load Finished",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
