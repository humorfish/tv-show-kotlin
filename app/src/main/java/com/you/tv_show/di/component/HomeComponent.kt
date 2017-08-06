package com.you.tv_show.di.component

import com.you.tv_show.di.module.CateroyModule
import com.you.tv_show.di.module.LiveListModule
import com.you.tv_show.di.scope.FragmentScope
import com.you.tv_show.pages.fragment.HomeFragment
import com.you.tv_show.pages.presenter.CategoryPresenter
import com.you.tv_show.pages.presenter.LiveListPresenter
import dagger.Component

/**
 * Created by Administrator on 2017/6/28.
 */
@FragmentScope
@Component(modules = arrayOf(CateroyModule::class, LiveListModule::class), dependencies = arrayOf(AppComponent::class))
interface HomeComponent
{

    fun inject(homeFragment: HomeFragment)
    fun inject(liveListFragment: LiveListFragment)

    val cateroyPresenter: CategoryPresenter

    val liveListPresenter: LiveListPresenter

}