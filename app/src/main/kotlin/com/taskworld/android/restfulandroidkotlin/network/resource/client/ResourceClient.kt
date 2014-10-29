package com.taskworld.android.restfulandroidkotlin.network.resource.client

import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.resource.router.ResourceRouter
import io.realm.Realm
import io.realm.RealmObject
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.network.request.GetListMovieSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.response.EventBusResponseListener
import com.taskworld.android.restfulandroidkotlin.network.resource.router.ResourceRouterImpl

class ResourceClient(builder: ResourceClient.Builder) {

    private var mSpiceManager: SpiceManager? = null
    private var mResourceRouter: ResourceRouter? = null
    private var mRealm: Realm? = null

    //initialize
    {
        mSpiceManager = builder.manager
        mResourceRouter = builder.router
        mRealm = builder.realm
    }

    class object {
        inner class Builder {

            var manager: SpiceManager? = null
            var router: ResourceRouter? = null
            var realm: Realm? = null

            fun setSpiceManager(manager: SpiceManager): Builder {
                this.manager = manager
                return this
            }

            fun setRouter(router: ResourceRouter): Builder {
                this.router = router
                return this
            }

            fun setRealm(realm: Realm): Builder {
                this.realm = realm
                return this
            }

            fun build(): ResourceClient {
                return ResourceClient(this)
            }
        }
    }

    fun <T: RealmObject> findAll(clazz: Class<T>) {
        findAll(clazz, null)
    }

    fun <T: RealmObject> findAll(clazz: Class<T>, args: Map<String, String>?) {
        val path = mResourceRouter!!.pathForAction("list", clazz, args)

        //db call
        val results = mRealm?.where(clazz)?.findAll()
        if (results != null) EventBus.getDefault().post(results)

        //network
        mSpiceManager?.execute(GetListMovieSpiceRequest(path!!), EventBusResponseListener())
    }
}