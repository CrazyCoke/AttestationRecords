package com.gdctwh.attestationrecords.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdctwh.attestationrecords.bean.CommentAndtCollenctBean;
import com.gdctwh.attestationrecords.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9.
 */

public class CommentAdapter extends BaseHeaderFooterRVAdapter<CommentAndtCollenctBean.ItemsBean> {

    public CommentAdapter(List<CommentAndtCollenctBean.ItemsBean> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ListHolder(mFooterView);
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_layout, parent, false);

        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (mHeaderView == null) {
                ((ListHolder) holder).tvName.setText(mData.get(position).getUsername());
                ((ListHolder) holder).tvTime.setText(mData.get(position).getTime());
                ((ListHolder) holder).tvComment.setText(mData.get(position).getContent());
            } else {
                ((ListHolder) holder).tvName.setText(mData.get(position - 1).getUsername());
                ((ListHolder) holder).tvTime.setText(mData.get(position - 1).getTime());
                ((ListHolder) holder).tvComment.setText(mData.get(position - 1).getContent());
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }
    }

    class ListHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvTime;
        public TextView tvComment;
        public RoundedImageView imgHead;

        public ListHolder(View itemView) {
            super(itemView);

            if (itemView == mFooterView) {
                return;
            }
            if (itemView == mHeaderView) {
                return;
            }
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvComment = itemView.findViewById(R.id.tv_comment);
            imgHead = itemView.findViewById(R.id.img_head);
        }

    }
}
