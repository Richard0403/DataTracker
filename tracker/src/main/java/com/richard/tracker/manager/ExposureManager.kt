package com.richard.tracker.manager

import android.graphics.Rect
import android.os.Handler
import android.os.HandlerThread
import android.util.ArrayMap
import android.view.View
import android.view.ViewGroup
import com.richard.tracker.constant.GlobalConfig
import com.richard.tracker.constant.TrackerConstants
import com.richard.tracker.model.CommitLog
import com.richard.tracker.model.ExposureModel
import com.richard.tracker.model.ReuseLayoutHook
import com.richard.tracker.util.CommonHelper
import com.richard.tracker.util.DataProcess
import com.richard.tracker.util.TrackerLog
import com.richard.tracker.util.TrackerUtil
import java.lang.IllegalArgumentException
import java.util.*

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author wuzhiguo
 * 邮箱：wuzhiguo@ksjgs.com
 * 创建时间: 6/16/21     4:08 PM
 * 用途:
 ***************************************
 */
class ExposureManager {

    /**
     * whether or not to hit the exposure event
     */
    private var isSampleHit: Boolean = false

    private var traverseTime: Long = 0

    private lateinit var exposureHandler: Handler

    var commitLogs: MutableMap<String, CommitLog> = mutableMapOf()


    init {
        val exposureThread = HandlerThread("ViewTracker_exposure")
        exposureThread.start()

        exposureHandler =
            Handler(exposureThread.looper, Handler.Callback { msg ->
                when (msg.what) {
                    SINGLE_COMMIT_EXPOSURE -> {
                        val exposureInner: ExposureManager.ExposureInner = msg.obj as ExposureManager.ExposureInner
                        when (exposureInner.triggerType) {
                            TrackerConstants.TRIGGER_WINDOW_CHANGED -> for (controlName in exposureInner.lastVisibleViewMap.keys) {
                                // If the current window invokes change, all the visible views need to be committed.
                                val model: ExposureModel? = exposureInner.lastVisibleViewMap[controlName]
                                model?.let {
                                    model.endTime = System.currentTimeMillis()
                                    reportExposureData(model, controlName)
                                }
                            }
                            TrackerConstants.TRIGGER_VIEW_CHANGED -> for (controlName in exposureInner.lastVisibleViewMap.keys) {
                                // If the view is visible in the last trigger timing, but invisible this time, then we commit the view as a exposure event.
                                if (!exposureInner.currentVisibleViewMap.containsKey(controlName)) {
                                    val model: ExposureModel? = exposureInner.lastVisibleViewMap[controlName]
                                    model?.let {
                                        model.endTime = System.currentTimeMillis()
                                        reportExposureData(model, controlName)
                                    }

                                }
                            }
                        }
                    }
                    BATCH_COMMIT_EXPOSURE -> {
                        for (commitLog in commitLogs.values) {
                            // the exposure times inside page
                            commitLog.argsInfo.put(
                                "exposureTimes",
                                commitLog.exposureTimes.toString()
                            )
                            // Scene 3 (switch back and forth when press Home button) is excluded.
                            TrackerUtil.trackExploreData(
                                commitLog.argsInfo,
                                commitLog.totalDuration
                            )
                            TrackerLog.v(
                                "onActivityPaused batch commit " + "pageName=" + commitLog.pageName + ",viewName=" + commitLog.viewName
                                        + ",totalDuration=" + commitLog.totalDuration + ",args=" + commitLog.argsInfo.toString()
                            )
                        }

                        // clear after committed.
                        commitLogs.clear()
                    }
                    else -> {
                    }
                }
                false
            })
    }

    fun getExposureHandler(): Handler? {
        return exposureHandler
    }


    private fun reportExposureData(
        model: ExposureModel,
        viewTag: String
    ) {
        val duration: Long = getExposureViewDuration(model)
        if (duration > 0) {
            TrackerLog.v("ExposureView report $model exposure data $duration")
            val indexMap =
                HashMap<String, Any>()
            if (!GlobalConfig.exposureIndex.containsKey(viewTag)) {
                // commit firstly
                GlobalConfig.exposureIndex[viewTag] = 1
                indexMap["exposureIndex"] = 1
            } else {
                val index: Int = GlobalConfig.exposureIndex[viewTag] ?:0
                GlobalConfig.exposureIndex[viewTag] = index + 1
                indexMap["exposureIndex"] = index + 1
            }
            DataProcess.commitExposureParams(
                model.params,
                duration
            )
        } else {
            TrackerLog.d("曝光时间不足${GlobalConfig.timeThreshold}毫秒==${viewTag}")
        }
    }

    /**
     * for the exposure event
     *
     * @param view
     * @return
     */
    fun triggerViewCalculate(
        triggerType: Int,
        view: View?,
        lastVisibleViewMap: MutableMap<String, ExposureModel?>
    ) {
        if (!GlobalConfig.trackerExposureOpen) {
            return
        }
        val triggerTime = System.currentTimeMillis()
//        if (triggerTime - traverseTime < 100) {
//            TrackerLog.d("triggerTime interval is too close to 100ms")
//            return
//        }
//        traverseTime = triggerTime
        if (view == null) {
            TrackerLog.d("view is null")
            return
        }
        // Sample not hit
        isSampleHit = CommonHelper.isSamplingHit(GlobalConfig.exposureSampling)

        if (!isSampleHit) {
            TrackerLog.d("exposure isSampleHit is false")
            return
        }
        val currentVisibleViewMap: MutableMap<String, ExposureModel?> =
            ArrayMap()
        traverseViewTree(view, lastVisibleViewMap, currentVisibleViewMap)
        commitExposure(triggerType, lastVisibleViewMap, currentVisibleViewMap)
        TrackerLog.d("triggerViewCalculate")
    }

    fun traverseViewTree(view: View?, reuseLayoutHook: ReuseLayoutHook?) {
        if (!GlobalConfig.trackerExposureOpen) {
            return
        }

        reuseLayoutHook?.checkHookLayout(view)

        if (view is ViewGroup) {
            val group = view
            val childCount = group.childCount
            for (i in 0 until childCount) {
                traverseViewTree(group.getChildAt(i), reuseLayoutHook)
            }
        }
    }

    /**
     * find all the view that can be seen in screen.
     *
     * @param view
     */
    private fun traverseViewTree(
        view: View,
        lastVisibleViewMap: MutableMap<String, ExposureModel?>,
        currentVisibleViewMap: MutableMap<String, ExposureModel?>
    ) {
        if (CommonHelper.isViewHasExposureTag(view)) {
            wrapExposureCurrentView(view, lastVisibleViewMap, currentVisibleViewMap)
        }
        if (view is ViewGroup) {
            val group = view
            val childCount = group.childCount
            for (i in 0 until childCount) {
                traverseViewTree(group.getChildAt(i), lastVisibleViewMap, currentVisibleViewMap)
            }
        }
    }

    private fun wrapExposureCurrentView(
        view: View,
        lastVisibleViewMap: MutableMap<String, ExposureModel?>,
        currentVisibleViewMap: MutableMap<String, ExposureModel?>
    ) {

        var params = view.getTag(TrackerConstants.TAG_EXPLORE_DATA) as MutableMap<String, Any?>?
        if (params == null) {
            params = view.getTag(TrackerConstants.TAG_EXPLORE_AND_CLICK_DATA) as MutableMap<String, Any?>?
        }

        val viewTag = view.getTag(TrackerConstants.VIEW_TAG_UNIQUE_NAME) as String?

        val isWindowChange = view.hasWindowFocus()
        val exposureValid = checkExposureViewDimension(view)
        val needExposureProcess = isWindowChange && exposureValid
        if (!needExposureProcess) {
            return
        }

        if (viewTag.isNullOrBlank()) {
            throw IllegalArgumentException("没有有给曝光的view设置->VIEW_TAG_UNIQUE_NAME")
        }



        // only add the visible view in screen
        if (lastVisibleViewMap.containsKey(viewTag)) {
            val model = lastVisibleViewMap[viewTag]
            model?.params = params
            currentVisibleViewMap[viewTag] = model
        } else if (!currentVisibleViewMap.containsKey(viewTag)) {
            val model = ExposureModel()
            model.beginTime = System.currentTimeMillis()
            model.tag = viewTag
            model.params = params
            currentVisibleViewMap[viewTag] = model
        }
    }

    private fun commitExposure(
        triggerType: Int,
        lastVisibleViewMap: MutableMap<String, ExposureModel?>,
        currentVisibleViewMap: MutableMap<String, ExposureModel?>
    ) {
        val exposureInner = ExposureInner()
        exposureInner.triggerType = triggerType
        exposureInner.lastVisibleViewMap = mutableMapOf()
        for ((key, value) in lastVisibleViewMap) {
            exposureInner.lastVisibleViewMap[key] = (value!!.clone() as ExposureModel)
        }
        exposureInner.currentVisibleViewMap = mutableMapOf()
        for ((key, value) in currentVisibleViewMap) {
            exposureInner.currentVisibleViewMap[key] = (value!!.clone() as ExposureModel)
        }
        lastVisibleViewMap.clear()
        lastVisibleViewMap.putAll(currentVisibleViewMap)

        // transfer time-consuming operation to new thread.
        if (exposureInner.lastVisibleViewMap.isNotEmpty() || exposureInner.currentVisibleViewMap.isNotEmpty()) {
            val message = exposureHandler.obtainMessage()
            message.what = SINGLE_COMMIT_EXPOSURE
            message.obj = exposureInner
            exposureHandler.sendMessage(message)
        }
    }

    /**
     * check the visible width and height of the view, compared with the its original width and height.
     *
     * @param view
     * @return
     */
    private fun checkExposureViewDimension(view: View): Boolean {
        val width = view.width
        val height = view.height
        val GlobalVisibleRect = Rect()
        val isVisibleRect = view.getGlobalVisibleRect(GlobalVisibleRect)
        if (isVisibleRect) {
            val visibleWidth = GlobalVisibleRect.width()
            val visibleHeight = GlobalVisibleRect.height()

            return visibleWidth * 1.00 / width > GlobalConfig.dimThreshold && visibleHeight * 1.00 / height > GlobalConfig.dimThreshold
        } else {
            return false
        }
    }


    private fun getExposureViewDuration(model: ExposureModel): Long {
        if (model.beginTime > 0 && model.endTime > 0 && model.endTime > model.beginTime
        ) {
            val duration = model.endTime - model.beginTime
            // omit the value less than 100
            if (duration > GlobalConfig.timeThreshold && duration < GlobalConfig.maxTimeThreshold) {
                return duration
            }
        }
        return 0
    }


    companion object {
        /**
         * 单例
         */
        private var instance: ExposureManager? = null
            get() {
                if (field == null) {
                    field = ExposureManager()
                }
                return field
            }
        @Synchronized
        fun get(): ExposureManager{
            return instance!!
        }

        /**
         * 单个提交
         */
        val SINGLE_COMMIT_EXPOSURE = 0

        /**
         * 批量提交
         */
        val BATCH_COMMIT_EXPOSURE = 1
    }

    private inner class ExposureInner {
        var triggerType = 0
        var lastVisibleViewMap: MutableMap<String, ExposureModel> = mutableMapOf()
        var currentVisibleViewMap: MutableMap<String, ExposureModel> = mutableMapOf()
    }
}