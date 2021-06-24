package com.richard.datatracker.ui

import android.os.Bundle
import com.richard.datatracker.R
import com.richard.datatracker.utils.ViewTagUtils.addExposureOrClickTag
import kotlinx.android.synthetic.main.activity_scroll.*

class ScrollActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll)


        val exposureData0 = mutableMapOf<String, Any?>("text" to tv_0.text)
        tv_0.addExposureOrClickTag(getPageCode(), "Show", exposureData0)

        val exposureData1 = mutableMapOf<String, Any?>("text" to tv_1.text)
        tv_1.addExposureOrClickTag(getPageCode(),"Show", exposureData1)

        val exposureData2 = mutableMapOf<String, Any?>("text" to tv_2.text)
        tv_2.addExposureOrClickTag(getPageCode(),"Show", exposureData2)

        val exposureData3 = mutableMapOf<String, Any?>("text" to tv_3.text)
        tv_3.addExposureOrClickTag(getPageCode(), "Show",exposureData3)

        val exposureData4 = mutableMapOf<String, Any?>("text" to tv_4.text)
        tv_4.addExposureOrClickTag(getPageCode(), "Show", exposureData4)

        val exposureData5 = mutableMapOf<String, Any?>("text" to tv_5.text)
        tv_5.addExposureOrClickTag(getPageCode(), "Show", exposureData5)

        val exposureData6 = mutableMapOf<String, Any?>("text" to tv_6.text)
        tv_6.addExposureOrClickTag(getPageCode(),"Show", exposureData6)
    }
}