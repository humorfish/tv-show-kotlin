package com.you.tv_show.pages.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.you.tv_show.App
import com.you.tv_show.Constants
import com.you.tv_show.bean.LiveInfo
import com.you.tv_show.mvp.MvpFragment
import com.you.tv_show.pages.activity.ContentActivity
import com.you.tv_show.util.LogUtils


/**
 * Created by Administrator on 2017/6/2.
 */
public abstract class BaseFragment<V : BaseView, P : BasePresenter<V>> : MvpFragment<V, P>()
{
    var mContext: Context? = null

    private var rootView: View? = null

    private var mUnbinder: Unbinder? = null

    fun <T : View> findView(@IdRes id: Int): T
    {
        return rootView!!.findViewById(id) as T
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        mContext = activity
        rootView = inflater!!.inflate(getRootViewId(), container, false)
        mUnbinder = ButterKnife.bind(this, rootView!!)
        LogUtils.d("onCreateView")

        initUI()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        mUnbinder!!.unbind()
    }

    fun getRootView(): View
    {
        return rootView!!
    }

    fun replaceFragment(@IdRes id: Int, fragment: Fragment)
    {
        fragmentManager.beginTransaction().replace(id, fragment).commit()
    }

    fun replaceChildFragment(@IdRes id: Int, fragment: Fragment)
    {
        childFragmentManager.beginTransaction().replace(id, fragment).commit()
    }

    fun getApp(): App
    {
        return activity.application as App
    }

    fun <T> toSetList(list: MutableList<T>?, newList: List<T>?, isMore: Boolean)
    {

        if (list != null && newList != null)
        {
            synchronized(BaseFragment::class.java) {
                if (!isMore)
                {
                    list.clear()
                }
                list.addAll(newList)
            }
        }
    }

    protected fun getIntent(): Intent
    {
        return activity.intent
    }


    protected fun getFragmentIntent(fragmentKey: Int): Intent
    {
        val intent = getContentActivityIntent()
        intent.putExtra(Constants.KEY_FRAGMENT, fragmentKey)
        return intent
    }

    protected fun getContentActivityIntent(): Intent
    {
        return Intent(context, ContentActivity::class.java)
    }

    protected fun startActivity(cls: Class<*>)
    {
        startActivity(Intent(context, cls))
    }


    protected fun finish()
    {
        activity.finish()
    }


    protected fun startWeb(title: String, url: String)
    {
        val intent = getFragmentIntent(Constants.WEB_FRAGMENT)
        intent.putExtra(Constants.KEY_TITLE, title)
        intent.putExtra(Constants.KEY_URL, url)
        startActivity(intent)
    }

    private fun lowcase(text: String): String
    {
        if (TextUtils.isEmpty(text))
            return ""
        else
            return  text.toLowerCase()
    }

    protected fun startRoom(liveInfo: LiveInfo)
    {

        var fragmentKey = Constants.ROOM_FRAGMENT
        if (lowcase(Constants.SHOWING) === lowcase(liveInfo.category_slug))
        {
            fragmentKey = Constants.FULL_ROOM_FRAGMENT
        }

        val intent = getFragmentIntent(fragmentKey)
        intent.putExtra(Constants.KEY_UID, liveInfo.getUid())
        intent.putExtra(Constants.KEY_COVER, liveInfo.getThumb())
        startActivity(intent)
    }

    protected fun startLogin()
    {
        val intent = getFragmentIntent(Constants.LOGIN_FRAGMENT)
        startActivity(intent)
    }


    protected fun startAbout()
    {
        val intent = getFragmentIntent(Constants.ABOUT_FRAGMENT)
        startActivity(intent)
    }

    abstract fun getRootViewId(): Int

    abstract fun initUI()

    abstract fun initData()

}