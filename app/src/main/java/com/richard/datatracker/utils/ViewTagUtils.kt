package com.richard.datatracker.utils

import android.view.View
import com.richard.tracker.constant.TrackerConstants

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author richard
 * 邮箱：985507966@qq.com
 * 创建时间: 6/18/21     3:17 PM
 * 用途:
 ***************************************
 */
object ViewTagUtils {

    /**
     * @param tagType 默认TAG_EXPLORE_AND_CLICK_DATA，也就是曝光和点击的数据通用，
     * 可单独设置TAG_EXPLORE_DATA 或者 TAG_CLICK_DATA
     */
    fun View.addExposureOrClickTag(pageCode: String, event: String, params: MutableMap<String, Any?>,
                                   tagType: Int = TrackerConstants.TAG_EXPLORE_AND_CLICK_DATA) {
        setUniqueNameTag(this)

        val exposureData1 = mutableMapOf<String, Any?>()
        exposureData1[TrackerConstants.KEY_PAGE_CODE] = pageCode
        exposureData1[TrackerConstants.KEY_PARAM] = params
        exposureData1[TrackerConstants.KEY_EVENT] = event
        this.setTag(tagType, exposureData1)
    }


    private fun setUniqueNameTag (view: View) {
        view.setTag(TrackerConstants.VIEW_TAG_UNIQUE_NAME, view.hashCode().toString())
    }
}