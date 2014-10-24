package com.taskworld.android.restfulandroidkotlin.activities

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.Button
import com.taskworld.android.restfulandroidkotlin.network.request.DiscoverMovieSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.response.EventBusResponseListener
import com.taskworld.android.restfulandroidkotlin.model.Movie
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.tag

class MainActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId = R.layout.activity_main

    //widgets
    val btCheckProduct by Delegates.lazy { bindView<Button>(R.id.btCheck) }
    val btFetchMovie by Delegates.lazy { bindView<Button>(R.id.btFetchMovie) }

    override fun setUp() {
        btCheckProduct.setOnClickListener { view ->
            startActivity(ProductListActivity.newInstance(this))
        }

        btFetchMovie.setOnClickListener { view ->
            getServiceManager().execute(DiscoverMovieSpiceRequest("popularity.desc"),  EventBusResponseListener<Movie.ResultList>())
        }
    }

    public fun onEvent(results: Movie.ResultList) {
        Log.i(tag(), results.getResults().size.toString())
    }

}