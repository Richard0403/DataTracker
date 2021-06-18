package com.richard.datatracker.utils

import android.app.Activity
import java.lang.IllegalArgumentException

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author wuzhiguo
 * 邮箱：wuzhiguo@ksjgs.com
 * 创建时间: 6/18/21     3:34 PM
 * 用途:
 ***************************************
 */
object PageCode {

    val pageMap = mutableMapOf(
        "CommonActivity" to "普通页面",
        "CoverActivity" to "测试跳转新页面",
        "ScrollActivity" to "滚动页面",
        "ViewPagerActivity" to "viewPager页面"
    )

    fun Activity.getActivityPageCode(): String {
        pageMap[this.javaClass.simpleName]?.let {
            return it
        }?:let{
            throw IllegalArgumentException("pageCode 未注册 请检查")
        }
    }
}