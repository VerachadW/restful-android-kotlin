package com.taskworld.android.restfulandroidkotlin.network.response

import com.octo.android.robospice.persistence.exception.SpiceException
import com.octo.android.robospice.request.listener.RequestListener
import de.greenrobot.event.EventBus

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */

class EventBusRequestListener<T>(val bus: EventBus) : RequestListener<T> {

    companion object {
        fun <T> newInstance(): EventBusRequestListener<T> {
            return EventBusRequestListener(EventBus.getDefault())
        }

        fun <T> newInstance(bus: EventBus): EventBusRequestListener<T> {
            return EventBusRequestListener(bus)
        }
    }

    override fun onRequestFailure(spiceException: SpiceException?) {
        bus.post(spiceException)
    }

    override fun onRequestSuccess(result: T) {
        bus.post(result)
    }

}