package com.gdctwh.attestationrecords.acitvity;

import android.os.Bundle;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdctwh.attestationrecords.factory.MainFragmentFactory;

import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * FragmentLayout分块
     */
    @BindView(R.id.frame_layout_main)
    FrameLayout frameLayoutMain;

    /**
     * “资讯”图标
     */
    @BindView(R.id.img_main_news)
    ImageView imgMainNews;

    /**
     * “资讯”文字
     */
    @BindView(R.id.tv_main_news)
    TextView tvMainNews;

    /**
     * “资讯”整体按钮
     */
    @BindView(R.id.lly_mian_bottom_news)
    LinearLayout llyMianBottomNews;

    /**
     * “我要鉴证”图标
     */
    @BindView(R.id.img_main_authenticate)
    ImageView imgMainAuthenticate;

    /**
     * “我要鉴证”文字
     */
    @BindView(R.id.tv_main_authenticate)
    TextView tvMainAuthenticate;

    /**
     * “我要鉴证”整体按钮
     */
    @BindView(R.id.lly_main_bottom_authenticate)
    LinearLayout llyMainBottomAuthenticate;

    /**
     * “艺术空间”图标
     */
    @BindView(R.id.img_main_art)
    ImageView imgMainArt;

    /**
     * “艺术空间”文字
     */
    @BindView(R.id.tv_main_art)
    TextView tvMainArt;

    /**
     * “艺术空间”按钮
     */
    @BindView(R.id.lly_main_bottom_art)
    LinearLayout llyMainBottomArt;

    /**
     * “个人中心”图标
     */
    @BindView(R.id.img_main_mine)
    ImageView imgMainMine;

    /**
     * “个人中心”文字
     */
    @BindView(R.id.tv_main_mine)
    TextView tvMainMine;

    /**
     * “个人中心”按钮
     */
    @BindView(R.id.lly_main_bottom_mine)
    LinearLayout llyMainBottomMine;
    private FragmentManager mFragmentManager;

    /**
     * 用于存放底部的textView   改变颜色使用
     */
    private List<TextView> mTextViewList = new ArrayList<>();

    /**
     * 用于存放底部的icon， 根据状态的不同切换图片
     */
    private List<ImageView> mImageViewList = new ArrayList<>();
    private Fragment artFragment;
    private Fragment newsFragment;
    private Fragment mineFragment;
    private Fragment authenticateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initEvent();
        initFragment();
    }



    //控件
    private void initView() {


        //TextView控件，加入集合中，根据状态的不同改变文字颜色
        mTextViewList.add(tvMainNews);
        mTextViewList.add(tvMainAuthenticate);
        mTextViewList.add(tvMainArt);
        mTextViewList.add(tvMainMine);

        //ImageView,用于改变状态
        mImageViewList.add(imgMainNews);
        mImageViewList.add(imgMainAuthenticate);
        mImageViewList.add(imgMainArt);
        mImageViewList.add(imgMainMine);

        changeState(R.id.tv_main_news,R.id.img_main_news);


        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        MainFragmentFactory factory = MainFragmentFactory.getmInstance();
        artFragment = factory.getMainFragment(IConstants.FRAGMENTTAG.FRAGMENT_ART);
        newsFragment = factory.getMainFragment(IConstants.FRAGMENTTAG.FRAGMENT_NEWS);
        mineFragment = factory.getMainFragment(IConstants.FRAGMENTTAG.FRAGMENT_MINE);
        authenticateFragment = factory.getMainFragment(IConstants.FRAGMENTTAG.FRAGMENT_AUTHENTICATE);

        transaction.add(R.id.frame_layout_main, newsFragment,IConstants.FRAGMENTTAG.FRAGMENT_NEWS);
        transaction.add(R.id.frame_layout_main, authenticateFragment,IConstants.FRAGMENTTAG.FRAGMENT_AUTHENTICATE);
        transaction.add(R.id.frame_layout_main, artFragment,IConstants.FRAGMENTTAG.FRAGMENT_ART);
        transaction.add(R.id.frame_layout_main, mineFragment,IConstants.FRAGMENTTAG.FRAGMENT_MINE);

        transaction.hide(authenticateFragment).hide(artFragment).hide(mineFragment);
        transaction.show(newsFragment);
        transaction.commit();
    }


    /**
     * 用于改变底部导航栏的状态
     * @param tvID  要改变状态的TextView的id
     * @param imgID  要改变状态的ImageView的id
     */
    private void changeState(int tvID,int imgID) {
        for (int i = 0; i < mTextViewList.size(); i++) {
            TextView textView = mTextViewList.get(i);
            ImageView imageView = mImageViewList.get(i);
            if (textView.getId() == tvID){
                textView.setSelected(true);
            }else {
                textView.setSelected(false);
            }

            if (imageView.getId() == imgID){
                imageView.setSelected(true);
            }else {
                imageView.setSelected(false);
            }
        }
    }

    //事件
    private void initEvent() {
        llyMianBottomNews.setOnClickListener(this);
        llyMainBottomAuthenticate.setOnClickListener(this);
        llyMainBottomArt.setOnClickListener(this);
        llyMainBottomMine.setOnClickListener(this);
    }

    //板块
    private void initFragment() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lly_mian_bottom_news:
                changeState(R.id.tv_main_news,R.id.img_main_news);
                getSupportFragmentManager().beginTransaction().show(newsFragment).hide(mineFragment)
                        .hide(authenticateFragment).hide(artFragment).commit();
                break;

            case R.id.lly_main_bottom_authenticate:
                changeState(R.id.tv_main_authenticate,R.id.img_main_authenticate);
                getSupportFragmentManager().beginTransaction().show(authenticateFragment).hide(mineFragment)
                        .hide(artFragment).hide(newsFragment).commit();
                break;

            case R.id.lly_main_bottom_art:
                changeState(R.id.tv_main_art,R.id.img_main_art);
                getSupportFragmentManager().beginTransaction().show(artFragment).hide(mineFragment)
                        .hide(authenticateFragment).hide(newsFragment).commit();

                break;
            case R.id.lly_main_bottom_mine:
                changeState(R.id.tv_main_mine,R.id.img_main_mine);
               getSupportFragmentManager().beginTransaction().show(mineFragment).hide(artFragment)
                       .hide(authenticateFragment).hide(newsFragment).commit();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
