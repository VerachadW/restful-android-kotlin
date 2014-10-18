package com.taskworld.android.restfulandroidkotlin.activities

import android.util.Log
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.extensions.TAG

class MainActivity : BaseActivity() {

    override val mContentLayoutResourceId: Int = R.layout.activity_main

    override fun setUp() {
        Log.i("${TAG()}", "setUp")
    }

}