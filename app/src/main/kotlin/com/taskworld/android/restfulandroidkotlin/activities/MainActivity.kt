package com.taskworld.android.restfulandroidkotlin.activities

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.Button
import com.taskworld.android.restfulandroidkotlin.model.Movie
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.tag
import com.taskworld.android.restfulandroidkotlin.network.request.GetTokenSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.response.EventBusRequestListener

class MainActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId = R.layout.activity_main

    //widgets
    val btCheckProduct by Delegates.lazy { bindView<Button>(R.id.btCheck) }
    val btCheckMovie by Delegates.lazy { bindView<Button>(R.id.btCheckMovie) }
    val btLogin by Delegates.lazy { bindView<Button>(R.id.btLogin) }

    override fun setUp() {
        btCheckProduct.setOnClickListener { view ->
            startActivity(ProductListActivity.newInstance(this))
        }

        btCheckMovie.setOnClickListener { view ->
            startActivity(MovieListActivity.newInstance(this))
        }

        btLogin.setOnClickListener { view ->
            getServiceSpiceManager().execute(GetTokenSpiceRequest(), EventBusRequestListener())
        }

    }

    public fun onEvent(map: Map<String, String>) {
        Log.d(tag(), "Request token: ${map.get("request_token")}")
    }

    public fun onEvent(results: Movie.ResultList) {
        Log.i(tag(), results.getResults().size.toString())
    }

}