package com.forkthecode.capstone.rest;

/**
 * Created by rohanarora on 20/10/16.
 *
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.forkthecode.capstone.data.models.Event;
import com.forkthecode.capstone.ui.fragments.EventOverviewFragment;
import com.forkthecode.capstone.ui.fragments.SpeakerFragment;

import java.util.HashMap;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int OVERVIEW_POSITION = 0;
    private static final int SPEAKER_POSITION = 1;

    private Event mEvent;

    private HashMap<Integer,Fragment> fragmentHashMap = new HashMap<>();

    public SectionsPagerAdapter(FragmentManager fm, Event event) {
        super(fm);
        mEvent = event;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(fragmentHashMap.containsKey(position)){
            return fragmentHashMap.get(position);
        }
        switch (position){
            case OVERVIEW_POSITION:
                EventOverviewFragment eventOverviewFragment =  EventOverviewFragment.newInstance(mEvent);
                fragmentHashMap.put(position,eventOverviewFragment);
                return eventOverviewFragment;
            case SPEAKER_POSITION:
                SpeakerFragment speakerFragment =  SpeakerFragment.newInstance(mEvent);
                fragmentHashMap.put(position,speakerFragment);
                return speakerFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case OVERVIEW_POSITION:
                return "OVERVIEW";
            case SPEAKER_POSITION:
                return "SPEAKER";
        }
        return null;
    }
}