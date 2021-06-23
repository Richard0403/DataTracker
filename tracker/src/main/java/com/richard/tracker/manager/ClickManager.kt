package com.richard.tracker.manager

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.richard.tracker.constant.GlobalConfig
import com.richard.tracker.delegate.ViewDelegate
import com.richard.tracker.util.CommonHelper
import com.richard.tracker.util.TrackerLog
import java.util.*

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author richard
 * 邮箱：985507966@qq.com
 * 创建时间: 6/17/21     9:37 AM
 * 用途:
 ***************************************
 */
class ClickManager {

    private var instance: ClickManager? = null
    private var mDelegate: ViewDelegate? = null
    private var isSampleHit: Boolean? = null


    init {
        mDelegate = ViewDelegate()
    }


    /**
     * find the clicked view, register the View.AccessibilityDelegate, commit data when trigger the click event.
     *
     * @param activity
     * @param event
     */
    fun eventAspect(
        activity: Activity?,
        event: MotionEvent
    ) {
        GlobalConfig.start = System.currentTimeMillis()
        if (!GlobalConfig.trackerOpen) {
            return
        }
        if (activity == null) {
            return
        }
        // sample not hit
        if (isSampleHit == null) {
            isSampleHit = CommonHelper.isSamplingHit(GlobalConfig.sampling)
        }
        if (!isSampleHit!!) {
            TrackerLog.d("click isSampleHit is false")
            return
        }
        try {
            if (event.action == MotionEvent.ACTION_DOWN) {
                handleViewClick(activity, event)
            }
        } catch (th: Throwable) {
            TrackerLog.e(th.message)
        }
    }

    private fun handleViewClick(
        activity: Activity,
        event: MotionEvent
    ) {
        val view = activity.window.decorView
        val tagView: View? = null
        val clickView = getClickView(view, event, tagView)

        clickView?.accessibilityDelegate = mDelegate
    }

    /**
     * find the clicked view while loop.
     *
     * @param view
     * @param event
     * @return
     */
    private fun getClickView(
        view: View,
        event: MotionEvent,
        tagView: View?
    ): View? {
        var tagView = tagView
        var clickView: View? = null
        if (isClickView(view, event) && view.visibility == View.VISIBLE) {
            // if the click view is a layout with tag, just return.
            if (CommonHelper.isViewHasClickTag(view) && view.hasOnClickListeners()) {
                tagView = view
            }
            // traverse the layout
            if (view is ViewGroup) {
                val group = view
                for (i in group.childCount - 1 downTo 0) {
                    val childView = group.getChildAt(i)
                    clickView = getClickView(childView, event, tagView)
                    if (clickView != null && CommonHelper.isViewHasClickTag(clickView) && clickView.hasOnClickListeners()) {
                        return clickView
                    }
                }
            }
            if (tagView != null) {
                clickView = tagView
            }
        }
        return clickView
    }

    private fun isClickView(view: View, event: MotionEvent): Boolean {
        val clickX = event.rawX
        val clickY = event.rawY
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1]
        val width = view.width
        val height = view.height
        return !(clickX < x || clickX > x + width || clickY < y || clickY > y + height)
    }


    companion object {
        /**
         * 单例
         */
        private var instance: ClickManager? = null
            get() {
                if (field == null) {
                    field = ClickManager()
                }
                return field
            }
        @Synchronized
        fun get(): ClickManager{
            return instance!!
        }
    }
}