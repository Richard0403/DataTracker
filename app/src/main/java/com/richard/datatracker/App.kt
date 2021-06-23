package com.richard.datatracker

import android.app.Application
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.richard.tracker.api.OnCommitListener
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
            logOpen = true,
            onCommitListener = object : OnCommitListener {
                override fun commitClickData(clickData: MutableMap<String, Any?>?) {
                    //点击数据
                    Log.i("commitClickData", "commitClickData==$clickData")
                }

                override fun commitExposureData(exposureData: MutableList<MutableMap<String, Any?>?>) {
                    //曝光数据
                    Log.i("commitExposureData", "commitExposureData==$exposureData")
                }
            })
    }

}