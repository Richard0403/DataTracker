package com.richard.tracker.api

import java.util.*

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author richard
 * 邮箱：985507966@qq.com
 * 创建时间: 6/16/21     3:38 PM
 * 用途:
 ***************************************
 */
interface IDataCommit {

    fun commitClickEvent(
        clickData: MutableMap<String, Any?>?
    )

    fun commitExposureEvent(
        exposureData: MutableMap<String, Any?>?,
        exposureTime: Long
    )

}