package com.gdctwh.attestationrecords.acitvity.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.utils.ToastUtils;
import com.gdctwh.attestationrecords.widget.FlowGroupView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.flow_view)
    FlowGroupView flowView;
    @BindView(R.id.lly_hot_search)
    LinearLayout llyHotSearch;
    private SearchView mSearchView;

    private static final String TAG = "SearchActivity";
    private SearchView.SearchAutoComplete mSearchAutoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        initView();
        initEvent();
        initData();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initEvent() {

    }

    private void initData() {
        List<String> mData = new ArrayList<>();
        mData.add("降龙十八掌");
        mData.add("黯然销魂掌");
        mData.add("七十二路空明拳");
        mData.add("小无相功");
        mData.add("拈花指");
        mData.add("打狗棍法");
        mData.add("蛤蟆功");
        mData.add("九阴白骨爪");
        mData.add("一招半式闯江湖");
        mData.add("醉拳");
        mData.add("龙蛇虎豹");
        mData.add("葵花宝典");
        mData.add("吸星大法");
        mData.add("如来神掌警示牌");

        //为布局添加内容
        for (int i = 0; i < mData.size(); i++) {
            addTextView(mData.get(i));
        }
    }

    /**
     * 动态添加布局
     *
     * @param s
     */
    private void addTextView(String s) {
        TextView child = new TextView(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        child.setLayoutParams(params);
        child.setBackgroundResource(R.drawable.shape_edite_text);
        child.setText(s);
        child.setTextColor(getResources().getColor(R.color.tv_color));
        child.setPadding(7,4,7,4);
        initChildEvens(child);
        flowView.addView(child);
    }

    /**
     * 添加child view 的点击监听
     * @param child
     */
    private void initChildEvens(final TextView child) {
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToastUtils.showShortToast(SearchActivity.this,child.getText().toString());
                mSearchAutoComplete.setText(child.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_view, menu);

        //找到searchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        initSearchView();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSearchView() {
        mSearchView.setIconified(false);//一开始处于展开状态
        mSearchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        mSearchView.setIconifiedByDefault(false);//默认为true在框内，设置false则在框外
        mSearchView.setSubmitButtonEnabled(true);//显示提交按钮
        mSearchView.setQueryHint("输入关键字查找");//设置默认无内容时的文字提示

        //修改输入框的颜色
        mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        mSearchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white));//设置提示文字颜色
        mSearchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));//设置内容文字颜色

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件
                ToastUtils.showShortToast(SearchActivity.this, "点击了提交按钮");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //当输入框内容改变的时候回调
                Log.i(TAG, "内容: " + newText);
                return true;
            }
        });
    }
}
