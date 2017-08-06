package com.you.tv_show.pages.base;

import com.you.tv_show.mvp.MvpView;

/**
 * Created by Administrator on 2017/5/10.
 */

public interface BaseView extends MvpView
{
    void showProgress();

    void onCompleted();

    void onError(Throwable e);
}
