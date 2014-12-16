package com.taskworld.android.restfulandroidkotlin.network

import com.taskworld.android.restfulandroidkotlin.events.BaseEvent
import com.taskworld.android.restfulandroidkotlin.network.RestfulResourceClient.DataSource
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.network.RestfulResourceClient.ActionType
import kotlin.properties.ReadWriteProperty
import com.taskworld.android.restfulandroidkotlin.model.Movie

/**
 * Created by VerachadW on 12/2/14.
 */
abstract class OnDataReceivedEvent<RESULT>(val requestId: Long): BaseEvent() {

    var action: ActionType = ActionType.GET
    var source: DataSource = DataSource.DATABASE
    var result: RESULT = null
}