package com.taskworld.android.restfulandroidkotlin.view.fragment

import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.service.TheMovieAPISpiceService

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

abstract class BaseSpiceFragment : BaseFragment() {

    val mSpiceManager: SpiceManager = SpiceManager(TheMovieAPISpiceService::class.java)

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
