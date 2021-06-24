package com.richard.datatracker.ui

import android.os.Bundle
import android.view.View
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.facebook.drawee.view.SimpleDraweeView
import com.richard.datatracker.R
import com.richard.datatracker.utils.ViewTagUtils.addExposureOrClickTag
import kotlinx.android.synthetic.main.activity_banner.*

/**
 * 本来用来测试viewPager的，结果ConvenientBanner是用recyclerview实现的，草了
 */
class BannerActivity : BaseActivity() {


    private val networkImages = mutableListOf(
        "https://audio11.kaishustory.com/kstory/admaster/4bf2d028-d59f-435a-a426-4f2ef4ee2f02.png",
        "https://hwcdn-image-new.kaishustory.com/kstory/admaster/8888184d-d00c-4920-8ba4-13a4742da8c2.jpg",
        "https://image10.kaishustory.com/kstory/admaster/830626ed-5983-496b-a7d2-38cc2308e847.png",
        "https://audio11.kaishustory.com/kstory/admaster/4bf2d028-d59f-435a-a426-4f2ef4ee2f02.png",
        "https://hwcdn-image-new.kaishustory.com/kstory/admaster/8888184d-d00c-4920-8ba4-13a4742da8c2.jpg",
        "https://image10.kaishustory.com/kstory/admaster/830626ed-5983-496b-a7d2-38cc2308e847.png"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)


        val convenient = findViewById<ConvenientBanner<String>>(R.id.convenientBanner)
        convenient.setPages(object : CBViewHolderCreator {
            override fun createHolder(itemView: View?): Holder<String?> {
                return NetWorkImageHolderView(itemView)
            }

            override fun getLayoutId(): Int {
                return R.layout.item_banner
            }

        }, networkImages)

    }

    override fun onResume() {
        super.onResume()
        convenientBanner.startTurning(4000)
    }

    override fun onPause() {
        super.onPause()
        convenientBanner.stopTurning()
    }


    inner class NetWorkImageHolderView(itemView: View?) : Holder<String?>(itemView) {
        var iv_image: SimpleDraweeView? = null

        override fun initView(itemView: View) {
            iv_image = itemView.findViewById(R.id.iv_image)
        }

        override fun updateUI(data: String?) {

            iv_image?.setImageURI(data)

            val exposureData = mutableMapOf<String, Any?>("text" to data + "===" + networkImages.indexOf(data))
            iv_image?.addExposureOrClickTag(getPageCode(), "click",  exposureData)
        }
    }

}