package com.gdctwh.attestationrecords.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdctwh.attestationrecords.adapter.BaseHeaderFooterRVAdapter;
import com.gdctwh.attestationrecords.api.RecycleViewItemClickListener;
import com.gdctwh.attestationrecords.bean.NewsBean;
import com.gdctwh.attestationrecords.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class NewsAuctionAdapter extends BaseHeaderFooterRVAdapter<NewsBean.ArticlesBean> {

    private Context mContext;

    public NewsAuctionAdapter(List<NewsBean.ArticlesBean> data, RecycleViewItemClickListener listener){
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
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_news_auction_layout,parent,false);
        ListHolder holder = new ListHolder(itemView,mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ( getItemViewType(position) == TYPE_NORMAL){
            if (holder instanceof ListHolder){
                if (mHeaderView == null) {
                    ((ListHolder) holder).tvName.setText(mData.get( position ).getName());
                    ((ListHolder) holder).tvAbout.setText(Html.fromHtml(mData.get( position ).getPreview()));
                    ((ListHolder) holder).tvRanking.setText(mData.get( position ).getViewed());
                    Glide.with(mContext).load(mData.get(position).getOriginal()).into(((ListHolder) holder).img);

                }else {
                    ((ListHolder) holder).tvName.setText(mData.get( position -1 ).getName());
                }
            }
        }
    }
    class ListHolder extends BaseHeaderFooterRVAdapter.BaseViewHolder {

        ImageView img ;
        TextView tvName;
        TextView tvRanking;
        TextView tvAbout;
        TextView tvCreateTime;
        public ListHolder(View itemView,RecycleViewItemClickListener listener) {
            super(itemView,listener);
            if (itemView == mHeaderView  || itemView == mFooterView ){
                return;
            }
            img = itemView.findViewById(R.id.img_auction);
            tvName = itemView.findViewById(R.id.tv_name);
            tvRanking = itemView.findViewById(R.id.tv_ranking);
            tvAbout = itemView.findViewById(R.id.tv_about);
            tvCreateTime = itemView.findViewById(R.id.tv_create_time);
        }
    }
}
