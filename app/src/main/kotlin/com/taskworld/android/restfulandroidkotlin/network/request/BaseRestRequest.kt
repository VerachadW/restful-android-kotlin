package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.SpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.OnDataReceivedEvent
import kotlin.properties.Delegates
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network.api.MovieDBApi


/**
 * Created by Johnny Dew on 12/12/2014 AD.
 */

abstract class BaseRestRequest<RESULT, API> {
    abstract val localRequest: BaseLocalRequest<RESULT, API>?
    abstract val networkRequest: BaseNetworkRequest<RESULT, API>
    abstract val event: OnDataReceivedEvent<RESULT>

    var saveResultBlock: (result: RESULT) -> Unit = { }

    val requestId: Long = System.currentTimeMillis()


}

abstract class BaseLocalRequest<RESULT, API>(clazz: Class<RESULT>): SpiceRequest<RESULT>(clazz) {

}

abstract class BaseNetworkRequest<RESULT, API>(resultClass: Class<RESULT>, apiClass: Class<API>): RetrofitSpiceRequest<RESULT, API>(resultClass, apiClass) {

    open fun saveResult(result: RESULT) {

    }
}