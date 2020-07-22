package com.ngoding.githubuserapp.model;

import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @SerializedName("login")
    private String username;

    @SerializedName("name")
    private String realName;

    @SerializedName("avatar_url")
    private String avatar;

    @SerializedName("company")
    private String company;

    @SerializedName("location")
    private String location;

    @SerializedName("followers")
    private String followers;

    @SerializedName("following")
    private String following;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}
