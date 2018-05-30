package com.gdctwh.attestationrecords.acitvity.base;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;

import com.gdctwh.attestationrecords.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/17.
 */

public abstract class BaseActionBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);

        //这行代码是用于获取android源生的资源文件
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        initActionBar();

        initView();
        initEvent();
        initData();
    }

    protected void initData() {

    }

    protected void initEvent() {

    }

    protected void initView() {

    }

    /**
     * 返回activity的layout
     * @return
     */
    protected abstract int getContentView();

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //富文本 设置 bar title的字体颜色
        SpannableString spannableString = new SpannableString(getString(getBarTitle()));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#000000"));
        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(spannableString);

        //设置bar 的背景颜色
        int color = Color.parseColor("#ffffff");
        ColorDrawable drawable = new ColorDrawable(color);
        actionBar.setBackgroundDrawable(drawable);

        //设置返回按钮的颜色
        Drawable upArrow =  ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
    }

    /**
     * 返回actionbar 的title
     * @return
     */
    protected abstract int getBarTitle();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
