package com.you.tv_show.di.module

import com.you.tv_show.App
import dagger.Module

/**
 * Created by Administrator on 2017/6/26.
 */
@Module
class CateroyModule
{
    var app: App? = null
    public constructor(app: App)
    {
        this.app = app
    }


}