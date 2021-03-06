package com.forkthecode.capstone.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.forkthecode.capstone.R;
import com.forkthecode.capstone.data.Contract;
import com.forkthecode.capstone.data.models.Event;
import com.forkthecode.capstone.data.models.News;
import com.forkthecode.capstone.data.models.Speaker;
import com.forkthecode.capstone.network.responses.EventsResponse;
import com.forkthecode.capstone.ui.fragments.EventsFragment;
import com.forkthecode.capstone.ui.fragments.InfoFragment;
import com.forkthecode.capstone.ui.fragments.NewsFragment;
import com.forkthecode.capstone.network.ApiClient;
import com.forkthecode.capstone.network.NetworkDataManager;
import com.forkthecode.capstone.network.responses.NewsResponse;
import com.forkthecode.capstone.utilities.ActivityUtils;
import com.forkthecode.capstone.utilities.DBUtils;
import com.forkthecode.capstone.widget.CapstoneWidget;
import com.forkthecode.capstone.widget.CapstoneWidgetListProvider;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;

public class HomeActivity extends AppCompatActivity implements OnTabSelectListener {


    private FirebaseAnalytics mFirebaseAnalytics;

    private ActionBar actionBar;

    private static final int TAB_ID_EVENTS = R.id.tab_events;
    private static final int TAB_ID_NEWS = R.id.tab_news;
    private static final int TAB_ID_INFO = R.id.tab_info;

    private static final int CONTAINER_ID = R.id.home_container;

    private HashMap<Integer,Fragment> fragmentHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN,null);

        actionBar = getSupportActionBar();

        BottomBar mBottomBar = (BottomBar) this.findViewById(R.id.home_bottom_bar);
        mBottomBar.setOnTabSelectListener(this);

        updateNews();
        updateEvents();
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId){
            case TAB_ID_EVENTS:
                selectEventsTab();
                break;
            case TAB_ID_NEWS:
                selectNewsTab();
                break;
            case  TAB_ID_INFO:
                selectInfoTab();
                break;
        }
    }

    private void selectInfoTab() {
        actionBar.setTitle(getString(R.string.community_name));
        InfoFragment fragment;
        if(!fragmentHashMap.containsKey(R.id.tab_info)) {
            fragment = InfoFragment.newInstance();
            fragmentHashMap.put(R.id.tab_info,fragment);
        }
        else {
            fragment = (InfoFragment) fragmentHashMap.get(R.id.tab_info);
        }
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),fragment,CONTAINER_ID);
    }

    private void selectNewsTab() {
        actionBar.setTitle(getString(R.string.tab_title_news));
        NewsFragment fragment;
        if(!fragmentHashMap.containsKey(R.id.tab_news)) {
            fragment = NewsFragment.newInstance();
            fragmentHashMap.put(R.id.tab_news,fragment);
        }
        else {
            fragment = (NewsFragment) fragmentHashMap.get(R.id.tab_news);
        }
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),fragment,CONTAINER_ID);
    }

    private void selectEventsTab() {
        actionBar.setTitle(getString(R.string.tab_title_events));
        EventsFragment fragment;
        if(!fragmentHashMap.containsKey(R.id.tab_events)) {
            fragment = EventsFragment.newInstance();
            fragmentHashMap.put(R.id.tab_events,fragment);
        }
        else {
            fragment = (EventsFragment) fragmentHashMap.get(R.id.tab_events);
        }
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),fragment,CONTAINER_ID);

    }

    private void updateNews(){
        Call<NewsResponse> call = ApiClient.apiService().getNews();
        NetworkDataManager<NewsResponse> manager = new NetworkDataManager<>();
        NetworkDataManager.NetworkResponseListener listener = manager.new NetworkResponseListener() {
            @Override
            public void onSuccessResponse(NewsResponse response) {
                List<News> newsList = response.getData().getNews();
                Vector<ContentValues> cVVector = new Vector<ContentValues>(newsList.size());
                for(News news:newsList){
                    ContentValues newsValues = DBUtils.cvFromNews(news);
                    cVVector.add(newsValues);
                }
                if ( cVVector.size() > 0 ) {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);
                    getContentResolver().bulkInsert(Contract.NewsEntry.CONTENT_URI, cvArray);
                }
            }

            @Override
            public void onFailure(int code, String message) {
                Toast.makeText(HomeActivity.this,message,Toast.LENGTH_SHORT).show();
            }


        };
        manager.execute(call,listener);
    }

    private void updateEvents(){
        Call<EventsResponse> call = ApiClient.apiService().getEvents();
        NetworkDataManager<EventsResponse> manager = new NetworkDataManager<>();
        NetworkDataManager.NetworkResponseListener listener = manager.new NetworkResponseListener() {
            @Override
            public void onSuccessResponse(EventsResponse response) {
                List<Event> events = response.getData().getEvents();
                Vector<ContentValues> cVVector = new Vector<ContentValues>(events.size());
                for(Event event:events){
                    ContentValues contentValues = DBUtils.cvFromEvent(event);
                    cVVector.add(contentValues);
                    ArrayList<Speaker> speakers = event.getSpeakers();
                    Vector<ContentValues> speakerContentValuesVector = new Vector<>();
                    for(Speaker speaker:speakers){
                        speaker.setEventId(event.getServerId());
                        ContentValues speakerContentValues = DBUtils.cvFromSpeaker(speaker);
                        speakerContentValuesVector.add(speakerContentValues);
                    }
                    if(speakerContentValuesVector.size()>0){
                        ContentValues[] speakerCVArray = new ContentValues[speakerContentValuesVector.size()];
                        speakerContentValuesVector.toArray(speakerCVArray);
                        getContentResolver().bulkInsert(Contract.SpeakerEntry.CONTENT_URI,speakerCVArray);
                    }
                }
                if ( cVVector.size() > 0 ) {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);
                    getContentResolver().bulkInsert(Contract.EventEntry.CONTENT_URI, cvArray);
                }
                updateWidget();
            }

            @Override
            public void onFailure(int code, String message) {
                Toast.makeText(HomeActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        };
        manager.execute(call,listener);
    }

    private void updateWidget() {
        ComponentName name = new ComponentName(this, CapstoneWidget.class);
        int [] ids = AppWidgetManager.getInstance(this).getAppWidgetIds(name);
        Intent intent = new Intent(this,CapstoneWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }
}
