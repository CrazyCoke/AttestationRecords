package com.gdctwh.attestationrecords.fragment.news;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gdctwh.attestationrecords.acitvity.news.NewsDeatilsActivity;
import com.gdctwh.attestationrecords.adapter.BaseHeaderFooterRVAdapter;
import com.gdctwh.attestationrecords.api.RecycleViewItemClickListener;
import com.gdctwh.attestationrecords.bean.NewsBean;
import com.gdctwh.attestationrecords.fragment.base.BasePullUpDownRclFragment;
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.utils.RetrofitUtil;
import com.gdctwh.attestationrecords.utils.ToastUtils;
import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.adapter.news.NewsHotListAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends BasePullUpDownRclFragment<NewsBean.ArticlesBean> {


    private static final String TAG = "HotFragment";
    private Banner mBanner;
    Intent intent;

    private List<NewsBean.ArticlesBean> mBannerData = new ArrayList<>();

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        setHeaderAdapter();
    }

    @Override
    protected void initData() {
        super.initData();
        mRefreshLayout.setRefreshing(true);
        //加载轮播图
        RetrofitUtil.createApiService2().news(IConstants.CATEGROY_BANNER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        mBannerData.clear();
                        mBannerData.addAll(newsBean.getArticles());
                        initBanner();
                    }

                    @Override
                    public void onError(Throwable e) {
                       ToastUtils.showShortToast(getContext(),"轮播图："+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


        //加载新闻数据
        RetrofitUtil.createApiService2().news(IConstants.CATEGROY_CTNEWS)
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
                        Log.e(TAG, "onError: " + e.toString() );
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    /**
     * 加载更多
     */
    public void loadingMore() {
      noMoreData();
    }

    @Override
    protected LinearLayoutManager getmLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected BaseHeaderFooterRVAdapter getAdapter() {
        return new NewsHotListAdapter(mData, new RecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (postion == 0){
                    //头布局不处理
                    return;
                }
                intent = new Intent(getContext(), NewsDeatilsActivity.class);
                //加入了头布局这里减1
                intent.putExtra(IConstants.NEWS_DATA.NEWS_ID,mData.get(postion - 1).getArticle_id());
                startActivity(intent);
            }
        });
    }


    private void setHeaderAdapter() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.header_news_hot_layout, null);
        mBanner = header.findViewById(R.id.news_hot_banner);
        mAdapter.setHeaderView(header);
    }

    //数据加载完成后设置顶部的轮播图
    private void initBanner() {
        List<String> imgs = new ArrayList<>(); //图片地址
        List<String> titles = new ArrayList<>(); //标题
        for (int i = 0; i < mBannerData.size(); i++) {
            imgs.add( mBannerData.get(i).getOriginal());
            titles.add(mBannerData.get(i).getName());
        }

        mBanner.setImages(imgs)
                .setBannerTitles(titles)
                .setDelayTime(6000)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(getContext()).load((String) path).into(imageView);
                    }
                }).start();


        //轮播图的点击监听
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.d(TAG, "OnBannerClick: " + position);
                if (mBannerData != null) {
                    intent = new Intent(getContext(), NewsDeatilsActivity.class);
                    intent.putExtra(IConstants.NEWS_DATA.NEWS_ID, mBannerData.get(position).getArticle_id());
                    startActivity(intent);
                }else {
                    ToastUtils.showShortToast(getContext(),R.string.data_error);
                }
            }
        });

    }


}
