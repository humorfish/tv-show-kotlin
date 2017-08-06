package com.you.tv_show.widget.convenientBanner

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.you.tv_show.R

/**
 * Created by Administrator on 2017/7/11.
 */
class CBPageAdapter<T>(protected var holderCreator: CBViewHolderCreator<T>, protected var mDatas: List<T>?) : PagerAdapter()
{
    //    private View.OnClickListener onItemClickListener;
    private var canLoop = true
    private var viewPager: CBLoopViewPager<T>? = null
    private val MULTIPLE_COUNT = 300

    fun toRealPosition(position: Int): Int
    {
        val realCount = realCount
        if (realCount == 0)
            return 0
        val realPosition = position % realCount
        return realPosition
    }

    override fun getCount(): Int
    {
        return if (canLoop) realCount * MULTIPLE_COUNT else realCount
    }

    val realCount: Int
        get() = if (mDatas == null) 0 else mDatas!!.size

    @RequiresApi(Build.VERSION_CODES.DONUT)
    override fun instantiateItem(container: ViewGroup, position: Int): Any
    {
        val realPosition = toRealPosition(position)

        val view = getView(realPosition, null, container)
        //        if(onItemClickListener != null) view.setOnClickListener(onItemClickListener);
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any)
    {
        val view = `object` as View
        container.removeView(view)
    }

    override fun finishUpdate(container: ViewGroup)
    {
        var position = viewPager!!.currentItem
        if (position == 0)
        {
            position = viewPager!!.fristItem
        }
        else if (position == count - 1)
        {
            position = viewPager!!.lastItem
        }
        try
        {
            viewPager!!.setCurrentItem(position, false)
        }
        catch (e: IllegalStateException)
        {
        }

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean
    {
        return view === `object`
    }

    fun setCanLoop(canLoop: Boolean)
    {
        this.canLoop = canLoop
    }

    fun setViewPager(viewPager: CBLoopViewPager<T>)
    {
        this.viewPager = viewPager
    }

    @RequiresApi(Build.VERSION_CODES.DONUT)
    fun getView(position: Int, view: View?, container: ViewGroup): View
    {
        var view = view
        var holder: Holder<T>? = null
        if (view == null)
        {
            holder = holderCreator.createHolder() as Holder<T>
            view = holder!!.createView(container.context)
            view!!.setTag(R.id.cb_item_tag, holder)
        }
        else
        {
            holder = view.getTag(R.id.cb_item_tag) as Holder<T>
        }
        if (mDatas != null && !mDatas!!.isEmpty())
            holder!!.UpdateUI(container.context, position, mDatas!![position])
        return view
    }

    //    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
    //        this.onItemClickListener = onItemClickListener;
    //    }
}