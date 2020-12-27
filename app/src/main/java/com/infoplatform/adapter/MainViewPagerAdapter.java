package com.infoplatform.adapter;

import com.infoplatform.fragment.DynamicFragment;
import com.infoplatform.fragment.FriendFragment;
import com.infoplatform.fragment.MyFragment;
import com.infoplatform.fragment.SeachFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private int size;

    public MainViewPagerAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DynamicFragment();
            case 1:
                return new SeachFragment();
            case 2:
                return new FriendFragment();
            case 3:
                return new MyFragment();
            default:
                return new DynamicFragment();
        }
    }

    @Override
    public int getCount() {
        return size;
    }
}