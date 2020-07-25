package com.ngoding.githubuserapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favorite.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase INSTANCE;

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDatabase.class, "favorite_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract FavoriteDao favoriteDao();
}
