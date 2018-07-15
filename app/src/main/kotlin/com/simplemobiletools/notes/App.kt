package com.simplemobiletools.notes

import android.app.Application
import com.facebook.stetho.Stetho
import com.simplemobiletools.commons.extensions.checkUseEnglish
import com.simplemobiletools.notes.utils.LogUtils
import com.squareup.leakcanary.LeakCanary

class App : Application() {
    val TAG = "App"
    override fun onCreate() { //override 用于继承的覆写
        super.onCreate()
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) { //用于分析内存泄露的工具
                return
            }
            LeakCanary.install(this) //leakCanary的实现
            Stetho.initializeWithDefaults(this)
            LogUtils.i(TAG, "in deug mode")
        }

        checkUseEnglish() //以扩展函数的形式为Application实现了这个函数
    }
}
