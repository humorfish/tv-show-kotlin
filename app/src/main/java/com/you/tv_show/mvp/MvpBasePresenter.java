package com.you.tv_show.mvp;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/5/10.
 */

public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V>
{
    private WeakReference<V> viewRef;

    @Override
    public void attachView(V view)
    {
        viewRef = new WeakReference<V>(view);
    }

    @Nullable
    public V getView()
    {
        return viewRef == null ? null : viewRef.get();
    }

    public boolean isViewAttached()
    {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void detachView(boolean retainInstance)
    {
        if (viewRef != null)
        {
            viewRef.clear();
            viewRef = null;
        }
    }
}
