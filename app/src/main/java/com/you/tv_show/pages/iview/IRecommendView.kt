package com.you.tv_show.pages.iview

import com.you.tv_show.bean.Banner
import com.you.tv_show.bean.Recommend
import com.you.tv_show.pages.base.BaseView

/**
 * Created by Administrator on 2017/7/10.
 */
interface IRecommendView : BaseView
{
    abstract fun onGetRecommend(recommend: Recommend)

    abstract fun onGetRooms(list: List<Recommend.RoomBean>)

    abstract fun onGetBanner(list: List<Banner>)
}