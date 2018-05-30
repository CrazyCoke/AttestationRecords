package com.gdctwh.attestationrecords.fragment.main;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdctwh.attestationrecords.acitvity.LoginAndRegistActivity;
import com.gdctwh.attestationrecords.acitvity.SubmitPswtActivity;
import com.gdctwh.attestationrecords.acitvity.mine.AuthenticateActivity;
import com.gdctwh.attestationrecords.acitvity.mine.CollectActivity;
import com.gdctwh.attestationrecords.acitvity.mine.DynamicActivity;
import com.gdctwh.attestationrecords.acitvity.mine.SettingActivity;
import com.gdctwh.attestationrecords.acitvity.mine.StoreUpActivity;
import com.gdctwh.attestationrecords.acitvity.mine.SubscribeActivity;
import com.gdctwh.attestationrecords.api.ApiService;
import com.gdctwh.attestationrecords.bean.LogOutBean;
import com.gdctwh.attestationrecords.bean.UserInfoBean;
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.utils.ProgressDialogUtil;
import com.gdctwh.attestationrecords.utils.RetrofitUtil;
import com.gdctwh.attestationrecords.utils.SharedPreferenceUtils;
import com.gdctwh.attestationrecords.utils.ToastUtils;
import com.gdctwh.attestationrecords.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.mine_title)
    TextView mineTitle;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.img_head)
    RoundedImageView imgHead;
    @BindView(R.id.tv_mine_uname)
    TextView tvMineUname;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.tv_mine_dynamic)
    TextView tvMineDynamic;
    @BindView(R.id.tv_mine_authenticate)
    TextView tvMineAuthenticat;
    @BindView(R.id.tv_mine_collect)
    TextView tvMineCollect;
    @BindView(R.id.tv_mine_subscribe)
    TextView tvMineSubscribe;
    @BindView(R.id.tv_mine_storeup)
    TextView tvMineStoreup;
    @BindView(R.id.tv_mine_setting)
    TextView tvMineSetting;
    Unbinder unbinder;

    private static final String TAG = "MineFragment";
    @BindView(R.id.btn_logout)
    Button btnLogout;
    private ProgressDialog progressDialog;
    private Disposable mDisposable;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initEvent();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        String session = SharedPreferenceUtils.getString(getContext(), IConstants.SESSION, null);
        String u_id = SharedPreferenceUtils.getString(getContext(), IConstants.U_ID, null);
        String u_account = SharedPreferenceUtils.getString(getContext(), IConstants.U_ACCOUNT, null);
        String u_name = SharedPreferenceUtils.getString(getContext(), IConstants.U_NAME, null);
        String u_head = SharedPreferenceUtils.getString(getContext(), IConstants.U_HEAD, null);


        //判断是否有已经储存的用户名和头像
        if (!TextUtils.isEmpty(u_name)) {
            Glide.with(getContext()).load(u_head).error(R.mipmap.img_default_head).into(imgHead);
            tvMineUname.setText(u_name);
            //登录成功之后改字段不可点击
            tvMineUname.setClickable(false);
            //显示退出登录按钮
            btnLogout.setVisibility(View.VISIBLE);
            return;
        } else {
            btnLogout.setVisibility(View.INVISIBLE);
        }


        //判断是否有已经储存的用户session
        if (!TextUtils.isEmpty(session) && !TextUtils.isEmpty(u_id) && !TextUtils.isEmpty(u_account)) {
            //有已经储存的用户。加载用户信息
            ApiService apiService = RetrofitUtil.createApiService();
//?act=getinfo&mtype=0&uid=10&sessionid=s6lsvpq6bgklemmllcbivmrnp1
            Map<String, String> params = new HashMap<>();
            params.put("act", "getinfo");
            params.put("mtype", "0");
            params.put("mid", u_account);
            params.put("uid", "10");
            params.put("phpsessionid", "s6lsvpq6bgklemmllcbivmrnp1");
            apiService.getUerInfo(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserInfoBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(UserInfoBean userInfoBean) {
                            Log.d(TAG, "onNext: " + userInfoBean.getUsername());
                            if (userInfoBean.getRet() == 1) {
                                tvMineUname.setText(userInfoBean.getUsername());
                                Glide.with(getContext()).load(userInfoBean.getImage()).error(R.mipmap.img_default_head).into(imgHead);
                                tvMineUname.setClickable(false);

                                btnLogout.setVisibility(View.VISIBLE);

                                //保存用户头像和用户名
                                SharedPreferenceUtils.putString(getContext(), IConstants.U_NAME, userInfoBean.getUsername());
                                SharedPreferenceUtils.putString(getContext(), IConstants.U_HEAD, userInfoBean.getImage());
                            } else {
                                ToastUtils.showShortToast(getContext(), "用户信息获取失败");
                                btnLogout.setVisibility(View.INVISIBLE);
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
    }

    private void initEvent() {
        imgHead.setOnClickListener(this);
        tvMineDynamic.setOnClickListener(this);
        tvMineAuthenticat.setOnClickListener(this);
        tvMineCollect.setOnClickListener(this);
        tvMineStoreup.setOnClickListener(this);
        tvMineSubscribe.setOnClickListener(this);
        tvMineUname.setOnClickListener(this);
        tvMineSetting.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    private void initView() {
        tvMineUname.setText("点击登录");
        btnLogout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_head:

                break;
            case R.id.tv_mine_uname:
                intent = new Intent(getContext(), LoginAndRegistActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mine_dynamic:
                intent = new Intent(getContext(), DynamicActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mine_collect:
                intent = new Intent(getContext(), CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mine_subscribe:
                intent = new Intent(getContext(), SubscribeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mine_storeup:
                intent = new Intent(getContext(), StoreUpActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mine_setting:
                intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mine_authenticate:
                intent = new Intent(getContext(), AuthenticateActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_logout:
                logout();
                break;
        }

    }

    private void logout() {
        progressDialog = ProgressDialogUtil.createProgressDialog(getContext(), "正在退出...", true);

       /* ToastUtils.showShortToast(getContext(),"已退出登录");
        SharedPreferenceUtils.putString(getContext(),IConstants.SESSION,null);
        SharedPreferenceUtils.putString(getContext(),IConstants.U_ID,null);
        SharedPreferenceUtils.putString(getContext(),IConstants.U_HEAD,null);
        SharedPreferenceUtils.putString(getContext(),IConstants.U_NAME,null);*/

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mDisposable != null) {
                    mDisposable.dispose();
                }
            }
        });


        Map<String, String> params = new HashMap<>();
        params.put("act", "logout");
        params.put("uid", SharedPreferenceUtils.getString(getContext(), IConstants.U_ID, ""));
        params.put("sessionid", SharedPreferenceUtils.getString(getContext(), IConstants.SESSION, ""));
        RetrofitUtil.createApiService().logout(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogOutBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(LogOutBean logOutBean) {
                        progressDialog.dismiss();
                        if (logOutBean.ret) {
                            ToastUtils.showShortToast(getContext(), "已退出登录");
                            SharedPreferenceUtils.putString(getContext(), IConstants.SESSION, null);
                            SharedPreferenceUtils.putString(getContext(), IConstants.U_ID, null);
                            SharedPreferenceUtils.putString(getContext(), IConstants.U_HEAD, null);
                            SharedPreferenceUtils.putString(getContext(), IConstants.U_NAME, null);
                            // SharedPreferenceUtils.putString(getContext(),IConstants.U_ACCOUNT,null);

                            tvMineUname.setClickable(true);
                            tvMineUname.setText("点击登录");
                            imgHead.setImageResource(R.mipmap.img_default_head);
                            btnLogout.setVisibility(View.INVISIBLE);
                        } else {

                        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
