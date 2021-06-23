package com.richard.tracker.api

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author wuzhiguo
 * 邮箱：wuzhiguo@ksjgs.com
 * 创建时间: 6/23/21     3:04 PM
 * 用途: 对外传输接口
 ***************************************
 */
interface OnCommitListener {

    fun commitClickData(clickData: MutableMap<String, Any?>?)

    fun commitExposureData(exposureData: MutableList<MutableMap<String, Any?>?>)
}