package com.taskworld.android.restfulandroidkotlin.network.response

import com.octo.android.robospice.request.listener.RequestListener
import com.octo.android.robospice.persistence.exception.SpiceException
import de.greenrobot.event.EventBus

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */

class EventBusResponseListener<T> : RequestListener<T> {

    override fun onRequestFailure(spiceException: SpiceException?) {
        EventBus.getDefault().post(spiceException)
    }

    override fun onRequestSuccess(result: T?) {
        EventBus.getDefault().post(result)
    }

}