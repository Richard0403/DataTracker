package com.richard.tracker.api.impl

import com.richard.tracker.api.IDataCommit
import com.richard.tracker.constant.GlobalConfig
import com.richard.tracker.manager.ExposureManager
import com.richard.tracker.util.TrackerLog
import com.richard.tracker.util.TrackerUtil

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author richard
 * 邮箱：985507966@qq.com
 * 创建时间: 6/16/21     4:02 PM
 * 用途:
 ***************************************
 */
class DataCommitImpl: IDataCommit {

    override fun commitClickEvent(clickData: MutableMap<String, Any?>?) {
        TrackerUtil.trackClickData(clickData)
    }

    override fun commitExposureEvent(exposureData: MutableMap<String, Any?>?, exposureTime: Long) {
        TrackerLog.d("曝光时间==$exposureTime===" + "曝光数据："+ exposureData.toString())
        if (GlobalConfig.batchOpen) {
            ExposureManager.get().commitLogs.add(exposureData)
        } else {
            TrackerUtil.trackExploreData(mutableListOf<MutableMap<String, Any?>?>().apply{
                add(exposureData)
            })
        }
    }
}