package com.you.tv_show.widget.convenientBanner

import android.content.Context
import android.view.View

/**
 * Created by Administrator on 2017/7/11.
 */
interface Holder<T>
{
    fun createView(context: Context): View
    fun UpdateUI(context: Context, position: Int, data: T)
}