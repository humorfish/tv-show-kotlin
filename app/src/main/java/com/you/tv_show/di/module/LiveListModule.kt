package com.you.tv_show.di.module

import com.you.tv_show.App
import com.you.tv_show.di.scope.FragmentScope
import com.you.tv_show.pages.presenter.LiveListPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Administrator on 2017/6/28.
 */
@Module
public class LiveListModule(var app: App)
{
    @FragmentScope
    @Provides
    fun provideLiveListPresenter(): LiveListPresenter
    {
        return LiveListPresenter(app)
    }
}