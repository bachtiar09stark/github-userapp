package com.ngoding.githubuserapp.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.adapter.UserAdapter;
import com.ngoding.githubuserapp.model.User;
import com.ngoding.githubuserapp.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ProgressBar progressBar;
    private View viewLoading;
    private TextView tvWelcome;
    private ImageView imgAnimSearch, imgAnimError;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        imgAnimSearch = findViewById(R.id.img_anim_search);
        imgAnimError = findViewById(R.id.img_anim_error);
        tvWelcome = findViewById(R.id.tv_welcome);
        progressBar = findViewById(R.id.progress_bar);
        viewLoading = findViewById(R.id.view_loading);
        ExtendedFloatingActionButton fabFavorite = findViewById(R.id.fab_favorite);

        recyclerView = findViewById(R.id.rv_github_user);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        showAnimSearch(true);

        homeViewModel.getUserData().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null) {
                    adapter.setData(users);
                }
            }
        });

        adapter.setOnItemClickCallback(new UserAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(User data) {
                Intent intent = new Intent(HomeActivity.this, UserDetailsActivity.class);
                intent.putExtra(UserDetailsActivity.EXTRA_USER, data);
                startActivity(intent);
            }
        });

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String username) {
                    homeViewModel.setUserData(username, progressBar, viewLoading, imgAnimError, recyclerView);
                    showAnimSearch(false);
                    searchView.clearFocus();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAnimSearch(Boolean state) {
        if (state) {
            imgAnimSearch.setVisibility(View.VISIBLE);
            tvWelcome.setVisibility(View.VISIBLE);
        } else {
            imgAnimSearch.setVisibility(View.GONE);
            tvWelcome.setVisibility(View.GONE);
        }
    }
}