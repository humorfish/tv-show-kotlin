package com.you.tv_show

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.you.tv_show.db.DaoMaster
import com.you.tv_show.db.DaoSession
import com.you.tv_show.di.component.AppComponent
import com.you.tv_show.di.module.AppModule

/**
 * Created by Administrator on 2017/6/2.
 */
class App : Application()
{
    private val BUGLY_ID = "28aeafeef1"

    private var mHelper: DaoMaster.DevOpenHelper? = null

    private var mDaoSession: DaoSession? = null

    private var mAppComponent: AppComponent? = null

    override fun attachBaseContext(base: Context)
    {
        super.attachBaseContext(base)

        MultiDex.install(base)
        Beta.installTinker()
    }

    override fun onCreate()
    {
        super.onCreate()
        initDatabase()
        // 调试时，将第三个参数改为true
        Bugly.init(this, BUGLY_ID, false)
        mAppComponent = DaggerAppComponent.builder().appModule(AppModule(this, Constants.BASE_URL)).build()
    }

    fun initDatabase()
    {
        mHelper = DaoMaster.DevOpenHelper(this, "tv-db", null)
        val daoMaster = DaoMaster(mHelper!!.getWritableDatabase())

        mDaoSession = daoMaster.newSession()
    }

    fun getAppCommponent(): AppComponent
    {
        return mAppComponent!!
    }

    fun getDaoSession(): DaoSession
    {
        return mDaoSession!!
    }
}