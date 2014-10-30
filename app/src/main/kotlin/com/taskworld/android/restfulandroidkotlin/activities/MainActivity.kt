package com.taskworld.android.restfulandroidkotlin.activities

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.Button
import com.taskworld.android.restfulandroidkotlin.model.Movie
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.tag

class MainActivity : BaseActivity() {

    override val mContentLayoutResourceId = R.layout.activity_main

    //widgets
    val btCheckProduct by Delegates.lazy { bindView<Button>(R.id.btCheck) }
    val btCheckMovie by Delegates.lazy { bindView<Button>(R.id.btCheckMovie) }

    override fun setUp() {
        btCheckProduct.setOnClickListener { view ->
            startActivity(ProductListActivity.newInstance(this))
        }

        btCheckMovie.setOnClickListener { view ->
            startActivity(MovieListActivity.newInstance(this))
        }
    }

    public fun onEvent(results: Movie.ResultList) {
        Log.i(tag(), results.getResults().size.toString())
    }

}