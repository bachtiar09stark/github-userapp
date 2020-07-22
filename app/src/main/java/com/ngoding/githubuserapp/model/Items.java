package com.ngoding.githubuserapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Items {

    @SerializedName("items")
    ArrayList<User> items;

    public ArrayList<User> getUserList() {
        return items;
    }
}
