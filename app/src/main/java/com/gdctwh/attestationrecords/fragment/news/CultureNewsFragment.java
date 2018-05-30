package com.gdctwh.attestationrecords.fragment.news;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.gdctwh.attestationrecords.acitvity.news.NewsDeatilsActivity;
import com.gdctwh.attestationrecords.adapter.BaseHeaderFooterRVAdapter;
import com.gdctwh.attestationrecords.adapter.news.CultureNewsAdapter;
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
 */
public class CultureNewsFragment extends BasePullUpDownRclFragment<NewsBean.ArticlesBean> {
    private static final String TAG = "CultureNewsFragment";
    private int category_id = 3;

    @Override
    protected void initData() {
        super.initData();
        mRefreshLayout.setRefreshing(true);
        RetrofitUtil.createApiService2().news(category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBean articlesBean) {
                        mRefreshLayout.setRefreshing(false);
                        mData.addAll(articlesBean.getArticles());
                        mAdapter.notifyDataSetChanged();
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

        if (category_id == 3) {
            noMoreData();
            return;
        }

        RetrofitUtil.createApiService2().news(category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBean articlesBean) {
                        loadMoreFinish();
                        if (articlesBean.getArticles() == null || articlesBean.getArticles().size() == 0){
                            noMoreData();
                            return;
                        }
                        mData.addAll(articlesBean.getArticles());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(getContext(),e.toString());
                        loadMoreFinish();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected LinearLayoutManager getmLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected BaseHeaderFooterRVAdapter getAdapter() {
        return new CultureNewsAdapter(mData, new RecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.d(TAG, "onItemClick: " + postion);
                Intent intent = new Intent(getContext(), NewsDeatilsActivity.class);
                intent.putExtra(IConstants.NEWS_DATA.NEWS_ID,mData.get(postion).getArticle_id());
                startActivity(intent);
            }
        });
    }
}
