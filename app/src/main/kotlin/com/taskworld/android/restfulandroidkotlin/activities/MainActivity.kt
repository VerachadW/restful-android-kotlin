package com.taskworld.android.restfulandroidkotlin.activities

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.findView
import android.widget.Button
import android.content.Intent

class MainActivity : BaseActivity() {

    override val mContentLayoutResourceId = R.layout.activity_main

    //widgets
    val btCheckProduct by Delegates.lazy { findView<Button>(R.id.btCheck) }

    override fun setUp() {
        btCheckProduct.setOnClickListener { view ->
            startActivity(ProductListActivity.newInstance(this))
        }
    }

}