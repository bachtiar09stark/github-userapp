package com.ngoding.githubuserapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ngoding.githubuserapp.database.Favorite;
import com.ngoding.githubuserapp.database.FavoriteDao;
import com.ngoding.githubuserapp.database.FavoriteDatabase;

public class FavoriteContentProvider extends ContentProvider {

    public static final String TAG = FavoriteContentProvider.class.getName();
    public static final String AUTHORITY = "com.ngoding.githubuserapp";
    public static final int ID_PERSON_DATA = 1;
    public static final int ID_PERSON_DATA_ITEM = 2;
    public static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, Favorite.TABLE_NAME, ID_PERSON_DATA);
        URI_MATCHER.addURI(AUTHORITY, Favorite.TABLE_NAME + "/*", ID_PERSON_DATA_ITEM);
    }

    private FavoriteDao favoriteDao;

    @Override
    public boolean onCreate() {
        favoriteDao = FavoriteDatabase.getInstance(getContext()).favoriteDao();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query");
        Cursor cursor;
        if (URI_MATCHER.match(uri) == ID_PERSON_DATA) {
            cursor = favoriteDao.selectAll();

            if (getContext() != null) {
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
        }
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert");

        switch (URI_MATCHER.match(uri)) {
            case ID_PERSON_DATA:
                if (getContext() != null) {
                    long id = favoriteDao.insertFav(Favorite.fromContentValues(values));
                    if (id != 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                        return ContentUris.withAppendedId(uri, id);
                    }
                }

            case ID_PERSON_DATA_ITEM:
                throw new IllegalArgumentException("Invalid URI: Insert failed" + uri);

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete");
        switch (URI_MATCHER.match(uri)) {
            case ID_PERSON_DATA:
                throw new IllegalArgumentException("Invalid uri: cannot delete");

            case ID_PERSON_DATA_ITEM:
                if (getContext() != null) {
                    int count = favoriteDao.deleteById(ContentUris.parseId(uri));
                    getContext().getContentResolver().notifyChange(uri, null);
                    return count;
                }

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        Log.d(TAG, "update");
        switch (URI_MATCHER.match(uri)) {
            case ID_PERSON_DATA:
                if (getContext() != null) {
                    int count = favoriteDao.update(Favorite.fromContentValues(values));
                    if (count != 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                        return count;
                    }
                }

            case ID_PERSON_DATA_ITEM:
                throw new IllegalArgumentException("Invalid URI: Cannot Update");

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
