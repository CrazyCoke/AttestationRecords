package com.gdctwh.attestationrecords.fragment.main;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.acitvity.art.ArtVRActivity;
import com.gdctwh.attestationrecords.adapter.ArtRecyclerViewAadapter;
import com.gdctwh.attestationrecords.api.RecycleViewItemClickListener;
import com.gdctwh.attestationrecords.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class ArtFragment extends Fragment {


    private View mView;
    private RecyclerView mArtRecyclerView;
    private List<String> mData = new ArrayList<>();
    private ArtRecyclerViewAadapter mAadapter;
    private static final float MIN_SCALE = 0.75f;
    private List<String> mHeaderData;


    private int mIndex; //正确的数据索引
    private int start_position = 500;//中间页
    private int max_count = 1000;//最大的页面个数


    public ArtFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 创建根布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_art, container, false);
        mArtRecyclerView = mView.findViewById(R.id.art_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mArtRecyclerView.setLayoutManager(linearLayoutManager);
        initData();

        mAadapter = new ArtRecyclerViewAadapter(mData, new RecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (postion == 0){
                    return;
                }
                Intent intent = new Intent(getContext(),ArtVRActivity.class);
                intent.putExtra("position",postion);
                startActivity(intent);
            }
        });
        mArtRecyclerView.setAdapter(mAadapter);

        setHeaderView(mArtRecyclerView);
        setFooterView(mArtRecyclerView);
        return mView;
    }

    /**
     * 创建item 和头布局，底布局
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    /**
     * 加载数据
     */
    private void initData() {
        mData.clear();
        for (int i = 0; i < 20; i++) {
            mData.add("测试数据：" + i);
        }
    }

    /**
     * 设置头布局
     * @param view
     */
    private void setHeaderView(RecyclerView view){
        View header = LayoutInflater.from(getContext()).inflate(R.layout.header_art_layout,view,false);
        mHeaderData = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mHeaderData.add("" + i);
        }
        PagerContainer pagerContainer = header.findViewById(R.id.pager_container);
        ViewPager pager = pagerContainer.getViewPager();
        pager.setAdapter(new MyPagerAdapter());
        pager.setClipChildren(false);
        pager.setOffscreenPageLimit(15);
        pager.setCurrentItem(start_position);
        pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                //执行一些page的动画
                if (position < -1) {//看不到的一页 *
                    page.setScaleX(0.75f);
                    page.setScaleY(0.75f);
                } else if (position <= 1) {
                    if (position < 0) {//滑出的页 0.0 ~ -1 *
                        float scaleFactor = (1 - MIN_SCALE) * (0 - position);
                        page.setScaleX(1 - scaleFactor);
                        page.setScaleY(1 - scaleFactor);
                    } else {//滑进的页 1 ~ 0.0 *
                        float scaleFactor = (1 - MIN_SCALE) * (1 - position);
                        page.setScaleX(MIN_SCALE + scaleFactor);
                        page.setScaleY(MIN_SCALE + scaleFactor);
                    }
                } else {//看不到的另一页 *
                    page.setScaleX(0.75f);
                    page.setScaleY(0.75f);
                }
            }
        });
        pagerContainer.onPageSelected(3);

        pagerContainer.setPageItemClickListener(new PageItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showShortToast(getContext(),"position:" + position);

            }
        });

        boolean showTransformer = getActivity().getIntent().getBooleanExtra("showTransformer", false);
        if (showTransformer){
            new CoverFlow.Builder()
                    .with(pager)
                    .scale(0.3f)
                    .pagerMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin))
                    .spaceSize(0f)
                    .rotationY(25f)
                    .build();
        }else {
            pager.setPageMargin(0);
        }


        addPointDot(header, mHeaderData,pager);

        mAadapter.setHeaderView(header);
    }

    /**
     * 动态添加指示点
     */
    private void addPointDot(View header,List<String> headerData,ViewPager pager) {
        final List<View> dotList = new ArrayList<>();
        LinearLayout llDot = header.findViewById(R.id.ll_dot);
        for (int i = 0; i < headerData.size() ; i++) {
            View dot = getLayoutInflater().inflate(R.layout.point_dot_view,llDot,false);
            llDot.addView(dot);
            dotList.add(dot);
        }
        dotList.get(Math.abs(max_count - start_position) % mHeaderData.size()).setSelected(true);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mIndex = Math.abs(position - start_position) % mHeaderData.size();
                for (int i = 0; i < dotList.size(); i++) {
                    if (i == mIndex){
                        dotList.get(i).setSelected(true);
                    }else {
                        dotList.get(i).setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置底布局
     * @param view
     */
    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.footer_list_layout,view,false);
        mAadapter.setFooterView(footer);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 顶部ViewPager的适配器
     */
    class MyPagerAdapter extends PagerAdapter {



        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_art_header_vp_layout,null);
           // view.setBackgroundColor(Color.argb(255, position * 50, position * 10, position * 50));
            ImageView img1 = view.findViewById(R.id.img1);
            ImageView img2 = view.findViewById(R.id.img2);
            RelativeLayout layout = view.findViewById(R.id.rlt_layout);
            mIndex = Math.abs(position - start_position) % mHeaderData.size();
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showShortToast(getContext(),"" + mIndex);
                    Intent intent = new Intent(getContext(), ArtVRActivity.class);
                    startActivity(intent);
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            if (mHeaderData == null ){
                return 0;
            }
            return max_count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
