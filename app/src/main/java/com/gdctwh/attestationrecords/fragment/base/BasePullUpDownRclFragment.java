package com.gdctwh.attestationrecords.fragment.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdctwh.attestationrecords.adapter.BaseHeaderFooterRVAdapter;
import com.gdctwh.attestationrecords.R;


/**
 * Created by Administrator on 2018/4/2.
 */

public abstract class BasePullUpDownRclFragment<T> extends BaseRefreshRecyclerFragment<T> {
    protected BaseHeaderFooterRVAdapter mAdapter;
    protected int mLastVisibleItemPosition;
    protected boolean isLoadingMore = false; //是否正在加载更多
    protected Animation mAnimation;
    protected View footerView;
    protected ImageView imgLoadMore;
    protected TextView tvLoadMore;
    private static final String TAG = "BasePullUpDownRclFragme";
    protected LinearLayoutManager mLayoutManager;

    @Override
    public void initView() {
        super.initView();
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.progress);

    }

    @Override
    protected void initData() {
        if (mAnimation != null){
            mAnimation.cancel();
        }
        imgLoadMore.setImageResource(R.mipmap.arrows_pull_up);
        tvLoadMore.setText(R.string.tv_pull_up_loading_more);
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = getAdapter();
        mLayoutManager = getmLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.footer_list_layout, mRecyclerView, false);
        imgLoadMore = footerView.findViewById(R.id.img_loading_more);
        tvLoadMore = footerView.findViewById(R.id.tv_loading_more);
        mAdapter.setFooterView(footerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "onScrollStateChanged: lastVisibleItemPosition" + mLastVisibleItemPosition);
                Log.d(TAG, "onScrollStateChanged: newState" + newState);
                //当手指离开屏幕 滑动结束，并且最后一个item（底布局）已在屏幕完全显示出来就开启加载更多功能
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    if (mRefreshLayout.isRefreshing()) {
                        //正在下拉刷新的时候不可以加载更多。
                        return;
                    }
                    if (isLoadingMore) {
                        //当前正在加载更多，就return
                        return;
                    } else {
                        prepareLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 准备加载更多 更改界面  开启动画
     */
    private void prepareLoadMore() {

        imgLoadMore.setImageResource(R.mipmap.footer_load);
        //开启动画
        imgLoadMore.startAnimation(mAnimation);
        tvLoadMore.setText(R.string.is_loading);
        isLoadingMore = true;
        loadingMore();
    }

    /**
     * 加载更多实际功能交给子类实现，
     * 在数据请求完成之后 需要立即调用 loadMoreFinish方法
     */
    protected abstract void loadingMore();

    /**
     * 加载更多的数据请求回来之后调用次方法 取消动画 更改界面  更改加载更多的状态
     */
    protected void loadMoreFinish() {
        isLoadingMore = false;
        imgLoadMore.clearAnimation();
        imgLoadMore.setImageResource(R.mipmap.arrows_pull_up);
        tvLoadMore.setText(R.string.tv_pull_up_loading_more);
    }

    /**
     * 加载更多去请求数据的时候  发现没有更多数据了  调用此方法
     */
    protected void noMoreData() {
        isLoadingMore = false;
        imgLoadMore.clearAnimation();
        imgLoadMore.setImageResource(R.mipmap.stop);
        tvLoadMore.setText(R.string.tv_no_more);

    }

    /**
     * @return
     */
    protected abstract LinearLayoutManager getmLayoutManager();


    /**
     * 多态 的方式返回一个adapter
     *
     * @return
     */
    protected abstract BaseHeaderFooterRVAdapter getAdapter();
}
