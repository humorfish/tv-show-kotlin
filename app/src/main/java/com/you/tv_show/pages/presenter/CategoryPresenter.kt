package com.you.tv_show.pages.presenter

import android.util.Log
import com.you.tv_show.App
import com.you.tv_show.bean.LiveCategory
import com.you.tv_show.pages.base.BasePresenter
import com.you.tv_show.pages.iview.ICategoryView
import com.you.tv_show.thread.ThreadPoolManager
import com.you.tv_show.util.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Administrator on 2017/6/27.
 */
class CategoryPresenter @Inject constructor(app: App) : BasePresenter<ICategoryView>(app)
{
    val TAG: String = "CategoryPresenter"

    fun getAllCategories()
    {
        view!!.showProgress()
        getAppComponent().getAPIService()
                .allCategories
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({list: List<LiveCategory> ->
                    run {
                        LogUtils.d("Response:" + list)

                        ThreadPoolManager.getInstance().execute(Runnable { getDaoSession().liveCategoryDao.insertOrReplaceInTx(list) })
                        if (isViewAttached)
                            view!!.onGetLiveCategory(list)
                    }
                },{err ->
                    run {
                        Log.i(TAG, err.message)
                        if (isViewAttached)
                            view!!.onError(err)
                    }
                },{->
                    run{
                        if (isViewAttached)
                            view!!.onCompleted()
                    }
                })
    }

    fun getAllCategoriesByDB()
    {
        val list = getDaoSession().liveCategoryDao.loadAll()
        LogUtils.d("list:" + list!!)
        if (list != null && list.size > 0)
        {
            if (isViewAttached)
                view!!.onGetLiveCategory(list)
        }

    }
}