package com.you.tv_show.di.module

import android.content.Context
import com.you.tv_show.App
import com.you.tv_show.http.APIService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Administrator on 2017/6/13.
 */
@Module
class AppModule(private var context: App, private var baseUrl: String)
{
    /**
     * 默认超时时间 单位/秒
     */
    private val DEFAULT_TIME_OUT = 10L

    @Provides
    @Singleton
    fun provideContext(): Context
    {
        return context
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit
    {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient
    {
        return OkHttpClient().newBuilder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideAPIService(): APIService
    {
        return provideRetrofit().create(APIService::class.java)
    }
}