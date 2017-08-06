package com.you.tv_show.pages.iview

import com.you.tv_show.bean.LiveInfo
import com.you.tv_show.pages.base.BaseView

/**
 * Created by Administrator on 2017/6/28.
 */
interface ILiveListView : BaseView
{
    fun onGetLiveList(list: List<LiveInfo>)
    fun onGetMoreLiveList(list: List<LiveInfo>)
}