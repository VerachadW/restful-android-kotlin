package com.taskworld.android.restfulandroidkotlin.activities

import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.service.TheMovieDBSpiceService

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */

abstract class BaseSpiceActivity : BaseActivity() {

    val mSpiceManager: SpiceManager = SpiceManager(javaClass<TheMovieDBSpiceService>())

    override fun onStart() {
        super<BaseActivity>.onStart()
        mSpiceManager.start(this)
    }

    override fun onStop() {
        super<BaseActivity>.onStop()
        mSpiceManager.shouldStop()
    }

    fun getServiceSpiceManager(): SpiceManager {
        return mSpiceManager
    }

}
