package com.taskworld.android.restfulandroidkotlin.network.request

import com.taskworld.android.restfulandroidkotlin.model.TV
import com.taskworld.android.restfulandroidkotlin.network.api.MovieDBApi
import com.taskworld.android.restfulandroidkotlin.network.OnDataReceivedEvent
import com.taskworld.android.restfulandroidkotlin.network.OnTVLoadedEvent
import io.realm.Realm

/**
 * Created by VerachadW on 12/16/14.
 */
class GetTVRequest(val realm: Realm, val category: String): BaseRestRequest<TV.ResultList, MovieDBApi.TVApi>() {

    override val localRequest: BaseLocalRequest<TV.ResultList, MovieDBApi.TVApi>? = null
    override val networkRequest: BaseNetworkRequest<TV.ResultList, MovieDBApi.TVApi> = GetTVNetworkRequest()
    override val event: OnDataReceivedEvent<TV.ResultList> = OnTVLoadedEvent(requestId)

    inner class GetTVNetworkRequest(): BaseNetworkRequest<TV.ResultList, MovieDBApi.TVApi>(javaClass<TV.ResultList>(), javaClass< MovieDBApi.TVApi>()) {
        override fun loadDataFromNetwork(): TV.ResultList? {
            return getService().getTVs(category)
        }
    }
}
