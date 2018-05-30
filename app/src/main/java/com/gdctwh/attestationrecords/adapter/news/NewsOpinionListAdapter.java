package com.gdctwh.attestationrecords.adapter.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.adapter.BaseHeaderFooterRVAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class NewsOpinionListAdapter extends BaseHeaderFooterRVAdapter<String> {

    public NewsOpinionListAdapter(List<String> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER){
            return new ListHolder(mHeaderView);
        }

        if (mFooterView != null && viewType == TYPE_FOOTER){
            return new ListHolder(mFooterView);
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_opinion_layout,parent,false);
        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL){
            if (mHeaderView != null) {
                ((ListHolder) holder).tvName.setText(mData.get(position - 1));
            }else {
                ((ListHolder) holder).tvName.setText(mData.get(position));
            }
        }else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }else {
            return;
        }
    }

    class ListHolder extends RecyclerView.ViewHolder{

        private ImageView headImg;
        private TextView tvName;
        private TextView tvAbout;
        private TextView tvContent;

        public ListHolder(View itemView) {
            super(itemView);

            if (itemView == mHeaderView  || itemView == mFooterView ){
                return;
            }
            headImg = itemView.findViewById(R.id.img_head);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAbout = itemView.findViewById(R.id.tv_about);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
