package com.taskworld.android.restfulandroidkotlin.fragments

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.TextView
import android.view.View

/**
 * Created by Kittinun Vantasin on 11/10/14.
 */

class TestFragment : BaseFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_test

    val tvOutput by Delegates.lazy { getRootView().bindView<TextView>(R.id.tvOutput) }

    val mContents by Delegates.lazy { listOf("1", "2", "3", "4", "5") }

    class object {
        fun newInstance(): TestFragment {
            return TestFragment()
        }
    }

    override fun setUp() {
    }

    override fun setUpUI(view: View) {
        if (mContents == null) {

        }
    }
}