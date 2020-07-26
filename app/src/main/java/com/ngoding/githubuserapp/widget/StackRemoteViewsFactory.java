package com.ngoding.githubuserapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.database.Favorite;

import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    int mAppWidgetId;
    private Context context;
    private Cursor cursor;

    public static final String TABLE_NAME = "favorite_table";
    private static final String AUTHORITY = "com.ngoding.githubuserapp";
    private static final Uri URI_FAVORITE = Uri.parse(
            "content://" + AUTHORITY + "/" + TABLE_NAME);

    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(
                URI_FAVORITE, null, null, null, null
        );
    }

    private Favorite getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Error");
        }

        return new Favorite(cursor);
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Favorite favorite = getItem(position);
        String avatar = favorite.getAvatar();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;
        try {
            bmp = Glide.with(context)
                    .asBitmap()
                    .load(avatar)
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        remoteViews.setImageViewBitmap(R.id.imgWidget, bmp);

        Bundle bundle = new Bundle();
        bundle.putInt(FavoritesWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.imgWidget, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
