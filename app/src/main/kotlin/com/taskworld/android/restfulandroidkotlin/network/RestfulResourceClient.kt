package com.taskworld.android.restfulandroidkotlin.network

import de.greenrobot.event.EventBus
import kotlin.properties.Delegates
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.octo.android.robospice.request.SpiceRequest
import com.octo.android.robospice.SpiceManager
import com.octo.android.robospice.request.listener.RequestListener
import com.octo.android.robospice.persistence.exception.SpiceException
import com.taskworld.android.restfulandroidkotlin.network.request.BaseRestRequest

/**
 * Created by VerachadW on 11/26/14.
 */

class RestfulResourceClient private (val mNetworkSpiceManager: SpiceManager, val mLocalSpiceManager: SpiceManager){

    var mBus: EventBus by Delegates.notNull()

    enum class DataSource {
        DATABASE
        NETWORK
    }

    enum class ActionType {
        GET
        POST
        PUT
        DELETE
    }

    {
        mBus = bus?:EventBus.getDefault()
    }

    class object {

        var bus: EventBus? = null

        fun newInstance(networkSpiceManger: SpiceManager, localSpiceManager: SpiceManager): RestfulResourceClient {
            return RestfulResourceClient(networkSpiceManger, localSpiceManager)
        }

        fun newInstance(networkSpiceManager: SpiceManager, localSpiceManager: SpiceManager, eventBus: EventBus): RestfulResourceClient {
            bus = eventBus
            return RestfulResourceClient(networkSpiceManager, localSpiceManager)
        }
    }

    fun <RESULT, API> execute(request: BaseRestRequest<RESULT, API>) {
        if (request.localRequest != null) {
            mLocalSpiceManager.execute(request.localRequest, DatabaseRequestListener(request))
        } else {
            val listener = NetworkRequestListener(request.event)
            mNetworkSpiceManager.execute(request.networkRequest, listener)
        }

    }

    inner class DatabaseRequestListener<RESULT, API>(val request: BaseRestRequest<RESULT, API>): RequestListener<RESULT> {

        override fun onRequestFailure(spiceException: SpiceException?) {
            request.event.source = DataSource.DATABASE
            request.event.error = spiceException
            mBus.post(request.event)

        }

        override fun onRequestSuccess(result: RESULT) {
            request.event.source = DataSource.DATABASE
            request.event.result = result
            mBus.post(request.event)

            val listener = NetworkRequestListener(request.event)
            listener.f = request.saveResultBlock

            mNetworkSpiceManager.execute(request.networkRequest, listener)
        }
    }

    inner class NetworkRequestListener<RESULT>(val event: OnDataReceivedEvent<RESULT>): RequestListener<RESULT> {

        var f: (RESULT) -> Unit = {}

        override fun onRequestFailure(spiceException: SpiceException?) {
            event.source = DataSource.NETWORK
            event.error = spiceException
            mBus.post(event)
        }

        override fun onRequestSuccess(result: RESULT) {

            if (event.action == ActionType.GET) {
                f(result)
            }

            event.source = DataSource.NETWORK
            event.result = result
            mBus.post(event)

        }

    }
}