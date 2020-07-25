package com.ngoding.consumerapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ngoding.consumerapp.database.Favorite;
import com.ngoding.consumerapp.database.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository repository;
    private LiveData<List<Favorite>> allFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
        allFavorites = repository.getAllFavorites();
    }

    public void insert(Favorite favorite) {
        repository.insert(favorite);
    }

    public void delete(Favorite favorite) {
        repository.delete(favorite);
    }

    public void deleteAllFavorites() {
        repository.deleteAllFavorites();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorites;
    }
}
