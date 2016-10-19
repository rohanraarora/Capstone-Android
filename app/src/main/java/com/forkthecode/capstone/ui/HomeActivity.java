package com.forkthecode.capstone.ui;

import android.content.ContentValues;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.forkthecode.capstone.R;
import com.forkthecode.capstone.data.Contract;
import com.forkthecode.capstone.data.models.News;
import com.forkthecode.capstone.ui.fragments.EventsFragment;
import com.forkthecode.capstone.ui.fragments.InfoFragment;
import com.forkthecode.capstone.ui.fragments.NewsFragment;
import com.forkthecode.capstone.network.ApiClient;
import com.forkthecode.capstone.network.NetworkDataManager;
import com.forkthecode.capstone.network.responses.NewsResponse;
import com.forkthecode.capstone.utilities.ActivityUtils;
import com.forkthecode.capstone.utilities.DBUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;

public class HomeActivity extends AppCompatActivity implements OnTabSelectListener {


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

        actionBar = getSupportActionBar();

        BottomBar mBottomBar = (BottomBar) this.findViewById(R.id.home_bottom_bar);
        mBottomBar.setOnTabSelectListener(this);

        updateNews();
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
        actionBar.setTitle(getString(R.string.tab_title_info));
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
                int inserted = 0;
                // add to database
                if ( cVVector.size() > 0 ) {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);
                    getContentResolver().bulkInsert(Contract.NewsEntry.CONTENT_URI, cvArray);
                }
            }

            @Override
            public void onFailure(int code, String message) {
                Toast.makeText(HomeActivity.this,"News Error: " + message,Toast.LENGTH_SHORT).show();
            }


        };
        manager.execute(call,listener);
    }
}
