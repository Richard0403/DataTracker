package com.richard.datatracker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.richard.datatracker.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_common.setOnClickListener {
            startActivity(Intent(this, CommonActivity::class.java))
        }

        btn_scroll.setOnClickListener {
            startActivity(Intent(this, ScrollActivity::class.java))
        }

        btn_banner.setOnClickListener {
            startActivity(Intent(this, BannerActivity::class.java))
        }

        btn_view_pager.setOnClickListener {
            startActivity(Intent(this, ViewPagerActivity::class.java))
        }

        btn_list.setOnClickListener {
            startActivity(Intent(this, RecyclerActivity::class.java))
        }
    }
}