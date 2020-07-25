package com.ngoding.consumerapp.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FavoriteRepository {
    private FavoriteDao favoriteDao;
    private LiveData<List<Favorite>> allFavorite;

    public FavoriteRepository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        favoriteDao = database.favoriteDao();
        allFavorite = favoriteDao.getAllFavorites();
    }

    public void insert(Favorite favorite) {
        new InsertFavoriteAsyncTask(favoriteDao).execute(favorite);
    }

    public void delete(Favorite favorite) {
        new DeleteFavoriteAsyncTask(favoriteDao).execute(favorite);
    }

    public void deleteAllFavorites() {
        new DeleteAllFavoriteAsyncTask(favoriteDao).execute();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorite;
    }

    private static class InsertFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private InsertFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.insert(favorites[0]);
            return null;
        }
    }

    private static class DeleteFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private DeleteFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.delete(favorites[0]);
            return null;
        }
    }

    private static class DeleteAllFavoriteAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteDao favoriteDao;

        private DeleteAllFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDao.deleteAllFavorites();
            return null;
        }
    }
}
