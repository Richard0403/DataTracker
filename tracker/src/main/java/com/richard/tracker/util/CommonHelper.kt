package com.richard.tracker.util

import android.app.Activity
import android.view.View
import com.richard.tracker.constant.TrackerConstants
import com.richard.tracker.manager.TrackerManager
import com.richard.tracker.ui.TrackerFrameLayout
import java.util.*

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author richard
 * 邮箱：985507966@qq.com
 * 创建时间: 6/16/21     5:26 PM
 * 用途:
 ***************************************
 */
object CommonHelper {
    /**
     * common info in the page for the click and exposure event
     *
     * @param tfLayout
     */
//    fun addCommonArgsInfo(tfLayout: TrackerFrameLayout) {
//        if (tfLayout.getContext() != null && tfLayout.getContext() is Activity) {
//            val decorView =
//                (tfLayout.getContext() as Activity).window.decorView
//            tfLayout.commonInfo.clear()
//            val commonInfoMap: MutableMap<String, Any?> = TrackerManager.get().getCommonInfoMap()
//            if (commonInfoMap != null) {
//                tfLayout.commonInfo.putAll(commonInfoMap)
//            }
//
//            // common info attached with the view tag
//            val commonInfo = decorView.getTag(TrackerConstants.DECOR_VIEW_TAG_COMMON_INFO) as HashMap<String, Any>
//            if (commonInfo != null && !commonInfo.isEmpty()) {
//                tfLayout.commonInfo.putAll(commonInfo)
//                TrackerLog.v("addCommonArgsInfo commonInfo $commonInfo")
//            }
//            TrackerLog.v("addCommonArgsInfo all commonInfo " + tfLayout.commonInfo)
//        }
//    }

    fun isViewHasExposureTag(view: View): Boolean {
        return view.getTag(TrackerConstants.TAG_EXPLORE_DATA) != null
                || view.getTag(TrackerConstants.TAG_EXPLORE_AND_CLICK_DATA) != null
    }

    fun isViewHasClickTag(view: View): Boolean {
        return view.getTag(TrackerConstants.TAG_CLICK_DATA) != null
                || view.getTag(TrackerConstants.TAG_EXPLORE_AND_CLICK_DATA) != null
    }

    /**
     * 采样因子计算
     */
    fun isSamplingHit(sample: Int): Boolean {
        val rand = Random()
        val samplingSeed = rand.nextInt(100)
        return samplingSeed < sample
    }
}