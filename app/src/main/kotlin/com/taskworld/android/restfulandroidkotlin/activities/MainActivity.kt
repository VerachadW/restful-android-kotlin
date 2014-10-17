package com.taskworld.android.restfulandroidkotlin.activities

import android.app.Activity
import android.os.Bundle
import com.taskworld.android.restfulandroidkotlin.R

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}