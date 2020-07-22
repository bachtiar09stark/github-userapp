package com.ngoding.githubuserapp.api;

import com.ngoding.githubuserapp.model.Items;
import com.ngoding.githubuserapp.model.User;
import com.ngoding.githubuserapp.model.UserDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/search/users") // https://api.github.com/search/users?q={username}
    @Headers("Authorization: token 4da9050ee97dae1e7c716398f6fbc94b86f75228")
    Call<Items> getUser(@Query("q") String username);

    @GET("/users/{username}") // https://api.github.com/users/{username}
    @Headers("Authorization: token 4da9050ee97dae1e7c716398f6fbc94b86f75228")
    Call<UserDetails> getUserDetails(@Path("username") String username);

    @GET("/users/{username}/followers") // https://api.github.com/users/{username}/followers
    @Headers("Authorization: token 4da9050ee97dae1e7c716398f6fbc94b86f75228")
    Call<ArrayList<User>> getFollowers(@Path("username") String username);

    @GET("/users/{username}/following") // https://api.github.com/users/{username}/following
    @Headers("Authorization: token 4da9050ee97dae1e7c716398f6fbc94b86f75228")
    Call<ArrayList<User>> getFollowing(@Path("username") String username);
}
