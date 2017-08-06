package com.you.tv_show.widget.convenientBanner

import android.support.v4.view.ViewPager
import android.widget.ImageView
import java.util.ArrayList

/**
 * Created by Administrator on 2017/7/11.
 */
class CBPageChangeListener(private val pointViews: ArrayList<ImageView>, private val page_indicatorId: IntArray) : ViewPager.OnPageChangeListener
{
    private var onPageChangeListener: ViewPager.OnPageChangeListener? = null
    override fun onPageScrollStateChanged(state: Int)
    {
        if (onPageChangeListener != null) onPageChangeListener!!.onPageScrollStateChanged(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    {
        if (onPageChangeListener != null) onPageChangeListener!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(index: Int)
    {
        for (i in pointViews.indices)
        {
            pointViews[index].setImageResource(page_indicatorId[1])
            if (index != i)
            {
                pointViews[i].setImageResource(page_indicatorId[0])
            }
        }
        if (onPageChangeListener != null) onPageChangeListener!!.onPageSelected(index)

    }

    fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener)
    {
        this.onPageChangeListener = onPageChangeListener
    }
}