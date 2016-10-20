package com.forkthecode.capstone.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by rohanarora on 20/10/16.
 */
public class CapstoneWidgetService  extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CapstoneWidgetListProvider(this.getApplicationContext(),intent);
    }
}

