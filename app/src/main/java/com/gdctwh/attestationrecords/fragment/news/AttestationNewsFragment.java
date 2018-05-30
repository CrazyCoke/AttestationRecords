package com.gdctwh.attestationrecords.fragment.news;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gdctwh.attestationrecords.acitvity.news.NewsDeatilsActivity;
import com.gdctwh.attestationrecords.adapter.AttestationNewsAdapter;
import com.gdctwh.attestationrecords.adapter.BaseHeaderFooterRVAdapter;
import com.gdctwh.attestationrecords.api.RecycleViewItemClickListener;
import com.gdctwh.attestationrecords.bean.NewsBean;
import com.gdctwh.attestationrecords.fragment.base.BasePullUpDownRclFragment;
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.utils.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * 鉴证新闻页面
 */
public class AttestationNewsFragment extends BasePullUpDownRclFragment<NewsBean.ArticlesBean> {

    private Intent intent;

    @Override
    protected void initData() {
        super.initData();
        RetrofitUtil.createApiService2().news(4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBean bean) {
                        mData.addAll(bean.getArticles());
                        mAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
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
        return  new LinearLayoutManager(getContext());
    }

    @Override
    protected BaseHeaderFooterRVAdapter getAdapter() {
        return new AttestationNewsAdapter(mData, new RecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                intent = new Intent(getContext(), NewsDeatilsActivity.class);
                intent.putExtra(IConstants.NEWS_DATA.NEWS_ID,mData.get(postion).getArticle_id());
                startActivity(intent);
            }
        });
    }

}
