package com.infoplatform.adapter;

import com.infoplatform.fragment.DynamicCheckFragment;
import com.infoplatform.fragment.UserManagerFragment;
import com.infoplatform.fragment.MyFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private int size;

    public HomeViewPagerAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DynamicCheckFragment();
            case 1:
                return new UserManagerFragment();
            case 2:
                return new MyFragment();
            default:
                return new DynamicCheckFragment();
        }
    }

    @Override
    public int getCount() {
        return size;
    }
}
