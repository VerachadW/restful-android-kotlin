package com.taskworld.android.restfulandroidkotlin.network2.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.network2.OnDataReceivedEvent

/**
 * Created by VerachadW on 12/3/14.
 */
abstract class NetworkSpiceRequest<RESULT, API>(resultClass: Class<RESULT>, apiClass: Class<API>): RetrofitSpiceRequest<RESULT, API>(resultClass, apiClass) {

    var event: OnDataReceivedEvent<RESULT> by Delegates.notNull()

    open fun saveResult(result: RESULT) {

    }
}
