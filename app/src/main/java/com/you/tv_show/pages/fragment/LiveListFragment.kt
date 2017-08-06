package com.you.tv_show.pages.fragment

import android.os.Bundle
import com.you.tv_show.bean.LiveInfo
import com.you.tv_show.pages.base.BaseFragment
import com.you.tv_show.pages.iview.ILiveListView
import com.you.tv_show.pages.presenter.LiveListPresenter

/**
 * Created by Administrator on 2017/8/6.
 */
class LiveListFragment: BaseFragment<ILiveListView, LiveListPresenter>(), ILiveListView
{
    private var slug: String? = null

    private var isSearch: Boolean = false

    companion object
    {

        fun newInstance(slug: String): LiveListFragment
        {
            return newInstance(slug, false)
        }

        fun newInstance(slug: String, isSearch: Boolean): LiveListFragment
        {
            val args = Bundle()

            val fragment = LiveListFragment()
            fragment.slug = slug
            fragment.isSearch = isSearch
            fragment.arguments = args

            return fragment
        }
    }

    override fun showProgress()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCompleted()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetLiveList(list: List<LiveInfo>)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(e: Throwable?)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetMoreLiveList(list: List<LiveInfo>)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createPresenter(): LiveListPresenter
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRootViewId(): Int
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initUI()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}