package com.ngoding.githubuserapp.viewmodel;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.api.ApiClient;
import com.ngoding.githubuserapp.api.ApiService;
import com.ngoding.githubuserapp.model.Items;
import com.ngoding.githubuserapp.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<User>> listUser;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        listUser = new MutableLiveData<>();
    }

    public void setUserData(final String username, final ProgressBar progressBar,
                            final View viewLoading, final ImageView imgAnimError,
                            final RecyclerView recyclerView) {
        progressBar.setVisibility(View.VISIBLE);
        viewLoading.setVisibility(View.VISIBLE);
        imgAnimError.setVisibility(View.GONE);
        ApiService service = ApiClient.getApiClient().create(ApiService.class);
        Call<Items> getUser = service.getUser(username);
        getUser.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    viewLoading.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    assert response.body() != null;
                    ArrayList<User> users = response.body().getUserList();

                    listUser.postValue(users);
                } else {
                    int statusCode = response.code();
                    progressBar.setVisibility(View.GONE);
                    viewLoading.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    imgAnimError.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplication().getApplicationContext(), statusCode + " : " + R.string.connection_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                viewLoading.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                imgAnimError.setVisibility(View.VISIBLE);
                Toast.makeText(getApplication().getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<ArrayList<User>> getUserData() {
        return listUser;
    }
}

