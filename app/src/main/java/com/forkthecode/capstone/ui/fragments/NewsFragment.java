package com.forkthecode.capstone.ui.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.forkthecode.capstone.data.models.News;
import com.forkthecode.capstone.rest.Constants;
import com.forkthecode.capstone.rest.NewsCursorAdapter;
import com.forkthecode.capstone.ui.NewsDetailActivity;
import com.forkthecode.capstone.utilities.DBUtils;
import com.forkthecode.capstone.utilities.RecyclerViewItemClickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int NEWS_CURSOR_LOADER_ID = 0;

    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private NewsCursorAdapter mAdapter;
    private Cursor mCursor;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(){
        return new NewsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.news_recycler_view);
        emptyTextView = (TextView)view.findViewById(R.id.news_empty_text_view);
        progressBar = (ProgressBar)view.findViewById(R.id.news_progress_bar);
        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(),
                new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        mCursor.moveToPosition(position);
                        News news = DBUtils.getNewsFromCursor(mCursor);
                        Intent newsDetailIntent = new Intent(getContext(), NewsDetailActivity.class);
                        newsDetailIntent.putExtra(Constants.IntentConstants.NEWS_KEY,news);
                        startActivity(newsDetailIntent);
                    }
                }));
        mAdapter = new NewsCursorAdapter(getContext(),null);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(NEWS_CURSOR_LOADER_ID,null,this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(NEWS_CURSOR_LOADER_ID,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Contract.NewsEntry.CONTENT_URI,
                new String[]{Contract.NewsEntry._ID, Contract.NewsEntry.COLUMN_SERVER_ID,
                        Contract.NewsEntry.COLUMN_TITLE, Contract.NewsEntry.COLUMN_CONTENT,
                        Contract.NewsEntry.COLUMN_URL, Contract.NewsEntry.COLUMN_COVER_IMAGE_URL,
                        Contract.NewsEntry.COLUMN_TIMESTAMP}, null, null, null);
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
