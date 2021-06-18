package com.richard.datatracker.ui

import android.os.Bundle
import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.richard.datatracker.R
import com.richard.datatracker.utils.ViewTagUtils.addExposureOrClickTag
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : BaseActivity() {

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
        setContentView(R.layout.activity_recycler)

        clv_content.adapter = RecyclerAdapter(networkImages).apply {
            setOnItemClickListener { adapter, view, position ->
                Log.i("tag", "列表点击")
            }
        }
    }


    inner class RecyclerAdapter(data: MutableList<String>?) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recyclerview, data) {

        override fun convert(holder: BaseViewHolder, item: String) {
            val iv_image = holder.getView<SimpleDraweeView>(R.id.iv_image)

            iv_image.setImageURI(item)

            val exposureData = mutableMapOf<String, Any?>("RecyclerView" to item + "===" + holder.adapterPosition)
            holder.itemView.addExposureOrClickTag(getPageCode(), exposureData)

        }

    }

}