package com.ngoding.consumerapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngoding.consumerapp.adapter.FavoriteAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String TABLE_NAME = "favorite_table";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_AVATAR = "avatar";
    private static final String AUTHORITY = "com.ngoding.githubuserapp";
    private static final Uri URI_FAVORITE = Uri.parse(
            "content://" + AUTHORITY + "/" + TABLE_NAME);
    private static final int LOADER_FAVORITE = 1;
    private ImageView imgFavNotFound;
    private FavoriteAdapter favoriteAdapter;
    private final LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @NonNull
                @Override
                public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                    return new CursorLoader(
                            getApplicationContext(), URI_FAVORITE, new String[]{COLUMN_USERNAME},
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgFavNotFound = findViewById(R.id.img_fav_not_found);
        imgFavNotFound.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.rv_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        favoriteAdapter = new FavoriteAdapter();
        favoriteAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(favoriteAdapter);

        if (favoriteAdapter.getItemCount() != 0) {
            imgFavNotFound.setVisibility(View.GONE);
        } else {
            imgFavNotFound.setVisibility(View.VISIBLE);
        }

        LoaderManager.getInstance(this).initLoader(LOADER_FAVORITE, null, mLoaderCallbacks);
    }
}
