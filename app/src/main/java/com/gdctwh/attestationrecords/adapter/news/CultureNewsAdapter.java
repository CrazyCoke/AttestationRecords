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
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class CultureNewsAdapter extends BaseHeaderFooterRVAdapter<NewsBean.ArticlesBean> {

    private Context mContext;

    public CultureNewsAdapter (List<NewsBean.ArticlesBean> data, RecycleViewItemClickListener listener){
        mData = data;
        mClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER){
           return new ListHolder(mHeaderView);
        }

        if (mFooterView != null && viewType == TYPE_FOOTER){
            return new ListHolder(mFooterView);
        }
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_news_layout,parent,false);
        return new ListHolder(itemView,mClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL){
            if (mHeaderView != null) {
                ((ListHolder) holder).tvTitle.setText(mData.get(position - 1).getName());
                ((ListHolder) holder).tvContent.setText(Html.fromHtml(mData.get(position - 1).getPreview()));
                Glide.with(mContext).load(mData.get(position - 1).getOriginal()).into(((ListHolder) holder).img);
            }else {
                ((ListHolder) holder).tvTitle.setText(mData.get(position).getName());
                ((ListHolder) holder).tvContent.setText(Html.fromHtml(mData.get(position).getPreview()));
                Glide.with(mContext).load(mData.get(position).getOriginal()).into(((ListHolder) holder).img);
            }
        }else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }else {
            return;
        }
    }

    class ListHolder extends BaseHeaderFooterRVAdapter.BaseViewHolder {

        RoundedImageView img;
        TextView tvTitle;
        TextView tvContent;

        public ListHolder(View itemView){
            super(itemView,null);
            if (itemView == mHeaderView ){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
            initView(itemView);
        }

        public ListHolder(View itemView, RecycleViewItemClickListener listener) {
            super(itemView,listener);

            if (itemView == mHeaderView ){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
            initView(itemView);
        }

        private void initView(View itemView) {
            img = itemView.findViewById(R.id.img_hot);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }

    }
}
