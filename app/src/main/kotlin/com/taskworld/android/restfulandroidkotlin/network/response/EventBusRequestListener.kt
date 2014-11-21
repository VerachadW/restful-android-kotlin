package com.taskworld.android.restfulandroidkotlin.network.response

import com.octo.android.robospice.request.listener.RequestListener
import com.octo.android.robospice.persistence.exception.SpiceException
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.events.OnDataReceivedEvent
import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient
import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient.Action

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */

class EventBusRequestListener<T> private (val action: ResourceClient.Action, val bus: EventBus) : RequestListener<T> {

    class object {

        fun newInstance<T>(): EventBusRequestListener<T> {
            return EventBusRequestListener(Action.NONE, EventBus.getDefault())
        }

        fun newInstance<T>(action: ResourceClient.Action): EventBusRequestListener<T> {
            return EventBusRequestListener(action, EventBus.getDefault())
        }

        fun newInstance<T>(action: ResourceClient.Action, bus: EventBus): EventBusRequestListener<T> {
            return EventBusRequestListener(action, bus)
        }
    }

    override fun onRequestFailure(spiceException: SpiceException?) {
        bus.post(spiceException)
    }

    override fun onRequestSuccess(result: T) {
        bus.post(if (action == Action.NONE) result else OnDataReceivedEvent(action, result))
    }

}