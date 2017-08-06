package com.you.tv_show.pages.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.OnClick
import com.you.tv_show.Constants
import com.you.tv_show.R
import com.you.tv_show.bean.LiveCategory
import com.you.tv_show.pages.adapter.ViewPagerFragmentAdapter
import com.you.tv_show.pages.base.BaseFragment
import com.you.tv_show.pages.iview.ICategoryView
import com.you.tv_show.pages.presenter.CategoryPresenter
import java.util.*

/**
 * Created by Administrator on 2017/7/2.
 */
public class HomeFragment : BaseFragment<ICategoryView, CategoryPresenter>(), ICategoryView
{

    private var listCategory: MutableList<LiveCategory> = ArrayList()
    private var listTitle: MutableList<CharSequence> = ArrayList()
    private var listData: MutableList<Fragment> = ArrayList()

    private var viewPagerFragmentAdapter: ViewPagerFragmentAdapter? = null

    @BindView(R.id.ivLeft)
    var ivLeft: ImageView? = null

    @BindView(R.id.ivTitle)
    var ivTitle: ImageView? = null

    @BindView(R.id.ivRight)
    var ivRight: ImageView? = null

    @BindView(R.id.tabLayout)
    var tabLayout: TabLayout? = null

    @BindView(R.id.btnMore)
    var btnMore: ImageView? = null

    @BindView(R.id.viewPager)
    var viewPager: ViewPager? = null

    @BindView(R.id.fab)
    var fab: FloatingActionButton? = null

    fun newInstance(): HomeFragment
    {
        val args = Bundle()

        val fragment = HomeFragment()
        fragment.arguments = args
        return fragment
    }

    override fun getRootViewId(): Int
    {
        return R.layout.fragment_home
    }

    override fun createPresenter(): CategoryPresenter
    {
        return CategoryPresenter(getApp())
    }

    override fun initData()
    {
        getPresenter().getAllCategories()
    }

    override fun initUI()
    {
        viewPagerFragmentAdapter = ViewPagerFragmentAdapter(childFragmentManager, listData, listTitle)
        viewPager!!.adapter = viewPagerFragmentAdapter

        tabLayout!!.setupWithViewPager(viewPager)
    }

    override fun showProgress()
    {
    }

    override fun onCompleted()
    {
    }

    override fun onGetLiveCategory(list: List<LiveCategory>)
    {
        if (list != null)
        {
            toSetList(listCategory, list, false)
            listData.clear()
            val listTemp = ArrayList<CharSequence>()
            //----------------------
            listTemp.add(getText(R.string.recommend))
            listData.add(RecommendFragment.newInstance())
            listTemp.add(getText(R.string.tab_all))
            listData.add(LiveListFragment.newInstance(null))
            //----------------------
            for (i in list.indices)
            {
                val liveCategory = list[i]

                listTemp.add(liveCategory.name)
                listData.add(LiveListFragment.newInstance(liveCategory.slug))
            }

            toSetList(listTitle, listTemp, false)
        }

        if (viewPagerFragmentAdapter != null)
        {
            viewPagerFragmentAdapter!!.setListTitle(listTitle)
            viewPagerFragmentAdapter!!.setListData(listData)
            viewPagerFragmentAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onError(e: Throwable?)
    {
    }

    @OnClick(R.id.ivLeft, R.id.ivRight, R.id.btnMore, R.id.fab)
    fun onClick(view: View)
    {
        when (view.id)
        {
            R.id.ivLeft -> startActivity(getFragmentIntent(Constants.SEARCH_FRAGMENT))
            R.id.ivRight -> startLogin()
            R.id.btnMore ->
            {
            }
            R.id.fab -> startAbout()
        }
    }

}