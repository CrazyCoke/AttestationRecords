package com.gdctwh.attestationrecords.fragment.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdctwh.attestationrecords.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/21.
 */

public abstract class BaseRefreshRecyclerFragment<T> extends Fragment {

    protected View mRootView;
    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mRefreshLayout;
    protected List<T> mData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.base_refresh_recycle_layout,container,false);
        initView();
        initRecyclerView();
        initEvent();
        initData();
        return mRootView;
    }

    protected abstract void initData();

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mData.clear();
                initData();
            }
        });
    }

    /**
     *
     * 子类 去实现 加载自己的RecyclerView的不同的布局管理器以及Adapter
     *
     */
    protected abstract void initRecyclerView();

    public void initView() {
        mRecyclerView = mRootView.findViewById(R.id.recyclerview);
        mRefreshLayout = mRootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setColorSchemeColors(Color.BLUE);
    }
}
