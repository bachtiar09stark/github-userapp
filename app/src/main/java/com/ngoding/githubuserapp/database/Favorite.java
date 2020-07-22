package com.ngoding.githubuserapp.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Favorite.TABLE_NAME)
public class Favorite implements Parcelable {

    public static final String TABLE_NAME = "favorite_table";
    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "realName")
    private String realName;
    @ColumnInfo(name = "avatar")
    private String avatar;
    @ColumnInfo(name = "company")
    private String company;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "followers")
    private String followers;
    @ColumnInfo(name = "following")
    private String following;

    public Favorite(String username, String realName, String avatar, String company, String location, String followers, String following) {
        this.username = username;
        this.realName = realName;
        this.avatar = avatar;
        this.company = company;
        this.location = location;
        this.followers = followers;
        this.following = following;
    }

    public Favorite(Parcel in) {
        id = in.readInt();
        username = in.readString();
        realName = in.readString();
        avatar = in.readString();
        company = in.readString();
        location = in.readString();
        followers = in.readString();
        following = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(realName);
        dest.writeString(avatar);
        dest.writeString(company);
        dest.writeString(location);
        dest.writeString(followers);
        dest.writeString(following);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getFollowers() {
        return followers;
    }

    public String getFollowing() {
        return following;
    }

}
