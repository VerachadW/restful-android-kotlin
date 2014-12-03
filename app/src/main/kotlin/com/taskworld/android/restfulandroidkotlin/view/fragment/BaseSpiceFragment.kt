package com.taskworld.android.restfulandroidkotlin.view.fragment

import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.service.TheMovieAPISpiceService
import com.taskworld.android.restfulandroidkotlin.network2.service.RealmSpiceService

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

abstract class BaseSpiceFragment : BaseFragment() {

    val mSpiceManager: SpiceManager = SpiceManager(javaClass<TheMovieAPISpiceService>())
    val mLocalSpiceManager: SpiceManager = SpiceManager(javaClass<RealmSpiceService>())

    override fun onStart() {
        super<BaseFragment>.onStart()
        mSpiceManager.start(getActivity())
        mLocalSpiceManager.start(getActivity())
    }

    override fun onStop() {
        super<BaseFragment>.onStop()
        mSpiceManager.shouldStop()
        mLocalSpiceManager.shouldStop()
    }

    fun getServiceSpiceManager(): SpiceManager {
        return mSpiceManager
    }

    fun getLocalSpiceManager(): SpiceManager {
        return mLocalSpiceManager
    }
}
