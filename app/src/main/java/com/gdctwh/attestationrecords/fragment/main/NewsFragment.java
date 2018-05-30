package com.gdctwh.attestationrecords.fragment.main;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gdctwh.attestationrecords.acitvity.news.SearchActivity;
import com.gdctwh.attestationrecords.adapter.news.NewsViewpagerAdapter;
import com.gdctwh.attestationrecords.fragment.news.AttestationNewsFragment;
import com.gdctwh.attestationrecords.fragment.news.CollectAuctionFragment;
import com.gdctwh.attestationrecords.fragment.news.CultureNewsFragment;
import com.gdctwh.attestationrecords.fragment.news.OpinionFragment;
import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.fragment.news.HotFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements View.OnClickListener {


    private View mView;
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private static final String TAG = "NewsFragment";
    private ImageView mImgSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_news, container, false);
        Log.d(TAG, "onCreateView: ");
        initView();
        initViewPager();
        initEvent();
        return mView;
    }

    private void initEvent() {
        mImgSearch.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mTablayout = mView.findViewById(R.id.news_tablayout);
        mViewPager = mView.findViewById(R.id.news_viewpager);
        mImgSearch = mView.findViewById(R.id.img_search);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HotFragment());
        fragmentList.add(new CultureNewsFragment());
      //  fragmentList.add(new OpinionFragment());
        fragmentList.add(new AttestationNewsFragment());
        fragmentList.add(new CollectAuctionFragment());

        NewsViewpagerAdapter mPagerAdapter = new NewsViewpagerAdapter(getChildFragmentManager());
        mPagerAdapter.setFragments(fragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        //TabLayout
        mTablayout.addTab(mTablayout.newTab());
        mTablayout.addTab(mTablayout.newTab());
       // mTablayout.addTab(mTablayout.newTab());
        mTablayout.addTab(mTablayout.newTab());
        mTablayout.addTab(mTablayout.newTab());

        mTablayout.setupWithViewPager(mViewPager);

        mTablayout.getTabAt(0).setText(R.string.tv_hot);
        mTablayout.getTabAt(1).setText(R.string.culture_news);
      //  mTablayout.getTabAt(2).setText(R.string.tv_opinion);
        mTablayout.getTabAt(2).setText(R.string.tv_authenticate_news);
        mTablayout.getTabAt(3).setText(R.string.tv_auction);

        mTablayout.setTabTextColors(Color.BLACK, Color.RED);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
