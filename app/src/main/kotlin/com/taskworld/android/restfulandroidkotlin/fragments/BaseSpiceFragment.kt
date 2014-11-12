package com.taskworld.android.restfulandroidkotlin.fragments

import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.service.TheMovieAPISpiceService

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

abstract class BaseSpiceFragment : BaseFragment() {

    val mSpiceManager: SpiceManager = SpiceManager(javaClass<TheMovieAPISpiceService>())

    override fun onStart() {
        super<BaseFragment>.onStart()
        mSpiceManager.start(getActivity())
    }

    override fun onStop() {
        super<BaseFragment>.onStop()
        mSpiceManager.shouldStop()
    }

    fun getServiceSpiceManager(): SpiceManager {
        return mSpiceManager
    }
}
