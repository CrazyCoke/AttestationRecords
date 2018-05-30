package com.gdctwh.attestationrecords.fragment.news;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gdctwh.attestationrecords.acitvity.news.NewsDeatilsActivity;
import com.gdctwh.attestationrecords.adapter.BaseHeaderFooterRVAdapter;
import com.gdctwh.attestationrecords.adapter.news.NewsAuctionAdapter;
import com.gdctwh.attestationrecords.api.RecycleViewItemClickListener;
import com.gdctwh.attestationrecords.bean.NewsBean;
import com.gdctwh.attestationrecords.fragment.base.BasePullUpDownRclFragment;
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.utils.RetrofitUtil;
import com.gdctwh.attestationrecords.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * 收藏拍卖页面
 */
public class CollectAuctionFragment extends BasePullUpDownRclFragment<NewsBean.ArticlesBean> {

    @Override
    protected void initData() {
        super.initData();
        mRefreshLayout.setRefreshing(true);
        RetrofitUtil.createApiService2().news(IConstants.CATEGROY_COLLECT_ACTION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        mData.addAll(newsBean.getArticles());
                        mAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(getContext(),e.toString());
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void loadingMore() {
        noMoreData();
    }

    @Override
    protected LinearLayoutManager getmLayoutManager() {
        mRecyclerView.setPadding(7,0,7,0);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        //设置底布局 占整行
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mAdapter.isFooterView(position)){
                    return gridLayoutManager.getSpanCount();
                }else {
                    return 1;
                }
            }
        });
        return gridLayoutManager;
    }

    @Override
    protected BaseHeaderFooterRVAdapter getAdapter() {
        return new NewsAuctionAdapter(mData, new RecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                //没有头布局
                Intent intent = new Intent(getContext(), NewsDeatilsActivity.class);
                intent.putExtra(IConstants.NEWS_DATA.NEWS_ID,mData.get(postion).getArticle_id());
                startActivity(intent);
            }
        });
    }

}
