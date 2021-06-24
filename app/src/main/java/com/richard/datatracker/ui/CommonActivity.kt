package com.richard.datatracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.richard.datatracker.R
import com.richard.datatracker.utils.ViewTagUtils.addExposureOrClickTag
import kotlinx.android.synthetic.main.activity_common.*

class CommonActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        btn_click.setOnClickListener {
            Toast.makeText(this, "点击了我", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CoverActivity::class.java))
        }

        btn_click_3.setOnClickListener {
            Toast.makeText(this, "点击了${btn_click_3.text}", Toast.LENGTH_SHORT).show()
        }

        val exposureData1 = mutableMapOf<String, Any?>()
        exposureData1["谁点的？"] = "我也不知道"
        btn_click.addExposureOrClickTag(getPageCode(),"clickAndShow", exposureData1)

        val exposureData2 = mutableMapOf<String, Any?>()
        exposureData1["谁点的？"] = btn_click_2.text.toString() + "点击"
        btn_click_2.addExposureOrClickTag(getPageCode(), "clickAndShow", exposureData2)

        val exposureData3 = mutableMapOf<String, Any?>()
        exposureData1["谁点的？"] = "xxxx点的"
        btn_click_3.addExposureOrClickTag(getPageCode(), "clickAndShow", exposureData2)
    }
}