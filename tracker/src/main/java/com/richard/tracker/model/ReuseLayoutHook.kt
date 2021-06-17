package com.richard.tracker.model

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.richard.tracker.constant.TrackerConstants
import com.richard.tracker.manager.ExposureManager
import com.richard.tracker.ui.TrackerFrameLayout
import com.richard.tracker.util.TrackerLog
import java.util.*

/**
 ***************************************
 * 项目名称:DataTracker
 * @Author wuzhiguo
 * 邮箱：wuzhiguo@ksjgs.com
 * 创建时间: 6/17/21     9:23 AM
 * 用途:
 ***************************************
 */
class ReuseLayoutHook(
    trackerFrameLayout: TrackerFrameLayout
) {

    private val HOOK_VIEW_TAG = -9100

    private lateinit var mRootLayout: TrackerFrameLayout
    private val mList: MutableList<ViewHookListener> =
        ArrayList()

    private interface ViewHookListener {
        fun isValid(view: View?): Boolean
        fun hookView(view: View?)
    }


    init {

        mRootLayout = trackerFrameLayout
        // replace with the onFling()
        //mList.add(new RecyclerViewHook());
        //mList.add(new AbsListViewHook());
        mList.add(ViewPagerHook())
    }

    private inner class ViewPagerHook : ViewHookListener {
        override fun isValid(view: View?): Boolean {
            return view is ViewPager
        }



        override fun hookView(view: View?) {
            val viewPager: ViewPager = view as ViewPager
            val tag: Any = viewPager.getTag(HOOK_VIEW_TAG)
            if (tag != null && tag !is Boolean) {
                return
            }
            val added = tag as Boolean
            if (added != null && added) {
                return
            }
            viewPager.addOnPageChangeListener(ViewPagerOnPageChangeListener())
            viewPager.setTag(HOOK_VIEW_TAG, true)
            TrackerLog.d("ViewPager addOnPageChangeListener.")
        }
    }



    fun checkHookLayout(view: View?) {
        for (listener in mList) {
            if (listener != null && listener.isValid(view)) {
                listener.hookView(view)
            }
        }
    }

    private inner class ViewPagerOnPageChangeListener : ViewPager.OnPageChangeListener {
        private var state: Int = ViewPager.SCROLL_STATE_IDLE

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            if (state != ViewPager.SCROLL_STATE_SETTLING) {
                ExposureManager.get().triggerViewCalculate(
                    TrackerConstants.TRIGGER_VIEW_CHANGED,
                    mRootLayout,
                    mRootLayout.getLastVisibleViewMap()
                )
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (this.state == ViewPager.SCROLL_STATE_SETTLING && state == ViewPager.SCROLL_STATE_IDLE) {
                ExposureManager.get().triggerViewCalculate(
                    TrackerConstants.TRIGGER_VIEW_CHANGED,
                    mRootLayout,
                    mRootLayout.getLastVisibleViewMap()
                )
            }
            this.state = state
        }
    }
}