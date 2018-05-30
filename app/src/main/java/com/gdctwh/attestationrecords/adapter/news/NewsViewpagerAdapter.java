package com.gdctwh.attestationrecords.adapter.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 * 资讯页面的pageradapter
 */

public class NewsViewpagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public NewsViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments){

        mFragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragmentList.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
