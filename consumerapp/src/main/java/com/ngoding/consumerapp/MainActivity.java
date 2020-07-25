package com.ngoding.consumerapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngoding.consumerapp.adapter.FavoriteAdapter;
import com.ngoding.consumerapp.database.Favorite;

public class MainActivity extends AppCompatActivity {

    private FavoriteAdapter favoriteAdapter;
    private static final String AUTHORITY = "com.ngoding.githubuserapp.provider";
    private static final Uri URI_FAVORITE = Uri.parse(
            "content://" + AUTHORITY + "/" + Favorite.TABLE_NAME);
    private static final int LOADER_FAVORITE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        favoriteAdapter = new FavoriteAdapter();
        favoriteAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(favoriteAdapter);

        LoaderManager.getInstance(this).initLoader(LOADER_FAVORITE, null, mLoaderCallbacks);
    }

    private final LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            return new CursorLoader(
                    getApplicationContext(), URI_FAVORITE, new String[]{Favorite.COLUMN_USERNAME},
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            favoriteAdapter.setFavorites(data);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            favoriteAdapter.setFavorites(null);
        }
    };
}
