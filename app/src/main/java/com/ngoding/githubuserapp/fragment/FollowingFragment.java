package com.ngoding.githubuserapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.adapter.FollowAdapter;
import com.ngoding.githubuserapp.api.ApiClient;
import com.ngoding.githubuserapp.api.ApiService;
import com.ngoding.githubuserapp.database.Favorite;
import com.ngoding.githubuserapp.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingFragment extends Fragment {

    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_FAVORITE = "extra_favorite";
    private FollowAdapter followAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView imgAnimError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgAnimError = view.findViewById(R.id.img_anim_error);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.rv_following);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        followAdapter = new FollowAdapter();
        followAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(followAdapter);

        getListFollowing();
    }

    private void getListFollowing() {
        progressBar.setVisibility(View.VISIBLE);
        imgAnimError.setVisibility(View.GONE);
        if (getActivity() != null) {
            User user = getActivity().getIntent().getParcelableExtra(EXTRA_USER);
            Favorite favorites = getActivity().getIntent().getParcelableExtra(EXTRA_FAVORITE);

            if (user != null || favorites != null) {
                if (user != null) {
                    String username = user.getUsername();
                    getFollowing(username);
                } else {
                    String username = favorites.getUsername();
                    getFollowing(username);
                }
            }
        }

    }

    private void getFollowing(String username) {
        ApiService service = ApiClient.getApiClient().create(ApiService.class);
        Call<ArrayList<User>> getFollowing = service.getFollowing(username);
        getFollowing.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    ArrayList<User> users = response.body();
                    followAdapter.setData(users);
                } else {
                    int statusCode = response.code();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    imgAnimError.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), statusCode + " : " + R.string.connection_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                imgAnimError.setVisibility(View.VISIBLE);
            }
        });
    }
}