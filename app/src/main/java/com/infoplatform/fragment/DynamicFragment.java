package com.infoplatform.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.infoplatform.R;

import java.util.ArrayList;
import java.util.List;

//动态管理
public class DynamicFragment extends Fragment {
    static final int NUM_ITEMS = 2;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private String[] strings = new String[]{"自己的","他人的"};

    public DynamicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        fragmentList.add(new MyDynamicFragment());
        fragmentList.add(new OtherDynamicFragment());
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        DynamicFragAdapter fragmentAdater = new  DynamicFragAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdater);
        return view;
    }

    public class DynamicFragAdapter extends FragmentPagerAdapter {
        public DynamicFragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
}