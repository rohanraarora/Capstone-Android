package com.forkthecode.capstone.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.forkthecode.capstone.R;
import com.forkthecode.capstone.data.models.Event;
import com.forkthecode.capstone.rest.Constants;

/**
 * Implementation of App Widget functionality.
 */
public class CapstoneWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            int layoutId = R.layout.capstone_widget;
            RemoteViews widget=new RemoteViews(context.getPackageName(), layoutId);

            Intent serviceIntent = new Intent(context,CapstoneWidgetService.class);
            widget.setRemoteAdapter(R.id.widget_list_view,serviceIntent);
            widget.setEmptyView(R.id.widget_list_view, R.id.empty_view);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_list_view);
            appWidgetManager.updateAppWidget(appWidgetId, widget);

        }

        super.onUpdate(context,appWidgetManager,appWidgetIds);

    }

}

