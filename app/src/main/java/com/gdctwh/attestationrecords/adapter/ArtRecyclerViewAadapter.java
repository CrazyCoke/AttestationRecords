package com.gdctwh.attestationrecords.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.api.RecycleViewItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */

public class ArtRecyclerViewAadapter extends RecyclerView.Adapter {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_NORMAL = 2;

    private List<String> mData;
    private RecycleViewItemClickListener mListener;


    private View mHeaderView;
    private View mFooterView;

    public ArtRecyclerViewAadapter( List<String> data,RecycleViewItemClickListener listener){

        mData = data;
        mListener = listener;
    }


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
        if (position == 0){
            //第一个 加载HeaderView
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1){
            //最后一个，加载FooterView
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    /**
     * 创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER ){
            return new ListHolder(mHeaderView,null);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER){
            return  new ListHolder(mFooterView,null);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_art_list_layout,parent,false);
        return new ListHolder(layout,mListener);
    }

    /**
     * 绑定View，根据返回的position的类型，从而进行绑定HeadView和FooterView的区分
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if (holder instanceof ListHolder){
                //这里加载数据的时候，是从position-1开始，因为position==0已经被HeaderView占用了。
                ((ListHolder) holder).tv.setText(mData.get(position-1));
                return;
            }
            return;
        }else if (getItemViewType(position) == TYPE_HEADER){
            return;
        }else {
            return;
        }
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

    class ListHolder extends BaseHeaderFooterRVAdapter.BaseViewHolder{

        TextView tv;
        public ListHolder(View itemView, RecycleViewItemClickListener listener) {
            super(itemView,listener);
            //如果是HeaderView或者是FooterView，直接返回
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
            tv = itemView.findViewById(R.id.tv_art_item);
        }
    }
}
