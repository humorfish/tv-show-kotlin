package com.you.tv_show.di.component

import android.content.Context
import com.you.tv_show.App
import com.you.tv_show.di.module.AppModule
import com.you.tv_show.http.APIService
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Administrator on 2017/6/13.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
public interface AppComponent
{
    fun inject(app: App)
    fun getContext(): Context
    fun getRetrofit(): Retrofit
    fun getOkHttpClient(): OkHttpClient
    fun getAPIService(): APIService
}