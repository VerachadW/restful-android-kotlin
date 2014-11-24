package com.taskworld.android.restfulandroidkotlin.events

import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient

/**
 * Created by VerachadW on 11/20/14.
 */
class OnDataReceivedEvent<T>(val action: ResourceClient.Action, val data: T) : BaseEvent() {


}