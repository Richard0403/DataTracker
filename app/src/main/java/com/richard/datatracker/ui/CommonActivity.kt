package com.richard.datatracker.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.richard.datatracker.R
import com.richard.tracker.constant.TrackerConstants
import kotlinx.android.synthetic.main.activity_common.*

class CommonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        btn_click.setOnClickListener {
            Toast.makeText(this, "点击了我", Toast.LENGTH_SHORT).show()
        }

        btn_click.setTag(TrackerConstants.VIEW_TAG_UNIQUE_NAME, btn_click.hashCode().toString())
        val exposureData1 = mutableMapOf<String, Any?>()
        exposureData1[TrackerConstants.KEY_PAGE_CODE] = "普通页面"
        exposureData1[TrackerConstants.KEY_PARAM] = btn_click.text
        btn_click.setTag(TrackerConstants.TAG_EXPLORE_DATA, exposureData1)

        btn_click_2.setTag(TrackerConstants.VIEW_TAG_UNIQUE_NAME, btn_click_2.hashCode().toString())
        val exposureData2 = mutableMapOf<String, Any?>()
        exposureData2[TrackerConstants.KEY_PAGE_CODE] = "普通页面"
        exposureData2[TrackerConstants.KEY_PARAM] = btn_click_2.text
        btn_click_2.setTag(TrackerConstants.TAG_EXPLORE_DATA, exposureData2)

        btn_click_3.setTag(TrackerConstants.VIEW_TAG_UNIQUE_NAME, btn_click_3.hashCode().toString())
        val exposureData3 = mutableMapOf<String, Any?>()
        exposureData3[TrackerConstants.KEY_PAGE_CODE] = "普通页面"
        exposureData3[TrackerConstants.KEY_PARAM] = btn_click_3.text
        btn_click_3.setTag(TrackerConstants.TAG_EXPLORE_DATA, exposureData3)

    }
}