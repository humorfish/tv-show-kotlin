package com.you.tv_show.pages.iview

import com.you.tv_show.bean.LiveCategory
import com.you.tv_show.pages.base.BaseView

/**
 * Created by Administrator on 2017/6/27.
 */
interface ICategoryView : BaseView
{

    fun onGetLiveCategory(list: List<LiveCategory>)

}