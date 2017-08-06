package com.you.tv_show.widget.convenientBanner

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Administrator on 2017/7/11.
 */
class CBLoopViewPager<T> : ViewPager
{
    internal var mOuterPageChangeListener: ViewPager.OnPageChangeListener? = null
    private var onItemClickListener: OnItemClickListener? = null
    private var mAdapter: CBPageAdapter<T>? = null

    var isCanScroll = true
    var isCanLoop = true
        set(canLoop)
        {
            field = canLoop
            if (!canLoop)
            {
                setCurrentItem(realItem, false)
            }

            if (mAdapter == null) return
            mAdapter!!.setCanLoop(canLoop)
            mAdapter!!.notifyDataSetChanged()
        }

    fun setAdapter(adapter: PagerAdapter, canLoop: Boolean)
    {
        mAdapter = adapter as CBPageAdapter<T>
        mAdapter!!.setCanLoop(canLoop)
        mAdapter!!.setViewPager(this)
        super.setAdapter(mAdapter)

        setCurrentItem(fristItem, false)
    }

    val fristItem: Int
        get() = if (isCanLoop) mAdapter!!.realCount else 0

    val lastItem: Int
        get() = mAdapter!!.realCount - 1

    private var oldX = 0f
    private var newX = 0f

    override fun onTouchEvent(ev: MotionEvent): Boolean
    {
        if (isCanScroll)
        {
            if (onItemClickListener != null)
            {
                when (ev.action)
                {
                    MotionEvent.ACTION_DOWN -> oldX = ev.x

                    MotionEvent.ACTION_UP ->
                    {
                        newX = ev.x
                        if (Math.abs(oldX - newX) < sens)
                        {
                            onItemClickListener!!.onItemClick(realItem)
                        }
                        oldX = 0f
                        newX = 0f
                    }
                }
            }
            return super.onTouchEvent(ev)
        }
        else
            return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean
    {
        if (isCanScroll)
            return super.onInterceptTouchEvent(ev)
        else
            return false
    }

    override fun getAdapter(): CBPageAdapter<T>
    {
        return mAdapter!!
    }

    val realItem: Int
        get() = if (mAdapter != null) mAdapter!!.toRealPosition(super.getCurrentItem()) else 0

    override fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener)
    {
        mOuterPageChangeListener = listener
    }


    constructor(context: Context) : super(context)
    {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    {
        init()
    }

    private fun init()
    {
        super.setOnPageChangeListener(onPageChangeListener)
    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener
    {
        private var mPreviousPosition = -1f

        override fun onPageSelected(position: Int)
        {
            val realPosition = mAdapter!!.toRealPosition(position)
            if (mPreviousPosition != realPosition.toFloat())
            {
                mPreviousPosition = realPosition.toFloat()
                if (mOuterPageChangeListener != null)
                {
                    mOuterPageChangeListener!!.onPageSelected(realPosition)
                }
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float,
                                    positionOffsetPixels: Int)
        {
            val realPosition = position

            if (mOuterPageChangeListener != null)
            {
                if (realPosition != mAdapter!!.realCount - 1)
                {
                    mOuterPageChangeListener!!.onPageScrolled(realPosition,
                            positionOffset, positionOffsetPixels)
                }
                else
                {
                    if (positionOffset > .5)
                    {
                        mOuterPageChangeListener!!.onPageScrolled(0, 0f, 0)
                    }
                    else
                    {
                        mOuterPageChangeListener!!.onPageScrolled(realPosition,
                                0f, 0)
                    }
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int)
        {
            if (mOuterPageChangeListener != null)
            {
                mOuterPageChangeListener!!.onPageScrollStateChanged(state)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener)
    {
        this.onItemClickListener = onItemClickListener
    }

    companion object
    {
        private val sens = 5f
    }
}