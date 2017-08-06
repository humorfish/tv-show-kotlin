package com.you.tv_show.pages.base

import com.you.tv_show.App
import com.you.tv_show.db.DaoSession
import com.you.tv_show.di.component.AppComponent
import com.you.tv_show.mvp.MvpBasePresenter
import com.you.tv_show.util.LogUtils
import javax.inject.Inject

/**
 * Created by Administrator on 2017/6/2.
 */
public open class BasePresenter<V : BaseView> : MvpBasePresenter<V>
{
    private var app: App? = null
    private var mDaoSession: DaoSession? = null
    private var mAppComponent: AppComponent? = null

    @Inject
    constructor(app: App): super()
    {
        this.app = app
        mDaoSession = app.getDaoSession()
        mAppComponent = app.getAppCommponent()
    }

    public fun getApp(): App?
    {
        return app
    }

    fun getAppComponent(): AppComponent
    {
        return mAppComponent!!
    }

    fun getDaoSession(): DaoSession
    {
        return mDaoSession!!
    }

    override fun isViewAttached() : Boolean
    {
        LogUtils.d("isViewAttached:" + super.isViewAttached());
        return super.isViewAttached();
    }
}