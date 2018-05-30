package com.gdctwh.attestationrecords.acitvity.mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.acitvity.news.NewsDeatilsActivity;
import com.gdctwh.attestationrecords.acitvity.base.BaseActionBarActivity;
import com.gdctwh.attestationrecords.adapter.news.NewsHotListAdapter;
import com.gdctwh.attestationrecords.api.RecycleViewItemClickListener;
import com.gdctwh.attestationrecords.bean.NewsBean;
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.utils.ProgressDialogUtil;
import com.gdctwh.attestationrecords.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StoreUpActivity extends BaseActionBarActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private ProgressDialog progressDialog;
    private List<NewsBean.ArticlesBean> mData;
    private NewsHotListAdapter mAdapter;


    @Override
    protected void initData() {
        super.initData();

        progressDialog = ProgressDialogUtil.createProgressDialog(this, "正在加载...", true);

        RetrofitUtil.createApiService2().news(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        progressDialog.dismiss();
                        mData.clear();
                        mData.addAll(newsBean.getArticles());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void initView() {
        super.initView();
        mData = new ArrayList<>();
        mAdapter = new NewsHotListAdapter(mData, new RecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(StoreUpActivity.this, NewsDeatilsActivity.class);
                intent.putExtra(IConstants.NEWS_DATA.NEWS_ID, mData.get(postion).getArticle_id());
                startActivity(intent);
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_store_up;
    }

    @Override
    protected int getBarTitle() {
        return R.string.title_store_up;
    }

}
