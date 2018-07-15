package com.simplemobiletools.notes.extensions

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.simplemobiletools.notes.R
import com.simplemobiletools.notes.helpers.*

//扩展属性的使用
val Context.config: Config get() = Config.newInstance(applicationContext)

//如果函数体只有一个语句作为函数体,可省略括号,直接将语句作为函数返回值
val Context.dbHelper: DBHelper get() = DBHelper.newInstance(applicationContext)

//扩展函数的使用
fun Context.getTextSize() =
        //when语句代替switch语句, 每个语句块的最后一个语句就是其返回值
        when (config.fontSize) {
            FONT_SIZE_SMALL -> resources.getDimension(R.dimen.smaller_text_size)
            FONT_SIZE_LARGE -> resources.getDimension(R.dimen.big_text_size)
            FONT_SIZE_EXTRA_LARGE -> resources.getDimension(R.dimen.extra_big_text_size)
            else -> resources.getDimension(R.dimen.bigger_text_size)
        }

fun Context.updateWidget() {
    val widgetIDs = AppWidgetManager.getInstance(this).getAppWidgetIds(ComponentName(this, MyWidgetProvider::class.java))
    if (widgetIDs.isNotEmpty()) {
        Intent(this, MyWidgetProvider::class.java).apply {
            //那如果两个类(intent/context)都含有某个函数,那么调用顺序是什么呢?
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIDs)
            sendBroadcast(this)
        }
    }
}
