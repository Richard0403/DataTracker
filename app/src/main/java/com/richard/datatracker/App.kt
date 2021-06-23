package com.richard.datatracker

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.richard.tracker.manager.TrackerManager

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author richard
 * 邮箱：985507966@qq.com
 * 创建时间: 6/17/21     10:08 AM
 * 用途:
 ***************************************
 */
class App: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)

        TrackerManager.get().initTracker(
            this,
            trackerOpen = true,
            trackerExposureOpen = true,
            logOpen = true)
    }

}