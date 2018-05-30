package com.gdctwh.attestationrecords.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdctwh.attestationrecords.R;

/**
 * Created by Administrator on 2018/3/29.
 * progressdialog工具类
 */

public class ProgressDialogUtil {
    private final static int mProDialogWidth = 320;
    private final static int mProDialogHeight = 35;
    private final static int mMarginTop = 80;

    public static ProgressDialog createProgressDialog (Context context,String hint,boolean cancelable){
        //加载自定义的布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout,null);
        LinearLayout progressLayout = view.findViewById(R.id.lly_progress);
        ImageView imgLoading = view.findViewById(R.id.img_loading);
        //旋转动画
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.progress);
        imgLoading.startAnimation(animation);
        TextView tvHint = view.findViewById(R.id.tv_loading_hint);
        tvHint.setText(hint);

        //创建ProgressDialog，设置不可以通过back键或界面点击取消
        ProgressDialog proDialog = new ProgressDialog(context,R.style.MyDialogStyle);//设置主题
        proDialog.setCancelable(cancelable);
        proDialog.setCanceledOnTouchOutside(false);
        proDialog.show();
        proDialog.setContentView(view);

        //设置布局参数
        WindowManager.LayoutParams params = proDialog.getWindow().getAttributes();
        proDialog.getWindow().setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
        params.width = getScreenWidth(context);
        params.height = getScreenHeight(context);
        params.y = getMarginTop(context);
        proDialog.getWindow().setAttributes(params);
        return proDialog;
    }

    /**
     * 根据自定义的高（dp）获取px单位的高
     *公式：density=densityDpi/160  ,px=dp*density
     */
    private static int getScreenHeight (Context context){
        final float density = context.getResources().getDisplayMetrics().density;
        return ((int) (mProDialogHeight * density + 0.5f));
    }

    private static int getScreenWidth(Context context){
        final float density = context.getResources().getDisplayMetrics().density;
        return ((int) (mProDialogWidth * density + 0.5f));
    }

    private static int getMarginTop(Context context){
        final float density = context.getResources().getDisplayMetrics().density;
        return ((int) (mMarginTop * density + 0.5f));
    }
}
