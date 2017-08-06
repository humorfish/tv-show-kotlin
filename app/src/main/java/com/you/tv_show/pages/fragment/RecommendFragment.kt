package com.you.tv_show.pages.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.jude.easyrecyclerview.decoration.SpaceDecoration
import com.you.tv_show.R
import com.you.tv_show.bean.Banner
import com.you.tv_show.bean.Recommend
import com.you.tv_show.pages.adapter.RecommendAdapter
import com.you.tv_show.pages.base.BaseFragment
import com.you.tv_show.pages.iview.IRecommendView
import com.you.tv_show.pages.presenter.RecommendPresenter
import com.you.tv_show.util.LogUtils
import com.you.tv_show.util.SystemUtils
import com.you.tv_show.widget.convenientBanner.CBViewHolderCreator
import com.you.tv_show.widget.convenientBanner.ConvenientBanner
import com.you.tv_show.widget.convenientBanner.Holder
import com.you.tv_show.widget.convenientBanner.OnItemClickListener
import java.util.*

/**
 * Created by Administrator on 2017/7/10.
 */
class RecommendFragment : BaseFragment<IRecommendView, RecommendPresenter>(), IRecommendView
{
    private var convenientBanner: ConvenientBanner<Banner>? = null
    private var recommendAdapter: RecommendAdapter? = null

    @BindView(R.id.easyRecyclerView)
    var easyRecyclerView: EasyRecyclerView? = null

    private var tvTips: TextView? = null

    private var listData: ArrayList<Recommend.RoomBean>? = null

    private var listBanner: ArrayList<Banner>? = null

    override fun createPresenter(): RecommendPresenter
    {
        return RecommendPresenter(getApp())
    }

    override fun getRootViewId(): Int
    {
        return R.layout.fragment_recommend
    }

    override fun initUI()
    {
        tvTips = easyRecyclerView!!.findViewById(R.id.tvTips) as TextView
        val spaceDecoration = SpaceDecoration(0)
        easyRecyclerView!!.addItemDecoration(spaceDecoration)
        easyRecyclerView!!.setRefreshingColorResources(R.color.progress_color)
        listData = ArrayList<Recommend.RoomBean>()
        recommendAdapter = RecommendAdapter(context, listData)


        listBanner = ArrayList<Banner>()
        recommendAdapter!!.addHeader(HeaderView())

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        easyRecyclerView!!.setLayoutManager(linearLayoutManager)

        easyRecyclerView!!.setAdapter(recommendAdapter)

        easyRecyclerView!!.setRefreshListener(SwipeRefreshLayout.OnRefreshListener { getPresenter().getRecommend() })
    }

    override fun initData()
    {
        easyRecyclerView!!.showProgress()
        getPresenter().getRecommend()
        getPresenter().getBanner()
    }

    override fun onResume()
    {
        super.onResume()
        if (convenientBanner != null && !convenientBanner!!.isTurning)
        {
            convenientBanner!!.startTurning(4000)
        }
    }

    override fun onPause()
    {
        super.onPause()
        if (convenientBanner != null)
        {
            convenientBanner!!.stopTurning()
        }

    }

    override fun showProgress()
    {
    }

    override fun onCompleted()
    {
        easyRecyclerView!!.setRefreshing(false)
    }

    override fun onError(e: Throwable?)
    {
        LogUtils.w(e)
        if (SystemUtils.isNetWorkActive(context))
        {
            tvTips!!.setText(R.string.page_load_failed)
        }
        else
        {
            tvTips!!.setText(R.string.network_unavailable)
        }
        easyRecyclerView!!.showError()
    }

    override fun onGetRecommend(recommend: Recommend)
    {
    }

    override fun onGetRooms(list: List<Recommend.RoomBean>)
    {
        recommendAdapter!!.addAll(list)
        recommendAdapter!!.notifyDataSetChanged()

        if (recommendAdapter!!.count == 0)
        {
            easyRecyclerView!!.showEmpty()
        }
    }

    override fun onGetBanner(list: List<Banner>)
    {
        if (convenientBanner != null)
        {
            toSetList(listBanner, list, false)
            convenientBanner!!.notifyDataSetChanged()
        }
    }

    companion object
    {
        fun newInstance(): RecommendFragment
        {
            val args = Bundle()

            val fragment = RecommendFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private fun clickBannerItem(banner: Banner?)
    {
        if (banner != null)
        {
            if (banner.isRoom)
            {//如果是房间类型就点击进入房间
                startRoom(banner.link_object)
            }
            else
            {//广告类型
                startWeb(banner.title, banner.link)
            }
        }
    }

    inner class HeaderView : RecyclerArrayAdapter.ItemView
    {
        override fun onBindView(p0: View?)
        {
            convenientBanner!!.setPages(object : CBViewHolderCreator<Holder<Banner>>
            {
                override
                fun createHolder(): Holder<Banner>
                {
                    return ImageHolder()
                }
            }, listBanner)
                    .setPageIndicator(intArrayOf(R.drawable.ic_dot_normal, R.drawable.ic_dot_pressed))
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)

            if (! convenientBanner!!.isTurning())
            {
                convenientBanner!!.startTurning(4000)
            }
        }

        override fun onCreateView(p0: ViewGroup?): View
        {
            var v = LayoutInflater.from(this@RecommendFragment.mContext).inflate(R.layout.banner, null)
            convenientBanner = v.findViewById(R.id.convenientBanner) as ConvenientBanner<Banner>
            convenientBanner!!.setOnItemClickListener(object : OnItemClickListener
            {
                override
                fun onItemClick(position: Int)
                {
                    clickBannerItem(listBanner!![position])
                }
            })

            return v
        }
    }

    inner class ImageHolder : Holder<Banner>
    {

        private var iv: ImageView? = null

        override
        fun createView(context: Context): View
        {
            iv = ImageView(context)
            iv!!.scaleType = ImageView.ScaleType.CENTER_CROP

            return iv!!
        }

        override
        fun UpdateUI(context: Context, position: Int, data: Banner)
        {
            Glide.with(context).
                    load(data.thumb).placeholder(R.mipmap.live_default)
                    .error(R.mipmap.live_default)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iv)
        }
    }
}