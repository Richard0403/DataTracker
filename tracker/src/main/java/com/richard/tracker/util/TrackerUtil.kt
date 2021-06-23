package com.richard.tracker.util

import com.richard.tracker.manager.TrackerManager
import java.lang.IllegalArgumentException

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author richard
 * 邮箱：985507966@qq.com
 * 创建时间: 6/16/21     3:53 PM
 * 用途:
 ***************************************
 */

object TrackerUtil {

    fun trackClickData(clickData: MutableMap<String, Any?>?) {
        TrackerLog.d("点击数据："+ clickData.toString())
        TrackerManager.get().commitListener?.commitClickData(clickData)
    }

    fun trackExploreData(exposureData: MutableList<MutableMap<String, Any?>?>) {
        if (exposureData.isEmpty()) {
            throw IllegalArgumentException("传入曝光数据为空，不合法")
        }
        TrackerLog.d("曝光数据：$exposureData")
        TrackerManager.get().commitListener?.commitExposureData(exposureData)
    }


}