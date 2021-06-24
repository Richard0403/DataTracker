package com.richard.datatracker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.richard.datatracker.R
import com.richard.datatracker.utils.ViewTagUtils.addExposureOrClickTag
import kotlinx.android.synthetic.main.activity_view_pager.*


class ViewPagerActivity : BaseActivity() {




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
        setContentView(R.layout.activity_view_pager)


        view_pager.adapter = ViewPagerAdapter(networkImages)
    }



    inner class ViewPagerAdapter(val paths: List<String>): PagerAdapter() {


        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val context: Context = container.context
            val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_banner, container, false)

            val imageView: SimpleDraweeView = itemView.findViewById<View>(R.id.iv_image) as SimpleDraweeView
            imageView.setImageURI(paths[position])

            container.addView(itemView)

            val exposureData = mutableMapOf<String, Any?>("viewPager" to paths[position] + "===" + position)
            imageView.addExposureOrClickTag(getPageCode(), "Show",exposureData)

            return itemView
        }


        override fun getCount(): Int {
            return paths.size
        }


        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }


        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View?)
        }
    }
}