package com.richard.tracker.util

import android.util.Log
import com.richard.tracker.constant.GlobalConfig

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author wuzhiguo
 * 邮箱：wuzhiguo@ksjgs.com
 * 创建时间: 6/16/21     3:30 PM
 * 用途: 日志
 ***************************************
 */
object TrackerLog {
    private const val TAG = "TrackerLog"

    fun d(msg: String?) {
        if (GlobalConfig.logOpen) {
            Log.d(TAG, msg!!)
        }
    }

    fun v(msg: String?) {
        if (GlobalConfig.logOpen) {
            Log.v(TAG, msg!!)
        }
    }

    fun e(msg: String?) {
        if (GlobalConfig.logOpen) {
            Log.e(TAG, msg!!)
        }
    }
}