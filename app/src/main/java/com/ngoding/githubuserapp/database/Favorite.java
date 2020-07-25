package com.ngoding.githubuserapp.database;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Favorite.TABLE_NAME)
public class Favorite implements Parcelable {

    public static final String TABLE_NAME = "favorite_table";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_REAL_NAME = "realName";
    public static final String COLUMN_AVATAR = "avatar";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_FOLLOWERS = "followers";
    public static final String COLUMN_FOLLOWING = "following";

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
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;
    @ColumnInfo(name = COLUMN_USERNAME)
    private String username;
    @ColumnInfo(name = COLUMN_REAL_NAME)
    private String realName;
    @ColumnInfo(name = COLUMN_AVATAR)
    private String avatar;
    @ColumnInfo(name = COLUMN_COMPANY)
    private String company;
    @ColumnInfo(name = COLUMN_LOCATION)
    private String location;
    @ColumnInfo(name = COLUMN_FOLLOWERS)
    private String followers;
    @ColumnInfo(name = COLUMN_FOLLOWING)
    private String following;

    /*
    public Favorite(String username, String realName, String avatar, String company, String location, String followers, String following) {
        this.username = username;
        this.realName = realName;
        this.avatar = avatar;
        this.company = company;
        this.location = location;
        this.followers = followers;
        this.following = following;
    }
     */

    public Favorite() {
    }

    protected Favorite(Parcel in) {
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
        dest.writeLong(id);
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public static Favorite fromContentValues(ContentValues values) {
        Favorite favorite = new Favorite();
        if (values != null && values.containsKey(COLUMN_ID)) {
            favorite.setId(values.getAsInteger(COLUMN_ID));
        }
        if (values != null && values.containsKey(COLUMN_USERNAME)) {
            favorite.setUsername(values.getAsString(COLUMN_USERNAME));
        }
        if (values != null && values.containsKey(COLUMN_REAL_NAME)) {
            favorite.setRealName(values.getAsString(COLUMN_REAL_NAME));
        }
        if (values != null && values.containsKey(COLUMN_AVATAR)) {
            favorite.setAvatar(values.getAsString(COLUMN_AVATAR));
        }
        if (values != null && values.containsKey(COLUMN_COMPANY)) {
            favorite.setCompany(values.getAsString(COLUMN_COMPANY));
        }
        if (values != null && values.containsKey(COLUMN_LOCATION)) {
            favorite.setLocation(values.getAsString(COLUMN_LOCATION));
        }
        if (values != null && values.containsKey(COLUMN_FOLLOWERS)) {
            favorite.setFollowers(values.getAsString(COLUMN_FOLLOWERS));
        }
        if (values != null && values.containsKey(COLUMN_FOLLOWING)) {
            favorite.setFollowing(values.getAsString(COLUMN_FOLLOWING));
        }
        return favorite;
    }

}
