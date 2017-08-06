package com.you.tv_show.http

import com.you.tv_show.bean.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Administrator on 2017/6/13.
 */
interface APIService
{

    /**
     * 获取App启动页信息
     * @return
     */
    @get:GET("json/page/app-data/info.json?v=3.0.1&os=1&ver=4")
    val appStartInfo: Observable<AppStart>

    /**
     * 获取分类列表
     * @return
     * *
     * * categories/list.json
     */
    @get:GET("json/app/index/category/info-android.json?v=3.0.1&os=1&ver=4")
    val allCategories: Observable<List<LiveCategory>>

    /**
     * 获取推荐列表
     * @return
     */
    @get:GET("json/app/index/recommend/list-android.json?v=3.0.1&os=1&ver=4")
    val recommend: Observable<Recommend>

    /**
     * 获取直播列表
     * @return
     */
    @get:GET("json/play/list.json?v=3.0.1&os=1&ver=4")
    val liveListResult: Observable<LiveListResult>


    @GET("json/categories/{slug}/list.json?v=3.0.1&os=1&ver=4")
    fun getLiveListResultByCategories(@Path("slug") slug: String): Observable<LiveListResult>

    /**
     * 进入房间
     * @param uid
     * *
     * @return
     */
    @GET("json/rooms/{uid}/info.json?v=3.0.1&os=1&ver=4")
    fun enterRoom(@Path("uid") uid: String): Observable<Room>

    /**
     * 搜索
     * @param searchRequestBody
     * *
     * @return
     */
    @POST("site/search")
    fun search(@Body searchRequestBody: SearchRequestBody): Observable<SearchResult>

}