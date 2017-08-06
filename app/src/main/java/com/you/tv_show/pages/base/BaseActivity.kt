package com.you.tv_show.pages.base

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.you.tv_show.R


/**
 * Created by Administrator on 2017/5/27.
 */
abstract class BaseActivity : AppCompatActivity()
{
    private val COLOR_DEFAULT = Color.parseColor("#7f000000")
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        mContext = this
        setContentView(getRootViewId())
        initView()

        setStatusViewColor(resources.getColor(R.color.colorPrimaryDark))
    }

    private fun setStatusViewColor(color: Int)
    {

    }

    abstract fun getRootViewId(): Int
    abstract fun initView()
}