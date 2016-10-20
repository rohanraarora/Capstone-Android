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
import com.forkthecode.capstone.data.Contract;
import com.forkthecode.capstone.data.models.Speaker;
import com.forkthecode.capstone.utilities.CursorRecyclerViewAdapter;
import com.forkthecode.capstone.utilities.DBUtils;
import com.forkthecode.capstone.utilities.Util;
import com.squareup.picasso.Picasso;

/**
 * Created by rohanarora on 20/10/16.
 */

public class SpeakerCursorAdapter extends CursorRecyclerViewAdapter<SpeakerCursorAdapter.ViewHolder> {

    Context mContext;

    public SpeakerCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(SpeakerCursorAdapter.ViewHolder viewHolder, Cursor cursor) {
        Speaker speaker = DBUtils.getSpeakerFromCursor(cursor);
        viewHolder.name.setText(speaker.getName());
        viewHolder.bio.setText(speaker.getBio());
        String imageUrl = Util.getCompleteImageUrl(speaker.getProfilePicURL());
        Picasso.with(mContext).load(imageUrl).into(viewHolder.image);
    }

    @Override
    public SpeakerCursorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_speaker,parent,false);
        return new SpeakerCursorAdapter.ViewHolder(itemView);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView bio;
        public final ImageView image;
        public ViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.speaker_name);
            bio = (TextView) itemView.findViewById(R.id.speaker_bio);
            image = (ImageView) itemView.findViewById(R.id.speaker_image);
        }

    }
}