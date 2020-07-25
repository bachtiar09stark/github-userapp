package com.ngoding.githubuserapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.adapter.SectionsPagerAdapter;
import com.ngoding.githubuserapp.api.ApiClient;
import com.ngoding.githubuserapp.api.ApiService;
import com.ngoding.githubuserapp.database.Favorite;
import com.ngoding.githubuserapp.database.FavoriteDatabase;
import com.ngoding.githubuserapp.model.User;
import com.ngoding.githubuserapp.model.UserDetails;
import com.ngoding.githubuserapp.viewmodel.FavoriteViewModel;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_FAVORITE = "extra_favorite";

    private TextView tvName, tvLocation, tvCompany, tvFollowers, tvFollowing, tvToolbar, tvNotFound;
    private ImageView imgAvatar;
    private CircleImageView circleAvatar;
    private ProgressBar progressBar;
    private ExtendedFloatingActionButton fabFavorite;
    private FavoriteViewModel favoriteViewModel;
    private FavoriteDatabase database;
    private Boolean statusFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        database = Room.databaseBuilder(getApplicationContext(),
                FavoriteDatabase.class, "favorite_database")
                .allowMainThreadQueries()
                .build();

        progressBar = findViewById(R.id.progress_bar);
        imgAvatar = findViewById(R.id.img_avatar);
        circleAvatar = findViewById(R.id.circle_avatar);
        fabFavorite = findViewById(R.id.fab_favorite);
        tvName = findViewById(R.id.tv_name);
        tvLocation = findViewById(R.id.tv_location);
        tvCompany = findViewById(R.id.tv_company);
        tvFollowers = findViewById(R.id.tv_followers);
        tvFollowing = findViewById(R.id.tv_following);
        tvToolbar = findViewById(R.id.tv_toolbar);
        tvNotFound = findViewById(R.id.tv_not_found);

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        setStatusFavorite(statusFavorite);

        getUserDetail();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tab_layout);
        tabs.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(UserDetailsActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUserDetail() {
        progressBar.setVisibility(View.VISIBLE);
        tvNotFound.setVisibility(View.GONE);
        User user = getIntent().getParcelableExtra(EXTRA_USER);
        Favorite favorites = getIntent().getParcelableExtra(EXTRA_FAVORITE);

        if (user != null || favorites != null) {
            if (user != null) {
                tvToolbar.setText(user.getUsername());
                String username = user.getUsername();
                Favorite favorite = database.favoriteDao().getUserFavorites(username);
                if (favorite != null) {
                    getDetailFromDatabase(favorite, user);
                } else {
                    getDetailFromApi(username);
                }
            } else {
                tvToolbar.setText(favorites.getUsername());
                String username = favorites.getUsername();
                Favorite favorite = database.favoriteDao().getUserFavorites(username);
                getDetailFromDatabase(favorite, null);
            }
        }
    }

    private void getDetailFromApi(final String username) {
        ApiService service = ApiClient.getApiClient().create(ApiService.class);
        Call<UserDetails> getUserDetails = service.getUserDetails(username);
        getUserDetails.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    final UserDetails userDetails = response.body();
                    if (userDetails != null) {
                        final String name = userDetails.getRealName();
                        final String company = userDetails.getCompany();
                        final String location = userDetails.getLocation();
                        final String followers = userDetails.getFollowers();
                        final String following = userDetails.getFollowing();
                        final String avatar = userDetails.getAvatar();

                        tvName.setText(name);
                        tvCompany.setText(company);
                        tvLocation.setText(location);
                        tvFollowers.setText(followers);
                        tvFollowing.setText(following);

                        Glide.with(UserDetailsActivity.this)
                                .load(avatar)
                                .into(imgAvatar);
                        Glide.with(UserDetailsActivity.this)
                                .load(avatar)
                                .into(circleAvatar);

                        fabFavorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*
                                Favorite favorite = new Favorite(username, name, avatar,
                                        company, location, followers, following);
                                 */
                                Favorite favorite = new Favorite();
                                favorite.setUsername(username);
                                favorite.setRealName(name);
                                favorite.setAvatar(avatar);
                                favorite.setCompany(company);
                                favorite.setLocation(location);
                                favorite.setFollowing(following);
                                favorite.setFollowers(followers);
                                insertFavorite(favorite);
                            }
                        });

                    } else {
                        int statusCode = response.code();
                        progressBar.setVisibility(View.GONE);
                        tvNotFound.setVisibility(View.VISIBLE);
                        Toast.makeText(UserDetailsActivity.this, statusCode + " : " + R.string.connection_error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
                Toast.makeText(UserDetailsActivity.this, R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailFromDatabase(final Favorite favorite, final User user) {
        progressBar.setVisibility(View.GONE);

        tvName.setText(favorite.getRealName());
        tvCompany.setText(favorite.getCompany());
        tvLocation.setText(favorite.getLocation());
        tvFollowers.setText(favorite.getFollowers());
        tvFollowing.setText(favorite.getFollowing());
        statusFavorite = !statusFavorite;
        setStatusFavorite(statusFavorite);

        Glide.with(UserDetailsActivity.this)
                .load(favorite.getAvatar())
                .into(imgAvatar);
        Glide.with(UserDetailsActivity.this)
                .load(favorite.getAvatar())
                .into(circleAvatar);

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFavorite(favorite, user);
            }
        });
    }

    private void deleteFavorite(Favorite favorite, User user) {
        favoriteViewModel.delete(favorite);
        statusFavorite = !statusFavorite;
        setStatusFavorite(statusFavorite);
        if (user != null) {
            Intent intent = new Intent(UserDetailsActivity.this, FavoriteActivity.class);
            startActivity(intent);
        }
        Toast.makeText(UserDetailsActivity.this, R.string.txt_favorite_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void insertFavorite(Favorite favorite) {
        favoriteViewModel.insert(favorite);
        statusFavorite = !statusFavorite;
        setStatusFavorite(statusFavorite);
        Intent intent = new Intent(UserDetailsActivity.this, FavoriteActivity.class);
        startActivity(intent);
        Toast.makeText(UserDetailsActivity.this, R.string.txt_favorite_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setStatusFavorite(Boolean statusFavorite) {
        if (statusFavorite) {
            fabFavorite.setIconResource(R.drawable.ic_baseline_favorite_24);
        } else {
            fabFavorite.setIconResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }
}
