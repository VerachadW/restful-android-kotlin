package com.taskworld.android.restfulandroidkotlin.network.request

import com.taskworld.android.restfulandroidkotlin.network.api.MovieDBApi
import com.taskworld.android.restfulandroidkotlin.network.OnDataReceivedEvent

/**
 * Created by VerachadW on 12/16/14.
 */
class GetNewSessionRequest(val token: String): BaseRestRequest<Map<String, String>, MovieDBApi.Authentication>() {
    override val localRequest: BaseLocalRequest<Map<String, String>, MovieDBApi.Authentication>? = null
    override val networkRequest: BaseNetworkRequest<Map<String, String>, MovieDBApi.Authentication> = GetNewSessionNetworkRequest()
    override val event: OnDataReceivedEvent<Map<String, String>> = OnAuthenSuccessEvent(requestId)

    private inner class GetNewSessionNetworkRequest: BaseNetworkRequest<Map<String, String>, MovieDBApi.Authentication>(javaClass<Map<String, String>>(), javaClass<MovieDBApi.Authentication>()) {
        override fun loadDataFromNetwork(): Map<String, String>? {
            return getService().getNewSession(token)
        }
    }
}
