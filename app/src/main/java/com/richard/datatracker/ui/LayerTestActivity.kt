package com.richard.datatracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.marginTop
import com.richard.datatracker.R
import com.richard.datatracker.utils.ViewTagUtils.addExposureOrClickTag
import kotlinx.android.synthetic.main.activity_layer_test.*

class LayerTestActivity : BaseActivity() {
    val viewCount = 80
    var layerCount = 50


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layer_test)

        addLayout(ll_content)

    }


    private fun addLayout(parentView: ViewGroup) {
        if (layerCount == 0) {
            for (i in 0..viewCount) {
                val button = Button(this).apply {
                    text = "按钮==$i"
                    layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)

                    (layoutParams as ViewGroup.MarginLayoutParams).topMargin = 100
                }
                val exposureData = mutableMapOf<String, Any?>("LayerTestActivity" to button.text)
                button.addExposureOrClickTag(getPageCode(), "clickAndShow", exposureData)

                parentView.addView(button)
            }
        } else {
            val linearLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
            }
            layerCount--
            parentView.addView(linearLayout)
            addLayout(linearLayout)
        }
    }
}