package com.richard.datatracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.richard.datatracker.utils.PageCode.getActivityPageCode

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    open fun getPageCode(): String {
        return getActivityPageCode()
    }
}