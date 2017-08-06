package com.you.tv_show.pages.presenter

import com.you.tv_show.App
import com.you.tv_show.bean.LiveInfo
import com.you.tv_show.bean.LiveListResult
import com.you.tv_show.bean.P
import com.you.tv_show.bean.SearchRequestBody
import com.you.tv_show.pages.base.BasePresenter
import com.you.tv_show.pages.iview.ILiveListView
import com.you.tv_show.util.LogUtils
import com.you.tv_show.util.StringUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Administrator on 2017/6/28.
 */
class LiveListPresenter @Inject constructor(app: App) : BasePresenter<ILiveListView>(app)
{

    fun getLiveList(slug: String)
    {
        if (StringUtils.isBlank(slug))
        {
            getLiveList()
        }
        else
        {
            getLiveListBySlug(slug)
        }
    }

    fun getLiveList()
    {
        if (isViewAttached)
        {
            view!!.showProgress()
        }

        getAppComponent().getAPIService()
                .liveListResult
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ liveListResult ->
                    run {
                        com.you.tv_show.util.LogUtils.d("Response:" + liveListResult!!)
                        var list: kotlin.collections.List<com.you.tv_show.bean.LiveInfo>? = null
                        if (liveListResult != null)
                        {
                            list = liveListResult.data
                        }
                        if (isViewAttached)
                            view!!.onGetLiveList(list!!)
                    }
                }, { err ->
                    run {
                        if (isViewAttached)
                            view!!.onError(err)
                    }
                }, { ->

                    run {
                        if (isViewAttached)
                            view!!.onCompleted()
                    }
                })
    }

    fun getLiveListBySlug(slug: String)
    {
        if (isViewAttached)
        {
            view!!.showProgress()
        }


        getAppComponent().getAPIService()
                .getLiveListResultByCategories(slug)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ liveListResult: LiveListResult? ->
                    run {
                        LogUtils.d("Response:" + liveListResult)
                        var list: List<LiveInfo>? = null
                        if (liveListResult != null)
                        {
                            list = liveListResult.data
                        }

                        if (isViewAttached)
                            view!!.onGetLiveList(list!!)
                    }
                }, { err ->
                    run {
                        if (isViewAttached)
                            view!!.onError(err)
                    }
                }, { ->
                    run {
                        if (isViewAttached)
                            view!!.onCompleted()
                    }
                })
    }

    fun getLiveListByKey(key: String, page: Int)
    {
        getLiveListByKey(key, page, P.DEFAULT_SIZE)
    }

    fun getLiveListByKey(key: String, page: Int, pageSize: Int)
    {
        if (isViewAttached)
        {
            view!!.showProgress()
        }
        getAppComponent().getAPIService()
                .search(SearchRequestBody.getInstance(P(page, key, pageSize)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map({ result ->
                    run {
                        LogUtils.d("Response:" + result)
                        if (result != null)
                        {
                            if (result.data != null)
                            {
                                result.data.items
                            }
                            else
                            {
                                LogUtils.d(result.toString())
                            }
                        }
                        null
                    }
                })
                .onErrorReturn({ err ->
                    run {
                        LogUtils.w(err)
                        null
                    }
                })
                .subscribe({ list ->
                    run {
                        if (isViewAttached)
                        {
                            if (page > 0)
                            {
                                view!!.onGetMoreLiveList(list!!)
                            }
                            else
                            {
                                view!!.onGetLiveList(list!!)
                            }

                        }
                    }
                })

    }

}