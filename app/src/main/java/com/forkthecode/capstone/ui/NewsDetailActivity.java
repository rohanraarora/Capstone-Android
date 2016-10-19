package com.forkthecode.capstone.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forkthecode.capstone.R;
import com.forkthecode.capstone.data.models.News;
import com.forkthecode.capstone.network.URLConstant;
import com.forkthecode.capstone.rest.Constants;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ActionBar mActionBar;
    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mActionBar = getSupportActionBar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.news_detail_fab);
        fab.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.hasExtra(Constants.IntentConstants.NEWS_KEY)){
            news = (News) intent.getSerializableExtra(Constants.IntentConstants.NEWS_KEY);
            populateViews(news);
        }
    }

    private void populateViews(News news) {
        mActionBar.setTitle(news.getTitle());
        TextView contentTextView = (TextView)findViewById(R.id.news_detail_content_text_view);
        contentTextView.setText(news.getContent());
        TextView timestampTextView = (TextView)findViewById(R.id.news_detail_time_text_view);
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm, dd MMM");
        String timestampString = dateFormat.format(new Date(news.getTimeStamp()*1000L));
        timestampTextView.setText(timestampString);
        ImageView coverImageView = (ImageView)findViewById(R.id.news_detail_image_view);
        Picasso.with(this).load(URLConstant.BASE_URL + news.getCoverImageUrl()).into(coverImageView);

    }

    @Override
    public void onClick(View view) {
        if(news!=null && news.getUrl()!=null){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(news.getUrl()));
            try {
                startActivity(browserIntent);
            }
            catch (ActivityNotFoundException e){
                Snackbar.make(view, R.string.error_no_browser,Snackbar.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            NewsDetailActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
