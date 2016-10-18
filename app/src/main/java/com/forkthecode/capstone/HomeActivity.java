package com.forkthecode.capstone;

import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.forkthecode.capstone.utilities.ActivityUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends AppCompatActivity implements OnTabSelectListener {


    ActionBar actionBar;

    private static final int TAB_ID_EVENTS = R.id.tab_events;
    private static final int TAB_ID_NEWS = R.id.tab_news;
    private static final int TAB_ID_INFO = R.id.tab_info;

    private static final int CONTAINER_ID = R.id.home_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        actionBar = getSupportActionBar();

        BottomBar mBottomBar = (BottomBar) this.findViewById(R.id.home_bottom_bar);
        mBottomBar.setOnTabSelectListener(this);
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
        InfoFragment fragment = InfoFragment.newInstance();
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),fragment,CONTAINER_ID);
    }

    private void selectNewsTab() {
        actionBar.setTitle(getString(R.string.tab_title_news));
        NewsFragment fragment = NewsFragment.newInstance();
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),fragment,CONTAINER_ID);

    }

    private void selectEventsTab() {
        actionBar.setTitle(getString(R.string.tab_title_events));
        EventsFragment fragment = EventsFragment.newInstance();
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),fragment,CONTAINER_ID);

    }
}
