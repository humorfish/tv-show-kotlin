package com.you.tv_show.widget.convenientBanner

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

/**
 * Created by Administrator on 2017/7/18.
 */
class ViewPagerScroller : Scroller
{
    var scrollDuration = 800// 滑动速度,值越大滑动越慢，滑动太快会使3d效果不明显
    var isZero: Boolean = false

    constructor(context: Context) : super(context)

    constructor(context: Context, interpolator: Interpolator) : super(context, interpolator)

    constructor(context: Context, interpolator: Interpolator,
                flywheel: Boolean) : super(context, interpolator, flywheel)

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int)
    {
        super.startScroll(startX, startY, dx, dy, if (isZero) 0 else scrollDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int)
    {
        super.startScroll(startX, startY, dx, dy, if (isZero) 0 else scrollDuration)
    }
}