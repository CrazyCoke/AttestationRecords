package com.gdctwh.attestationrecords.fragment.news;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gdctwh.attestationrecords.adapter.BaseHeaderFooterRVAdapter;
import com.gdctwh.attestationrecords.adapter.news.NewsOpinionListAdapter;
import com.gdctwh.attestationrecords.fragment.base.BasePullUpDownRclFragment;
import com.gdctwh.attestationrecords.fragment.base.BaseRefreshRecyclerFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpinionFragment extends BasePullUpDownRclFragment<String> {

    int count = 0;

    public OpinionFragment(){
    }

/*
    @Override
    protected void initData() {
        for (int i = 0; i < 10; i++) {
            mData.add("测试数据" + i);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new NewsOpinionListAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }
*/

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void initData() {
        super.initData();
        count = 0;
        for (int i = 0; i < 10; i++) {
            mData.add("测试数据" + i);
        }
        if (mAdapter != null && mData != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void loadingMore() {

        if (count == 2){
            noMoreData();
            return;
        }

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Thread.sleep(3000);
                e.onNext("1");
                e.onComplete();
                for (int i = 0; i < 9; i++) {
                    mData.add("test" + i);
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        loadMoreFinish();
                        count++;
                    }
                });
    }

    @Override
    protected LinearLayoutManager getmLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected BaseHeaderFooterRVAdapter getAdapter() {
        return new NewsOpinionListAdapter(mData);
    }
}
