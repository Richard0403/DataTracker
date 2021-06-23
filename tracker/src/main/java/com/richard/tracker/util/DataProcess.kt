package com.richard.tracker.util

import android.text.TextUtils
import android.view.View
import com.richard.tracker.api.IDataCommit
import com.richard.tracker.constant.GlobalConfig
import com.richard.tracker.constant.TrackerConstants
import com.richard.tracker.manager.TrackerManager
import java.util.*

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author richard
 * 邮箱：985507966@qq.com
 * 创建时间: 6/16/21     4:54 PM
 * 用途:
 ***************************************
 */
object DataProcess {


    @Synchronized
    fun commitExposureParams(exposureData: MutableMap<String, Any?>?, exposureTime: Long) {

        val commit: IDataCommit = TrackerManager.get().getTrackerCommit()
        commit.commitExposureEvent(exposureData, exposureTime)
    }


    @Synchronized
    fun commitClickParams(clickData: MutableMap<String, Any?>) {
        TrackerLog.d("costTime=" + (System.currentTimeMillis() - GlobalConfig.start))
        val commit: IDataCommit = TrackerManager.get().getTrackerCommit()
        commit.commitClickEvent(clickData)
    }
}