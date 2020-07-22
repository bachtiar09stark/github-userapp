package com.ngoding.githubuserapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("DELETE FROM " + Favorite.TABLE_NAME)
    void deleteAllFavorites();

    @Query("SELECT * FROM " + Favorite.TABLE_NAME + " ORDER BY username DESC")
    LiveData<List<Favorite>> getAllFavorites();

    @Query("SELECT * FROM " + Favorite.TABLE_NAME + " WHERE username = :username")
    Favorite getUserFavorites(String username);
}
