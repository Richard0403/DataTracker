package com.richard.tracker.util

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
    }

    fun trackExploreData(exposureData: MutableMap<String, Any?>?, exposureTime: Long) {
        TrackerLog.d("曝光时间==$exposureTime===" + "曝光数据："+ exposureData.toString())
    }


}