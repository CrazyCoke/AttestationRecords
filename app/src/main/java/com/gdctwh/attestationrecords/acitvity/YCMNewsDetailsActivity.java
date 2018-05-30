package com.gdctwh.attestationrecords.acitvity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gdctwh.attestationrecords.acitvity.news.CommentActivity;
import com.gdctwh.attestationrecords.bean.CommentAndtCollenctBean;
import com.gdctwh.attestationrecords.bean.HotNewsDetailsBean;
import com.gdctwh.attestationrecords.global.App;
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.utils.ProgressDialogUtil;
import com.gdctwh.attestationrecords.utils.RetrofitUtil;
import com.gdctwh.attestationrecords.utils.SharedPreferenceUtils;
import com.gdctwh.attestationrecords.utils.ToastUtils;
import com.gdctwh.attestationrecords.utils.URLImageParser;
import com.gdctwh.attestationrecords.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class YCMNewsDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "YCMNewsDetailsActivity";
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_post_comment)
    TextView tvPostComment;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_storeup)
    TextView tvStoreup;
    @BindView(R.id.tv_shared)
    TextView tvShared;
    private Calendar calendar;
    private ProgressDialog progressDialog;
    private HotNewsDetailsBean mHotNewsDetailsBean;
    private String news_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);

        initActionbar();
        initView();
        //解析前面页面传递过来的数据
        news_id = getIntent().getStringExtra(IConstants.NEWS_DATA.NEWS_ID);
        String news_date = getIntent().getStringExtra(IConstants.NEWS_DATA.NEWS_DATE);
        Log.d(TAG, "onCreate: news_date:" + news_date);
        if (TextUtils.isEmpty(news_id) || TextUtils.isEmpty(news_date)) {
            ToastUtils.showShortToast(this, R.string.data_error);
            finish();
        } else {
            Log.d(TAG, "onCreate: news_id-----" + news_id);
            Log.d(TAG, "onCreate: news_date-----" + news_date);//2018-04-02 07:41:03
            //取出date中的年月日 用作url的拼接。
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(news_date);
                calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String dayStr = null;
                String monthStr = null;
                if (month < 10) {
                    monthStr = "0" + month;
                }else {
                    monthStr = "" + month;
                }
                if (day < 10) {
                    dayStr = "0" + day;
                }else {
                    dayStr = "" +day;
                }

                String yearAndMonth = year + "-" + monthStr;
                String page = "content_" + news_id + ".json";
                Log.d(TAG, "yearAndMonth:" + yearAndMonth + "-----page:" + page);
                initData(yearAndMonth, dayStr, page);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        initEvent();

    }

    private void initEvent() {
        tvComment.setOnClickListener(this);
    }

    private void initView() {
        tvContent.setMovementMethod(LinkMovementMethod.getInstance()); //设置超链接可以打开网页。

        int textSize = SharedPreferenceUtils.getInt(App.getInstance(), IConstants.TEXT_SIZE, 15);
        tvContent.setTextSize(textSize);
    }


    private void initData(String yearAndMonth, String day, String page) {
        progressDialog = ProgressDialogUtil.createProgressDialog(this, "正在加载...",true);
        RetrofitUtil.createApiService().hotNewsDetails(yearAndMonth, day, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotNewsDetailsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HotNewsDetailsBean hotNewsDetailsBean) {
                        mHotNewsDetailsBean = hotNewsDetailsBean;
                        Log.d(TAG, "onNext: " + hotNewsDetailsBean.getContent());
                        progressDialog.dismiss();

                        //将获取的数据显示到界面。
                        showContent();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: 1111" + e.toString());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        RetrofitUtil.createApiService().commentAndCollect(IConstants.COMMENT_AND_COLLECT.COMMENT_NUM,news_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentAndtCollenctBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommentAndtCollenctBean bean) {
                        if (bean.isRet()){
                            int commentNum = bean.getNum();
                            if (commentNum > 999){
                                tvComment.setText("999+");
                            }else {
                                tvComment.setText(commentNum+"");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 将获取的数据显示到界面
     */
    private void showContent() {
            tvContent.setText(Html.fromHtml(mHotNewsDetailsBean.getContent(),new URLImageParser(this,tvContent),null));
            tvTag.setText(mHotNewsDetailsBean.getSource());
            tvTitle.setText(mHotNewsDetailsBean.getTitle());
            tvDate.setText(mHotNewsDetailsBean.getDate());
            tvContent.setTextSize(18);
    }

    private void initActionbar() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(R.string.title_back);
    }

    /**
     * 加载自定义的actionbar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.font_size:
                Log.d(TAG, "onOptionsItemSelected: 你点击了设置字体大小按钮。");
                showPopupWindow();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopupWindow() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_comment:
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra(IConstants.NEWS_DATA.NEWS_ID,news_id);
                startActivity(intent);
                break;
        }
    }
}
