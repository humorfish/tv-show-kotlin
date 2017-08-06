package com.you.tv_show.util

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import android.text.TextUtils
import android.view.KeyEvent
import android.widget.EditText
import java.io.File

/**
 * Created by Administrator on 2017/8/6.
 */
class SystemUtils private constructor()
{



    init
    {
        throw AssertionError()
    }

    companion object
    {

        /**
         * 异步线程
         * @param runnable
         */
        fun asyncThread(runnable: Runnable)
        {
            Thread(runnable).start()
        }


        fun isNetWorkActive(context: Context): Boolean
        {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && activeNetInfo.isConnected
        }

        /**
         * 发送按键按下事件
         * @param keyCode
         */
        fun sendKeyCode(keyCode: Int)
        {
            asyncThread(Runnable {
                try
                {
                    val inst = Instrumentation()
                    inst.sendKeyDownUpSync(keyCode)
                }
                catch (e: Exception)
                {
                    LogUtils.e("Exception when sendPointerSync", e)
                }
            })
        }

        /**
         * 退格删除键

         * @param et
         */
        fun deleteClick(et: EditText)
        {
            val keyEventDown = KeyEvent(KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_DEL)
            et.onKeyDown(KeyEvent.KEYCODE_DEL, keyEventDown)
        }


        /**
         * 调用打电话界面

         * @param context
         * *
         * @param phoneNumber
         */
        fun call(context: Context, phoneNumber: String)
        {

            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(String.format("tel:%s", phoneNumber)))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            context.startActivity(intent)
        }


        /**
         * 调用发短信界面

         * @param context
         * *
         * @param phoneNumber
         */
        fun sendSMS(context: Context, phoneNumber: String)
        {

            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(String.format("smsto:%s", phoneNumber)))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            context.startActivity(intent)
        }

        /**
         * 发短信

         * @param phoneNumber
         * *
         * @param msg
         */
        fun sendSMS(phoneNumber: String, msg: String)
        {

            val sm = SmsManager.getDefault()

            val msgs = sm.divideMessage(msg)

            for (text in msgs)
            {
                sm.sendTextMessage(phoneNumber, null, text, null, null)
            }

        }

        /**
         * 拍照

         * @param activity
         * *
         * @param requestCode
         */
        fun imageCapture(activity: Activity, requestCode: Int)
        {
            imageCapture(activity, null, requestCode)
        }

        /**
         * 拍照

         * @param activity
         * *
         * @param path
         * *
         * @param requestCode
         */
        fun imageCapture(activity: Activity, path: String?,
                         requestCode: Int)
        {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (!TextUtils.isEmpty(path))
            {
                val uri = Uri.fromFile(File(path!!))
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
            activity.startActivityForResult(intent, requestCode)
        }

        /**
         * 拍照

         * @param fragment
         * *
         * @param path
         * *
         * @param requestCode
         */
        fun imageCapture(fragment: Fragment, path: String,
                         requestCode: Int)
        {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (!TextUtils.isEmpty(path))
            {
                val uri = Uri.fromFile(File(path))
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
            fragment.startActivityForResult(intent, requestCode)
        }

        /**
         * 获取包信息

         * @param context
         * *
         * @return
         */
        fun getPackageInfo(context: Context): PackageInfo?
        {
            var packageInfo: PackageInfo? = null
            try
            {
                packageInfo = context.packageManager.getPackageInfo(
                        context.packageName, 0)
            }
            catch (e: PackageManager.NameNotFoundException)
            {
                LogUtils.e(e)
            }
            catch (e: Exception)
            {
                LogUtils.e(e)
            }

            return packageInfo
        }

        /**
         * 获取当前应用包的版本名称

         * @param context
         * *
         * @return
         */
        fun getVersionName(context: Context): String?
        {
            val packageInfo = getPackageInfo(context)
            return packageInfo?.versionName
        }

        /**
         * 获取当前应用包的版本码

         * @param context
         * *
         * @return
         */
        fun getVersionCode(context: Context): Int
        {
            val packageInfo = getPackageInfo(context)
            return packageInfo?.versionCode ?: 0
        }

        /**
         * 跳转到app详情设置界面
         * @param context
         */
        fun startAppDetailSetings(context: Context)
        {
            val uri = Uri.fromParts("package", context.packageName, null)
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
            context.startActivity(intent)
        }

        /**
         * 安装apk
         * @param context
         * *
         * @param path
         */
        fun installApk(context: Context, path: String)
        {
            installApk(context, File(path))
        }

        /**
         * 安装apk
         * @param context
         * *
         * @param file
         */
        fun installApk(context: Context, file: File)
        {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val uriData = Uri.fromFile(file)
            val type = "application/vnd.android.package-archive"

            intent.setDataAndType(uriData, type)
            context.startActivity(intent)
        }


        /**
         * 卸载apk
         * @param context
         * *
         * @param packageName
         */
        @JvmOverloads fun uninstallApk(context: Context, packageName: String = context.packageName)
        {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val uriData = Uri.parse("package:" + packageName)
            intent.data = uriData
            context.startActivity(intent)
        }


        /**
         * 检测权限
         * @param context
         * *
         * @param permission
         * *
         * @return
         */
        fun checkSelfPermission(context: Context, permission: String): Boolean
        {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }


        /**
         * 申请权限
         * @param activity
         * *
         * @param permission
         * *
         * @param requestCode
         */
        fun requestPermission(activity: Activity, permission: String, requestCode: Int)
        {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
        }

        /**
         * 显示申请授权
         * @param activity
         * *
         * @param permission
         */
        fun shouldShowRequestPermissionRationale(activity: Activity, permission: String)
        {
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }
    }


}
/**
 * 卸载
 * @param context
 */
