package com.ngoding.consumerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngoding.consumerapp.adapter.FavoriteAdapter;
import com.ngoding.consumerapp.database.Favorite;
import com.ngoding.consumerapp.viewmodel.FavoriteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FavoriteAdapter favoriteAdapter;

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

        FavoriteViewModel favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                favoriteAdapter.submitList(favorites);
            }
        });
    }
}
