package com.simplemobiletools.notes.utils

import android.util.Log

object LogUtils {
    private const val TAG = "SimpleNotes"
    private val LogSwitch = true

    //只是为了刻意用lambda表达式来写
    private fun log(tag: String, msg: String, func: (String, String) -> Unit){
        if (LogSwitch) {
            func(TAG, "$tag : $msg")
        }
    }

    fun i(tag: String, msg: String) {
        log(tag, msg) {x, y -> Log.i(x, y)}
    }

    fun d(tag: String, msg:String) {
        log(tag, msg) {x, y -> Log.d(x,y)}
    }

    fun w(tag: String, msg:String) {
        log(tag, msg) {x, y -> Log.w(x,y)}
    }

    fun e(tag: String, msg:String) {
        log(tag, msg) {x, y -> Log.e(x,y)}
    }

}