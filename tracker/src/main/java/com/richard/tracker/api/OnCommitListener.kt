package com.richard.tracker.api

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author richard
 * 邮箱：985507966@qq.com
 * 创建时间: 6/23/21     3:04 PM
 * 用途: 对外传输接口
 ***************************************
 */
interface OnCommitListener {

    fun commitClickData(clickData: MutableMap<String, Any?>?)

    fun commitExposureData(exposureData: MutableList<MutableMap<String, Any?>?>)
}