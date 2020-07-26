package com.ngoding.githubuserapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.adapter.FavoriteAdapter;
import com.ngoding.githubuserapp.viewmodel.FavoriteViewModel;
import com.ngoding.githubuserapp.widget.FavoritesWidget;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private FavoriteViewModel favoriteViewModel;
    private ExtendedFloatingActionButton fabDelete, fabDeleteSweep, fabDeleteAll;
    private Animation animFabOpen, animFabClose;
    private Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fabDelete = findViewById(R.id.fab_delete);
        fabDeleteSweep = findViewById(R.id.fab_delete_sweep);
        fabDeleteAll = findViewById(R.id.fab_delete_all);
        animFabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        animFabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        recyclerView = findViewById(R.id.rv_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        favoriteAdapter = new FavoriteAdapter();
        favoriteAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(favoriteAdapter);

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.getAllFavorites().observe(this, favorites -> favoriteAdapter.submitList(favorites));

        favoriteAdapter.setOnItemClickCallback(data -> {
            Intent intent = new Intent(FavoriteActivity.this, UserDetailsActivity.class);
            intent.putExtra(UserDetailsActivity.EXTRA_FAVORITE, data);
            startActivity(intent);
        });

        deleteUserFavorite();
    }

    private void deleteUserFavorite() {
        fabDelete.setOnClickListener(v -> {
            if (isOpen) {
                fabDeleteAll.startAnimation(animFabClose);
                fabDeleteSweep.startAnimation(animFabClose);
                fabDelete.setIconResource(R.drawable.ic_baseline_delete_24);
                fabDeleteAll.setClickable(false);
                fabDeleteSweep.setClickable(false);
                isOpen = false;

            } else {
                fabDeleteAll.startAnimation(animFabOpen);
                fabDeleteSweep.startAnimation(animFabOpen);
                fabDelete.setIconResource(R.drawable.ic_baseline_close_24);
                fabDeleteAll.setClickable(true);
                fabDeleteSweep.setClickable(true);
                isOpen = true;
            }
        });

        fabDeleteSweep.setOnClickListener(v -> {
            if (isOpen) {
                deleteSweep();
                fabDeleteAll.startAnimation(animFabClose);
                fabDeleteSweep.startAnimation(animFabClose);
                fabDelete.setIconResource(R.drawable.ic_baseline_delete_24);
                fabDeleteAll.setClickable(false);
                fabDeleteSweep.setClickable(false);
                isOpen = false;
            }
        });

        fabDeleteAll.setOnClickListener(v -> {
            if (isOpen) {
                favoriteViewModel.deleteAllFavorites();
                FavoritesWidget.sendRefreshBroadcast(FavoriteActivity.this);
                Toast.makeText(FavoriteActivity.this, R.string.txt_all_favorites_deleted, Toast.LENGTH_SHORT).show();
                fabDeleteAll.startAnimation(animFabClose);
                fabDeleteSweep.startAnimation(animFabClose);
                fabDelete.setIconResource(R.drawable.ic_baseline_delete_24);
                fabDeleteAll.setClickable(false);
                fabDeleteSweep.setClickable(false);
                isOpen = false;
            }
        });
    }

    private void deleteSweep() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favoriteViewModel.delete(favoriteAdapter.getFavoriteAt(viewHolder.getAdapterPosition()));
                FavoritesWidget.sendRefreshBroadcast(FavoriteActivity.this);
                Toast.makeText(FavoriteActivity.this, R.string.txt_favorite_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
