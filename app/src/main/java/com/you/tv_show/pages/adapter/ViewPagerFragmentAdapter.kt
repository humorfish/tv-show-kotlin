package com.you.tv_show.pages.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Administrator on 2017/7/3.
 */
class ViewPagerFragmentAdapter : FragmentPagerAdapter
{
    private var listData: List<Fragment>? = null

    private var listTitle: List<CharSequence>? = null

    constructor(fm: FragmentManager, listData: List<Fragment>) : this(fm, listData, null)
    {}

    constructor(fm: FragmentManager, listData: List<Fragment>, listTitle: List<CharSequence>?): super(fm)
    {
        this.listData = listData
        this.listTitle = listTitle
    }

    fun getListData(): List<Fragment>
    {
        return listData!!
    }

    fun setListData(listData: List<Fragment>)
    {
        this.listData = listData
    }

    fun getListTitle(): List<CharSequence>
    {
        return listTitle!!
    }

    fun setListTitle(listTitle: List<CharSequence>)
    {
        this.listTitle = listTitle
    }

    override fun getItem(p0: Int): Fragment?
    {
        return if (listData == null) null else listData!![p0]
    }

    override fun getCount(): Int
    {
        return if (listData == null) 0 else listData!!.size
    }

    override fun getPageTitle(position: Int): CharSequence
    {
        if (listTitle != null && listTitle!!.isNotEmpty())
        {
            return listTitle!!.get(position)
        }
        return super.getPageTitle(position)
    }

}