package com.forkthecode.capstone.rest;

/**
 * Created by rohanarora on 20/10/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.forkthecode.capstone.ui.fragments.EventOverviewFragment;
import com.forkthecode.capstone.ui.fragments.SpeakerFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int OVERVIEW_POSITION = 0;
    private static final int SPEAKER_POSITION = 1;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case OVERVIEW_POSITION:
                return EventOverviewFragment.newInstance(position);
            case SPEAKER_POSITION:
                return  SpeakerFragment.newInstance();
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