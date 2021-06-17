package com.richard.tracker.manager

import android.R
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.app.TabActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import com.richard.tracker.api.IDataCommit
import com.richard.tracker.api.impl.DataCommitImpl
import com.richard.tracker.constant.GlobalConfig
import com.richard.tracker.ui.TrackerFrameLayout
import com.richard.tracker.util.TrackerLog

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author wuzhiguo
 * 邮箱：wuzhiguo@ksjgs.com
 * 创建时间: 6/16/21     3:21 PM
 * 用途:
 ***************************************
 */
class TrackerManager {

    private var mActivityLifecycle: ActivityLifecycleForTracker? = null

    private val commonInfoMap = mutableMapOf<String, Any?>()

    private var trackerCommit: IDataCommit? = null


    fun initTracker(
        application: Application,
        trackerOpen: Boolean,
        trackerExposureOpen: Boolean,
        logOpen: Boolean
    ) {
        GlobalConfig.mApplication = application
        GlobalConfig.trackerOpen = trackerOpen
        GlobalConfig.trackerExposureOpen = trackerExposureOpen
        GlobalConfig.logOpen = logOpen
        if (GlobalConfig.trackerOpen || GlobalConfig.trackerExposureOpen) {
            mActivityLifecycle = ActivityLifecycleForTracker()
            application.registerActivityLifecycleCallbacks(mActivityLifecycle)
        }
    }

    /**
     * 移除生命周期监听
     * @param application
     */
    fun unInit(application: Application) {
        if (mActivityLifecycle != null) {
            application.unregisterActivityLifecycleCallbacks(mActivityLifecycle)
        }
    }


    /**
     * set common info inside the page
     *
     * @param commonMap
     */
    fun setCommonInfoMap(commonMap: MutableMap<String, Any?>) {
        commonInfoMap.clear()
        commonInfoMap.putAll(commonMap)
    }

    fun getCommonInfoMap(): MutableMap<String, Any?> {
        return commonInfoMap
    }

    fun setSampling(sampling: Int) {
        var sampling = sampling
        if (sampling < 0) {
            sampling = 0
        } else if (sampling > 100) {
            sampling = 100
        }
        GlobalConfig.sampling = sampling
    }

    fun attachTrackerFrameLayout(activity: Activity?) {
        // this is a problem: several activity exist in the TabActivity
        if (activity == null || activity is TabActivity) {
            return
        }
        // exist android.R.id.content not found crash
        try {
            val container = activity.findViewById<View>(R.id.content) as ViewGroup
            if (container.childCount > 0) {
                val root = container.getChildAt(0)
                if (root is TrackerFrameLayout) {
                    TrackerLog.d("has added attachTrackerFrameLayout $activity")
                } else {
                    val trackerFrameLayout = TrackerFrameLayout(activity)
                    while (container.childCount > 0) {
                        val view = container.getChildAt(0)
                        container.removeViewAt(0)
                        trackerFrameLayout.addView(view, view.layoutParams)
                    }
                    container.addView(
                        trackerFrameLayout,
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    )
                }
            }
        } catch (e: Exception) {
            TrackerLog.e(e.toString())
        }
    }

    private fun detachTrackerFrameLayout(activity: Activity?) {
        if (activity == null || activity is TabActivity) {
            return
        }
        try {
            val container =
                activity.findViewById<View>(R.id.content) as ViewGroup
                    ?: return
            if (container.getChildAt(0) is TrackerFrameLayout) {
                container.removeViewAt(0)
            }
        } catch (e: Exception) {
            TrackerLog.e(e.toString())
        }
    }


    /**
     * set own data commit method
     *
     * @param externalCommit
     */
    fun setCommit(externalCommit: IDataCommit?) {
        trackerCommit = externalCommit
    }

    fun getTrackerCommit(): IDataCommit {
        if (trackerCommit == null) {
            trackerCommit = DataCommitImpl()
        }
        return trackerCommit!!
    }

    /**
     * commit the data for exposure event in batch
     */
    private fun batchReport() {
        val time = System.currentTimeMillis()
        val handler: Handler? = ExposureManager.get().getExposureHandler()
        val message = handler?.obtainMessage()
        message?.what = ExposureManager.BATCH_COMMIT_EXPOSURE
        message?.let {
            handler?.sendMessage(message)
        }
        TrackerLog.v("batch report exposure views " + (System.currentTimeMillis() - time) + "ms")
    }


    companion object {
        /**
         * 单例
         */
        private var instance: TrackerManager? = null
            get() {
                if (field == null) {
                    field = TrackerManager()
                }
                return field
            }
        @Synchronized
        fun get(): TrackerManager{
            return instance!!
        }
    }

    private inner class ActivityLifecycleForTracker : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}
        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityResumed(activity: Activity) {
            TrackerLog.d("onActivityResumed activity $activity")
            attachTrackerFrameLayout(activity)
        }

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {
            if (GlobalConfig.trackerExposureOpen) {
                TrackerLog.d("onActivityStopped activity $activity")
                if (GlobalConfig.batchOpen) {
                    batchReport()
                }
            }
        }
        override fun onActivityDestroyed(activity: Activity) {
            detachTrackerFrameLayout(activity)
        }

        override fun onActivitySaveInstanceState(
            activity: Activity,
            bundle: Bundle
        ) {
        }
    }
}