package com.you.tv_show

import android.widget.RadioButton
import com.you.tv_show.pages.base.BaseActivity


class MainActivity : BaseActivity()
{
    private var TAG = "MainActivity"

    private var rbHome: RadioButton? = null
    private var rbLive: RadioButton? = null
    private var rbFollw: RadioButton? = null
    private var rbMe: RadioButton? = null


    override fun getRootViewId(): Int
    {
        return R.layout.activity_main
    }

    override fun initView()
    {
        rbHome = findViewById(R.id.rbHome) as RadioButton?
        rbLive = findViewById(R.id.rbLive) as RadioButton?
        rbFollw = findViewById(R.id.rbFollw) as RadioButton?
        rbMe = findViewById(R.id.rbMe) as RadioButton?
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object
    {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
