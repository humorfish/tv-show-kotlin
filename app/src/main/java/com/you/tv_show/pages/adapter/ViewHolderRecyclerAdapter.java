package com.you.tv_show.pages.adapter;

import android.content.Context;
import android.view.View;

import com.you.tv_show.pages.adapter.viewholder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */

public abstract class ViewHolderRecyclerAdapter<T> extends HolderRecyclerAdapter<T, ViewHolder> {

    public ViewHolderRecyclerAdapter(Context context, List<T> listData) {
        super(context, listData);
    }

    @Override
    public ViewHolder buildHolder(View convertView, int viewType) {
        return new ViewHolder(convertView);
    }

}