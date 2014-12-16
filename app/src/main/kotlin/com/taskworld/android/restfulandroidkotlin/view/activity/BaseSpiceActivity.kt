package com.taskworld.android.restfulandroidkotlin.view.activity

import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.service.TheMovieAPISpiceService
import com.taskworld.android.restfulandroidkotlin.network.service.RealmSpiceService

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */

abstract class BaseSpiceActivity : BaseActivity() {

    val mNetworkSpiceManager: SpiceManager = SpiceManager(javaClass<TheMovieAPISpiceService>())
    val mLocalSpiceManager: SpiceManager = SpiceManager(javaClass<RealmSpiceService>())

    override fun onStart() {
        super<BaseActivity>.onStart()
        mNetworkSpiceManager.start(this)
        mLocalSpiceManager.start(this)
    }

    override fun onStop() {
        super<BaseActivity>.onStop()
        mNetworkSpiceManager.shouldStop()
        mLocalSpiceManager.shouldStop()
    }

    fun getServiceSpiceManager(): SpiceManager {
        return mNetworkSpiceManager
    }

    fun getLocalSpiceManager(): SpiceManager {
        return mLocalSpiceManager
    }

}
