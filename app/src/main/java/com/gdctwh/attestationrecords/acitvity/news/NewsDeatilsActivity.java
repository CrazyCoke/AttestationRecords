package com.gdctwh.attestationrecords.acitvity.news;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdctwh.attestationrecords.acitvity.base.BaseActionBarActivity;
import com.gdctwh.attestationrecords.bean.NewsDetailsBean;
import com.gdctwh.attestationrecords.global.App;
import com.gdctwh.attestationrecords.utils.DelHTMLTagUtil;
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.utils.ProgressDialogUtil;
import com.gdctwh.attestationrecords.utils.RetrofitUtil;
import com.gdctwh.attestationrecords.utils.SharedPreferenceUtils;
import com.gdctwh.attestationrecords.utils.ToastUtils;
import com.gdctwh.attestationrecords.utils.URLImageParser;
import com.gdctwh.attestationrecords.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsDeatilsActivity extends BaseActionBarActivity implements View.OnClickListener {

    NewsDetailsBean data;

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
    @BindView(R.id.details_root_view)
    LinearLayout detailsRootView;




    private static final String TAG = "CultureNewsDeatilsActiv";
    private String news_id;
    private ProgressDialog progressDialog;
    private Disposable mDisposable;
    /**
     * 弹出框的 内容
     */
    private String[] arraySize;
    private int sizeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arraySize = getResources().getStringArray(R.array.size);

        /*UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);*/

    }

    protected void initEvent() {
        tvComment.setOnClickListener(this);
        tvShared.setOnClickListener(this);
    }

    protected void initView() {

       // tvContent.setMovementMethod(LinkMovementMethod.getInstance()); //设置超链接可以打开网页。
        tvTag.setVisibility(View.GONE);
        sizeCount = SharedPreferenceUtils.getInt(App.getInstance(), IConstants.TEXT_SIZE, 1);
        tvContent.setTextSize(getResources().getIntArray(R.array.size_int)[sizeCount]);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_news_details;
    }

    @Override
    protected int getBarTitle() {
        return R.string.title_back;
    }

    protected void initData() {

        //获取列表里面传递过来的详情id
        news_id = getIntent().getStringExtra(IConstants.NEWS_DATA.NEWS_ID);

        progressDialog = ProgressDialogUtil.createProgressDialog(this, "正在加载...",true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mDisposable != null){
                    mDisposable.dispose();
                }
            }
        });
        RetrofitUtil.createApiService2().cultureDetails(news_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsDetailsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(NewsDetailsBean newsDetailsBean) {
                        progressDialog.dismiss();
                        data = newsDetailsBean;
                        showContent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                        progressDialog.dismiss();
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
        tvContent.setText(Html.fromHtml(data.getDescription(),new URLImageParser(this,tvContent),null));
        tvTag.setText(data.getDate());
        tvTitle.setText(data.getHeading_title());
        tvDate.setText(data.getDate_modified());
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
                showPopupWindow();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopupWindow() {
        sizeCount = SharedPreferenceUtils.getInt(App.getInstance(),IConstants.TEXT_SIZE,1);
        AlertDialog sizeDialog = new AlertDialog.Builder(this)
                .setTitle("字体大小")
                .setSingleChoiceItems(arraySize, sizeCount, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       tvContent.setTextSize(getResources().getIntArray(R.array.size_int)[which]);
                       SharedPreferenceUtils.putInt(App.getInstance(), IConstants.TEXT_SIZE, which);
                    }
                })
                .setNegativeButton("确定",null)
                .create();
        sizeDialog.show();
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
            case R.id.tv_shared:
                //权限
                if(Build.VERSION.SDK_INT>=23){
                    String[] mPermissionList = new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                           // ,Manifest.permission.ACCESS_FINE_LOCATION
                            //,Manifest.permission.READ_LOGS
                            ,Manifest.permission.READ_PHONE_STATE
                            , Manifest.permission.READ_EXTERNAL_STORAGE
                            //,Manifest.permission.SET_DEBUG_APP
                            ,Manifest.permission.SYSTEM_ALERT_WINDOW
                           // ,Manifest.permission.GET_ACCOUNTS
                            ,Manifest.permission.WRITE_APN_SETTINGS
                    };
                    ActivityCompat.requestPermissions(this,mPermissionList,123);
                }

                UMImage umImg = new UMImage(NewsDeatilsActivity.this,data.getOriginal());
                UMWeb web = new UMWeb("http://120.77.214.194:8086/index.php?" +
                        "route=newsblog/article&newsblog_path=3&newsblog_article_id=" + news_id);
                web.setTitle(data.getHeading_title());
                web.setDescription(DelHTMLTagUtil.delHTMLTag(data.getDescription()));
                web.setThumb(umImg);
                new ShareAction(this)
                        .withText(data.getHeading_title())
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .withMedia(web)
                        .setCallback(shareListener).open();
                break;
        }
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //开始分享

        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShortToast(NewsDeatilsActivity.this,"成功！");


        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShortToast(NewsDeatilsActivity.this,t.toString());
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShortToast(NewsDeatilsActivity.this,"取消分享");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
