package com.richard.datatracker.utils

import android.view.View
import com.richard.tracker.constant.TrackerConstants

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author wuzhiguo
 * 邮箱：wuzhiguo@ksjgs.com
 * 创建时间: 6/18/21     3:17 PM
 * 用途:
 ***************************************
 */
object ViewTagUtils {

    fun View.addExposureTag(pageCode: String, params: MutableMap<String, Any?>) {
        setUniqueNameTag(this)

        val exposureData1 = mutableMapOf<String, Any?>()
        exposureData1[TrackerConstants.KEY_PAGE_CODE] = pageCode
        exposureData1[TrackerConstants.KEY_PARAM] = params
        this.setTag(TrackerConstants.TAG_EXPLORE_DATA, exposureData1)
    }


    private fun setUniqueNameTag (view: View) {
        view.setTag(TrackerConstants.VIEW_TAG_UNIQUE_NAME, view.hashCode().toString())
    }
}