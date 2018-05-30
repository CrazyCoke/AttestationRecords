package com.gdctwh.attestationrecords.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gdctwh.attestationrecords.api.RecycleViewItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 * 添加了头布局和底部布局的RecyclerView 的基类。
 */

public abstract class BaseHeaderFooterRVAdapter<T> extends RecyclerView.Adapter {

    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_FOOTER = 1;
    protected static final int TYPE_NORMAL = 2;

    private static final String TAG = "BaseHeaderFooterRVAdapt";

    protected View mHeaderView;
    protected View mFooterView;
    protected List<T> mData;
    protected RecycleViewItemClickListener mClickListener;


    /**
     * Headerview 和 FooterView的get ，set函数
     * @param headerView
     */
    public void setHeaderView(View headerView){
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getmHeaderView() {
        return mHeaderView;
    }


    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }


    public View getmFooterView() {
        return mFooterView;
    }

    public void setOnItemClickListener(RecycleViewItemClickListener listener) {
        this.mClickListener = listener;
    }

    /**
     * 重写此方法，是加入Header和Footer的关键，我们通过判断item的类型来绑定不同的View。
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (mHeaderView != null) {
            if (position == 0) {
                //第一个 加载HeaderView
                return TYPE_HEADER;
            }
        }
        if (mFooterView != null) {
            if (position == getItemCount() - 1) {
                //最后一个，加载FooterView
                return TYPE_FOOTER;
            }
        }
        return TYPE_NORMAL;
    }

    //返回View中item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView的个数。
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null){
            return mData.size();
        }else if (mHeaderView == null && mFooterView != null){
            return mData.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return mData.size() + 1;
        }else {
            return mData.size() + 2;
        }
    }

    public boolean isHeaderView(int position){
        if (mHeaderView != null && position == 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean isFooterView(int position){
        if (mHeaderView != null) {
            if (mFooterView != null && position == mData.size() + 1) {
                return true;
            }else {
                return false;
            }
        }else {
            if (mFooterView != null && position == mData.size()){
                return true;
            }else {
                return false;
            }
        }
    }

     public static abstract class  BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         private RecycleViewItemClickListener mListener;// 声明自定义的接口
         public BaseViewHolder(View itemView,RecycleViewItemClickListener listener) {
             super(itemView);
             mListener = listener;
             itemView.setOnClickListener(this);
         }

         @Override
         public void onClick(View view) {
             // getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
             if (mListener == null){
                 return;
             }

             mListener.onItemClick(view,getPosition());
         }
     }

}
