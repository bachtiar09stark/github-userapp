package com.ngoding.githubuserapp.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("DELETE FROM " + Favorite.TABLE_NAME)
    void deleteAllFavorites();

    @Query("SELECT * FROM " + Favorite.TABLE_NAME + " ORDER BY " + Favorite.COLUMN_USERNAME + " DESC")
    LiveData<List<Favorite>> getAllFavorites();

    @Query("SELECT * FROM " + Favorite.TABLE_NAME + " WHERE " + Favorite.COLUMN_USERNAME + " = :username")
    Favorite getUserFavorites(String username);

    @Insert
    long insertFav(Favorite favorite);

    @Query("SELECT * FROM " + Favorite.TABLE_NAME)
    Cursor selectAll();

    @Query("DELETE FROM " + Favorite.TABLE_NAME + " WHERE " + Favorite.COLUMN_ID + " = :id")
    int deleteById(long id);

    @Update
    int update(Favorite favorite);
}
