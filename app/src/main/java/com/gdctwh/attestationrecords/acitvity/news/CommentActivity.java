package com.gdctwh.attestationrecords.acitvity.news;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdctwh.attestationrecords.acitvity.base.BaseActionBarActivity;
import com.gdctwh.attestationrecords.adapter.CommentAdapter;
import com.gdctwh.attestationrecords.bean.CommentAndtCollenctBean;
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.utils.ProgressDialogUtil;
import com.gdctwh.attestationrecords.utils.RetrofitUtil;
import com.gdctwh.attestationrecords.utils.ToastUtils;
import com.gdctwh.attestationrecords.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommentActivity extends BaseActionBarActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    List<CommentAndtCollenctBean.ItemsBean> mData = new ArrayList<>();

    private static final String TAG = "CommentActivity";
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.tv_no_comment)
    TextView tvNoComment;
    private CommentAdapter mAdapter;
    private ProgressDialog progressDialog;
    private String news_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        news_id = getIntent().getStringExtra(IConstants.NEWS_DATA.NEWS_ID);
        super.onCreate(savedInstanceState);

    }

    protected void initEvent() {

    }

    protected void initData() {

        for (int i = 0; i < 10; i++) {

        }
        progressDialog = ProgressDialogUtil.createProgressDialog(this, "正在加载...",true);
        RetrofitUtil.createApiService().commentAndCollect(IConstants.COMMENT_AND_COLLECT.COMMENT_DATA, news_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentAndtCollenctBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(CommentAndtCollenctBean bean) {
                        Log.d(TAG, "onNext: " + bean.isRet());
                        if (progressDialog != null){
                            progressDialog.dismiss();
                        }
                        if (bean.isRet()) {
                            mData.addAll(bean.getItems());
                            mAdapter.notifyDataSetChanged();
                            if (mData.size() != 0) {
                                tvNoComment.setVisibility(View.INVISIBLE);
                            }else {
                                tvNoComment.setVisibility(View.VISIBLE);
                            }
                        }else {
                            ToastUtils.showShortToast(getApplicationContext(),R.string.get_data_error);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog != null){
                            progressDialog.dismiss();
                        }
                        Log.e(TAG, "onError: " + e.toString());
                        ToastUtils.showShortToast(getApplicationContext(),e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    @Override
    protected int getBarTitle() {
        return R.string.comment;
    }

    protected void initView() {

        tvNoComment.setVisibility(View.INVISIBLE);

        mAdapter = new CommentAdapter(mData);
        mRecyclerview.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_comment;
    }

}
