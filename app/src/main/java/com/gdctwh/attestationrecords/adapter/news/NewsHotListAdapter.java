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
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */

public class NewsHotListAdapter extends BaseHeaderFooterRVAdapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NORMAL = 2;
    private List<NewsBean.ArticlesBean> mData;
    private Context mContext;

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
        notifyItemInserted(0);
    }

    private View mHeaderView;

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    private View mFooterView;

    public NewsHotListAdapter(List<NewsBean.ArticlesBean> data, RecycleViewItemClickListener listener){

        mData = data;
        mClickListener = listener;
    }


    /**
     * 根据不同的position绑定不同的view
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == 0){
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1){
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
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
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_news_layout,parent,false);
        return new ListHolder(layout,mClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ( getItemViewType(position) == TYPE_NORMAL ){
            if (holder instanceof ListHolder){
                if (mHeaderView == null){
                    ((ListHolder) holder).tvTitle.setText(mData.get(position ).getName());
                    ((ListHolder) holder).tvContent.setText(Html.fromHtml(mData.get(position ).getPreview()));
                    Glide.with(mContext).load(mData.get(position).getOriginal())
                            .error(R.mipmap.img_sample_hot).into(((ListHolder) holder).img);
                }else {
                    //这个界面有头布局 position - 1.
                    ((ListHolder) holder).tvTitle.setText(mData.get(position - 1).getName());
                    ((ListHolder) holder).tvContent.setText(Html.fromHtml(mData.get(position - 1).getPreview()));
                    Glide.with(mContext).load(mData.get(position - 1).getOriginal())
                            .error(R.mipmap.img_sample_hot).into(((ListHolder) holder).img);
                }
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else {
            return;
        }
    }

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
