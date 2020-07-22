package com.ngoding.githubuserapp.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.fragment.FollowersFragment;
import com.ngoding.githubuserapp.fragment.FollowingFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_followers,
            R.string.tab_following
    };

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FollowersFragment();
                break;
            case 1:
                fragment = new FollowingFragment();
                break;
        }
        assert fragment != null;
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
