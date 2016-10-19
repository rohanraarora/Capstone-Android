package com.forkthecode.capstone.rest;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forkthecode.capstone.R;
import com.forkthecode.capstone.data.Contract;
import com.forkthecode.capstone.network.URLConstant;
import com.forkthecode.capstone.utilities.CursorRecyclerViewAdapter;
import com.forkthecode.capstone.utilities.Util;
import com.squareup.picasso.Picasso;

/**
 * Created by rohanarora on 19/10/16.
 */

public class NewsCursorAdapter extends CursorRecyclerViewAdapter<NewsCursorAdapter.ViewHolder> {

    Context mContext;

    public NewsCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        viewHolder.title.setText(cursor.getString(cursor.getColumnIndex(Contract.NewsEntry.COLUMN_TITLE)));
        viewHolder.content.setText(cursor.getString(cursor.getColumnIndex(Contract.NewsEntry.COLUMN_CONTENT)));
        String imageUrl = cursor.getString(cursor.getColumnIndex(Contract.NewsEntry.COLUMN_COVER_IMAGE_URL));
        Picasso.with(mContext).load(Util.getCompleteImageUrl(imageUrl)).into(viewHolder.image);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_news,parent,false);
        return new ViewHolder(itemView);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView content;
        public final ImageView image;
        public ViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.card_news_title_text_view);
            content = (TextView) itemView.findViewById(R.id.card_news_description);
            image = (ImageView) itemView.findViewById(R.id.card_news_image_view);
        }

    }
}
