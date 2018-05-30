package com.gdctwh.attestationrecords.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdctwh.attestationrecords.api.RecycleViewItemClickListener;
import com.gdctwh.attestationrecords.bean.HotNewsBean;
import com.gdctwh.attestationrecords.bean.NewsBean;
import com.gdctwh.attestationrecords.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class AttestationNewsAdapter extends BaseHeaderFooterRVAdapter<NewsBean.ArticlesBean> {

    private Context mContext;

    public AttestationNewsAdapter(List<NewsBean.ArticlesBean> data,RecycleViewItemClickListener listener){
        mData = data;
        mClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER){
            return new ListHolder(mHeaderView,null);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER){
            return new ListHolder(mFooterView,null);
        }
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_news_authenticate_news_layout,parent,false);
        return new ListHolder(itemView,mClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            //这个页面没有头布局，如果添加头布局，需要加入判断 position - 1.
                ((ListHolder) holder).tvTitle.setText(mData.get(position).getName());
                ((ListHolder) holder).tvAbout.setText(Html.fromHtml(mData.get(position).getPreview()));
            Glide.with(mContext).load(mData.get(position).getOriginal()).into( ((ListHolder) holder).img);
        }else{
            return;
        }
    }
    class ListHolder extends BaseHeaderFooterRVAdapter.BaseViewHolder{

        ImageView img;
        TextView tvTitle;
        TextView tvAbout;
        public ListHolder(View itemView, RecycleViewItemClickListener listener) {
            super(itemView,listener);
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
            img = itemView.findViewById(R.id.img_authenticate_news);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAbout = itemView.findViewById(R.id.tv_about);
        }
    }

}
