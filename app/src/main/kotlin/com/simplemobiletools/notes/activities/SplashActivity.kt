package com.simplemobiletools.notes.activities

import android.content.Intent
import com.simplemobiletools.commons.activities.BaseSplashActivity
import com.simplemobiletools.notes.helpers.OPEN_NOTE_ID
import com.simplemobiletools.notes.utils.LogUtils

class SplashActivity : BaseSplashActivity() {
    val TAG = "SplashAcivity"
    override fun initActivity() {
        //用属性来代替函数getExtras
        if (intent.extras?.containsKey(OPEN_NOTE_ID) == true) {
            //1. 初始化一个Intent, 没有new
            //2. apply是一个扩展函数, 当作类的method使用, apply接收一个block块的lambda为参数,
            //       执行后面的block内的语句,暗含一个this(这里指Intent)
            Intent(this, MainActivity::class.java).apply {
                putExtra(OPEN_NOTE_ID, intent.getIntExtra(OPEN_NOTE_ID, -1))
                startActivity(this)
            }
            LogUtils.d(TAG, "with open note id")
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            LogUtils.d(TAG, "without open note id")
        }
        finish()
    }
}
