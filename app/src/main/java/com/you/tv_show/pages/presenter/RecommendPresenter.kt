package com.you.tv_show.pages.presenter

import com.you.tv_show.App
import com.you.tv_show.pages.base.BasePresenter
import com.you.tv_show.pages.iview.IRecommendView
import com.you.tv_show.util.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Administrator on 2017/7/10.
 */
class RecommendPresenter @Inject constructor(app: App) : BasePresenter<IRecommendView>(app)
{
    fun getRecommend()
    {
        if (isViewAttached)
        {
            view!!.showProgress()
        }
        getAppComponent().getAPIService()
                .recommend
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({recommend ->
                    run{
                        LogUtils.d("Response:" + recommend!!)
                        if (isViewAttached)
                            view!!.onGetRecommend(recommend)

                        if (recommend != null)
                        {
                            if (isViewAttached)
                                view!!.onGetRooms(recommend!!.room)
                        }
                    }}, {err ->
                    run{
                        if (isViewAttached)
                            view!!.onError(err)
                    }}, { ->
                    run{
                        if (isViewAttached)
                            view!!.onCompleted()
                    }})
    }

    fun getBanner()
    {
        getAppComponent().getAPIService()
                .appStartInfo
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({appStart ->
                    run{
                        if (appStart != null)
                        {
                            if (isViewAttached)
                                view!!.onGetBanner(appStart.getBanners())
                        }
                    }},
                    {err ->
                    run{
                        if (isViewAttached)
                            view!!.onError(err)
                    }}, {->
                    run{

                    }})
    }
}