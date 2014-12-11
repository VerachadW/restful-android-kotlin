package com.taskworld.android.restfulandroidkotlin.network2.request

import com.octo.android.robospice.request.SpiceRequest
import com.taskworld.android.restfulandroidkotlin.network2.OnDataReceivedEvent
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import kotlin.properties.Delegates

/**
 * Created by VerachadW on 12/2/14.
 */
abstract class RealmSpiceRequest<RESULT, API>(val requestId: Long, clazz: Class<RESULT>, val networkRequest: RetrofitSpiceRequest<RESULT, API>): SpiceRequest<RESULT>(clazz) {

    var event: OnDataReceivedEvent<RESULT> by Delegates.notNull()
    var saveResultBlock: (result: RESULT) -> Unit = { }


}
