package com.you.tv_show.pages.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */

public abstract class HolderRecyclerAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H>{

    protected Context context;

    private List<T> listData;

    private LayoutInflater layoutInflater;

    private OnItemClicklistener mOnItemClicklistener;

    public interface OnItemClicklistener{
        public void onItemClick(View v, int position);
    }

    public void setOnItemClicklistener(OnItemClicklistener onItemClicklistener){
        this.mOnItemClicklistener = onItemClicklistener;
    }

    public HolderRecyclerAdapter(Context context, List<T> listData){
        super();
        this.context = context;
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = buildConvertView(layoutInflater,viewType);
        return buildHolder(itemView,viewType);
    }

    @Override
    public void onBindViewHolder(H holder, final int position) {
        T t = position<listData.size() ? listData.get(position) : null;
        bindViewDatas(holder,t,position);
        if(this.mOnItemClicklistener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listData==null ? 0:listData.size();
    }

    public View inflate(int layoutId){
        return layoutInflater.inflate(layoutId, null);
    }

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }

    /**
     * 建立convertView
     * @param layoutInflater
     * @param viewType
     * @return
     */
    public abstract View buildConvertView(LayoutInflater layoutInflater,int viewType);

    /**
     * 建立视图Holder
     * @param convertView
     * @param viewType
     * @return
     */
    public abstract H buildHolder(View convertView,int viewType);

    /**
     * 绑定数据
     * @param holder
     * @param t
     * @param position
     */
    public abstract void bindViewDatas(H holder,T t,int position);
}
