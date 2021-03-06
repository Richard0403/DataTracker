package com.richard.tracker.delegate

import android.view.View
import android.view.accessibility.AccessibilityEvent
import com.richard.tracker.constant.TrackerConstants
import com.richard.tracker.util.DataProcess
import com.richard.tracker.util.TrackerLog
import java.util.*

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author richard
 * 邮箱：985507966@qq.com
 * 创建时间: 6/17/21     9:39 AM
 * 用途:
 ***************************************
 */

class ViewDelegate: View.AccessibilityDelegate() {


    override fun sendAccessibilityEvent(clickView: View, eventType: Int) {
        TrackerLog.d("eventType: $eventType")
        if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            TrackerLog.d("click: $clickView")
            //优先查找ClickData
            var exposureData = clickView.getTag(TrackerConstants.TAG_CLICK_DATA) as MutableMap<String, Any?>?
            if (exposureData == null) {
                exposureData = clickView.getTag(TrackerConstants.TAG_EXPLORE_AND_CLICK_DATA) as MutableMap<String, Any?>?
            }
            exposureData?.let {
                DataProcess.commitClickParams(it)
            }
        }
        super.sendAccessibilityEvent(clickView, eventType)
    }
}