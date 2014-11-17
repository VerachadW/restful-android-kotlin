package com.taskworld.android.restfulandroidkotlin.activities.presenter

import de.greenrobot.event.EventBus

/**
 * Created by Kittinun Vantasin on 11/17/14.
 */

trait BasePresenter {

    fun registerBus(bus: EventBus) {
        bus.register(this)
    }

    fun unregisterBus(bus: EventBus) {
        bus.unregister(this)
    }

    fun register() {
        registerBus(EventBus.getDefault())
    }

    fun unregister() {
        unregisterBus(EventBus.getDefault())
    }

}
