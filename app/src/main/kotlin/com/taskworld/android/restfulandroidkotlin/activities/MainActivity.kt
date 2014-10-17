package com.taskworld.android.restfulandroidkotlin.activities

import android.util.Log
import com.taskworld.android.restfulandroidkotlin.R

class MainActivity : BaseActivity() {

    override val mContentLayoutResourceId: Int = R.layout.activity_main

    override fun setUp() {
        Log.i("${tag()}::setUp", "log")
    }

}