package com.gdctwh.attestationrecords.acitvity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdctwh.attestationrecords.acitvity.base.BaseActionBarActivity;
import com.gdctwh.attestationrecords.bean.LoginBean;
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.utils.LogUtils;
import com.gdctwh.attestationrecords.utils.ProgressDialogUtil;
import com.gdctwh.attestationrecords.utils.RetrofitUtil;
import com.gdctwh.attestationrecords.utils.SharedPreferenceUtils;
import com.gdctwh.attestationrecords.utils.ToastUtils;
import com.gdctwh.attestationrecords.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginAndRegistActivity extends BaseActionBarActivity implements View.OnClickListener {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.edt_account)
    EditText edtAccount;
    @BindView(R.id.edt_psw)
    EditText edtPsw;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forget_psw)
    TextView tvForgetPsw;
    @BindView(R.id.lly_login)
    LinearLayout llyLogin;
    @BindView(R.id.edt_regist_phone)
    EditText edtRegistPhone;
    @BindView(R.id.edt_regist_set_uname)
    EditText edtRegistSetUname;

    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.btn_next)
    Button btnRegist;
    @BindView(R.id.tv_user_clause)
    TextView tvUserClause;
    @BindView(R.id.lly_regist)
    LinearLayout llyRegist;
    @BindView(R.id.img_wechat)
    ImageView imgWechat;
    @BindView(R.id.img_qq)
    ImageView imgQq;
    @BindView(R.id.img_weibo)
    ImageView imgWeibo;


    private Disposable mLoginDisposable;


    /**
     * 当前选中的是否是登录页面
     */
    private boolean isSelectedLogin = true;
    private Intent intent;


    private static final String TAG = "LoginAndRegistActivity";
    private ProgressDialog mProgressDialog;
    private UMShareAPI mUmShareAPI;
    private Disposable disposableSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUmShareAPI = UMShareAPI.get(this);
        //设置第三方登录，每次都出现授权页面
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        mUmShareAPI.setShareConfig(config);

    }

    protected void initEvent() {
        tvLogin.setOnClickListener(this);
        tvRegist.setOnClickListener(this);
        tvForgetPsw.setOnClickListener(this);
        tvUserClause.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRegist.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);

        imgQq.setOnClickListener(this);
        imgWechat.setOnClickListener(this);
        imgWeibo.setOnClickListener(this);

    }

    protected void initView() {
        tvLogin.setSelected(true);
        llyLogin.setVisibility(View.VISIBLE);
        llyRegist.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login_regist;
    }

    @Override
    protected int getBarTitle() {
        return R.string.title_login_regist;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
                if (!isSelectedLogin){
                    tvLogin.setSelected(true);
                    tvRegist.setSelected(false);
                    llyLogin.setVisibility(View.VISIBLE);
                    llyRegist.setVisibility(View.INVISIBLE);
                    isSelectedLogin = !isSelectedLogin;
                }
                break;
            case R.id.tv_regist:
                if (isSelectedLogin){
                    tvRegist.setSelected(true);
                    tvLogin.setSelected(false);
                    llyLogin.setVisibility(View.INVISIBLE);
                    llyRegist.setVisibility(View.VISIBLE);
                    isSelectedLogin = !isSelectedLogin;
                }
                break;
            case R.id.btn_login:
               login();
                break;
            case R.id.btn_get_code:
                String phoneNum = edtRegistPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum) && phoneNum.length() != 11){
                    ToastUtils.showShortToast(this,"请输入手机号！");
                    return;
                }
                sendCode(phoneNum);
                break;
            case R.id.btn_next:
                submitCode();

                break;
            case R.id.tv_forget_psw:
                intent = new Intent(this,ResetPswAndUserClauseActivity.class);
                intent.setAction(IConstants.ACTION_STRING.RESET_PSW);
                startActivity(intent);
                break;
            case R.id.tv_user_clause:
                intent = new Intent(this,ResetPswAndUserClauseActivity.class);
                intent.setAction(IConstants.ACTION_STRING.USER_CLAUSE);
                startActivity(intent);
                break;
            case R.id.img_qq:
                startThirdLogin(SHARE_MEDIA.QQ, mUmAuthListener);
                break;
            case R.id.img_wechat:
                startThirdLogin(SHARE_MEDIA.WEIXIN, mUmAuthListener);
                break;
            case R.id.img_weibo:
                startThirdLogin(SHARE_MEDIA.SINA, mUmAuthListener);
                break;

        }
    }

    /**
     * 调用第三方登录
     * @param sina
     * @param mUmAuthListener
     */
    private void startThirdLogin(SHARE_MEDIA sina, UMAuthListener mUmAuthListener) {
        mProgressDialog = ProgressDialogUtil.createProgressDialog(this, "请稍候...", true);
        mUmShareAPI.getPlatformInfo(this, sina, mUmAuthListener);
    }

    /**
     * 发送验证码
     */
    private void sendCode(String phoneNum) {

        //将按钮设置为不可点击
        btnGetCode.setSelected(true);
        btnGetCode.setClickable(false);
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 59; i >= 0; i--) {
                    e.onNext(i);
                    SystemClock.sleep(1000);
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableSecond = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        btnGetCode.setText(integer + "s");
                        if (integer == 0){
                            btnGetCode.setClickable(true);
                            btnGetCode.setSelected(false);
                            btnGetCode.setText("点击获取验证码");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    ToastUtils.showShortToast(LoginAndRegistActivity.this,"验证码请求成功！");
                } else{
                    // 处理错误的结果
                    ToastUtils.showShortToast(LoginAndRegistActivity.this,"验证码请求出错" + data.toString());
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(IConstants.COUNTRY_CODE, phoneNum);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode() {

        String code = edtCode.getText().toString().trim();
        String phone = edtRegistPhone.getText().toString().trim();
        final String userName = edtRegistSetUname.getText().toString().trim();

        if (TextUtils.isEmpty(phone)){
            ToastUtils.showShortToast(LoginAndRegistActivity.this,"请先输入手机号获取验证码！");
            return;
        }

        if (TextUtils.isEmpty(code)){
            ToastUtils.showShortToast(LoginAndRegistActivity.this,"请输入验证码！");
            return;
        }

        if (TextUtils.isEmpty(userName)){
            ToastUtils.showShortToast(LoginAndRegistActivity.this,"请输入用户名！");
            return;
        }

        //手机端直接验证
        final ProgressDialog progressDialog = ProgressDialogUtil.createProgressDialog(this, "正在验证...", false);
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理验证成功的结果
                    intent = new Intent(LoginAndRegistActivity.this,SubmitPswtActivity.class);
                    intent.setAction(IConstants.ACTION_STRING.SET_PSW);
                    intent.putExtra(IConstants.U_NAME,userName);
                    startActivity(intent);
                    progressDialog.dismiss();
                } else {
                    // 处理错误的结果
                    ToastUtils.showShortToast(LoginAndRegistActivity.this,"验证码错误!" );
                    progressDialog.dismiss();
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(IConstants.COUNTRY_CODE, phone, code);
    }

    private void login() {
        final String uAccount = edtAccount.getText().toString().trim();
        String uPsw = edtPsw.getText().toString().trim();
        if (TextUtils.isEmpty(uAccount) || TextUtils.isEmpty(uPsw)){
            ToastUtils.showShortToast(this,R.string.account_psw_cannot_empty);
            return;
        }
        mProgressDialog = ProgressDialogUtil.createProgressDialog(this, "登录中...",true);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                //停止登录
                if (mLoginDisposable != null){
                    //取消网络请求
                    mLoginDisposable.dispose();
                }
            }
        });
        Map<String,String> loginParams = new HashMap<>();
        loginParams.put("act","login");

        loginParams.put("mtype","0");
        loginParams.put("mid",uAccount);
        loginParams.put("password",uPsw);

        RetrofitUtil.createApiService().login(loginParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {



                    @Override
                    public void onSubscribe(Disposable d) {
                        mLoginDisposable = d;
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        mProgressDialog.dismiss();
                        LogUtils.d(TAG,loginBean.getData());
                        LogUtils.d(TAG,loginBean.getUid());
                        LogUtils.d(TAG,loginBean.getRet()+"");
                        if ( 1 == loginBean.getRet()) {
                            //判断服务端传递的信息是否完整
                            if (!TextUtils.isEmpty(loginBean.getData()) && !TextUtils.isEmpty(loginBean.getUid())) {
                                //登录成功储存session
                                SharedPreferenceUtils.putString(LoginAndRegistActivity.this, IConstants.SESSION, loginBean.getData());
                                //储存用户账号
                                SharedPreferenceUtils.putString(LoginAndRegistActivity.this,IConstants.U_ACCOUNT,uAccount);
                                //储存用户ID
                                SharedPreferenceUtils.putString(LoginAndRegistActivity.this,IConstants.U_ID,loginBean.getUid());
                            }else {
                                ToastUtils.showShortToast(getApplicationContext(), "信息获取错误,请重新登录。");
                                return;
                            }

                            ToastUtils.showShortToast(getApplicationContext(), "登录成功！");
                            finish();
                        }else {
                            ToastUtils.showShortToast(getApplicationContext(), "登录失败...");
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (disposableSecond != null) {
            disposableSecond.dispose();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if (disposableSecond != null) {
            disposableSecond.dispose();
        }
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }


    /**
     * 第三方授权回调
     */
    private UMAuthListener mUmAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            mProgressDialog.dismiss();
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            ToastUtils.showShortToast(getApplicationContext(),"Authorize succeed");

            for (Map.Entry<String, String> entry : data.entrySet()) {
                Log.d(TAG,"Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
            String name = data.get("name");//昵称
            String gender = data.get("gender");//性别
            String iconurl = data.get("iconurl");//头像
            String uid = data.get("uid");//标识

            SharedPreferenceUtils.putString(getApplicationContext(),IConstants.U_NAME,name);
            SharedPreferenceUtils.putString(getApplicationContext(),IConstants.U_HEAD,iconurl);
            SharedPreferenceUtils.putString(getApplicationContext(),IConstants.U_ACCOUNT,uid);


            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtils.showShortToast(getApplicationContext(),"Authorize fail：" +t.toString());

        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtils.showShortToast(getApplicationContext(),"Authorize cancel");

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
