package com.taskworld.android.restfulandroidkotlin.network.resource.client

import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.resource.router.ResourceRouter
import io.realm.Realm
import io.realm.RealmObject
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.network.request.GetListMovieSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.response.EventBusResponseListener
import com.taskworld.android.restfulandroidkotlin.network.request.GetMovieSpiceRequest
import com.taskworld.android.restfulandroidkotlin.extensions.toStartingLetterUppercase
import com.octo.android.robospice.request.SpiceRequest

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

        private val REQUEST_PACKAGE = "com.taskworld.android.restfulandroidkotlin.network.request"
        private val REQUEST_CLASS_PREFIX = "SpiceRequest"

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
        val httpVerb = "get"
        val action = "list"
        val path = mResourceRouter!!.getPathForAction(action, clazz, args)

        //db call
        val results = mRealm?.where(clazz)?.findAll()
        if (results != null) {
            EventBus.getDefault().post(results)
        }

        //network
        executeWithEventBusListener<T>(httpVerb, action, clazz.getSimpleName(), path!!)
    }


    fun <T: RealmObject> find(clazz: Class<T>, id: String) {
        find(clazz, id, null)
    }

    fun <T: RealmObject> find(clazz: Class<T>, id: String, args: Map<String, String>?) {
        val httpVerb = "get"
        val action = ""

        var newArgs = hashMapOf("id" to id)
        if (args != null) {
            newArgs.putAll(args)
        }

        val path = mResourceRouter!!.getPathForAction(action, clazz, newArgs)

        //db call
        val result = mRealm?.where(clazz)?.equalTo("id", id)?.findFirst()
        if (result != null) {
            EventBus.getDefault().post(result)
        }

        //network
        executeWithEventBusListener<T>(httpVerb, action, clazz.getSimpleName(), path!!)
    }

    fun <T> executeWithEventBusListener(httpVerb: String, action: String, resourceName: String, requestPath: String) {
        val className = listOf(httpVerb.toStartingLetterUppercase(),
                action.toStartingLetterUppercase(),
                resourceName.toStartingLetterUppercase(),
                REQUEST_CLASS_PREFIX).join("")
        val constructorOfClassName = Class.forName(REQUEST_PACKAGE + "." + className).getConstructor(javaClass<String>())

        [suppress("unchecked_cast")]
        val requestInstance = constructorOfClassName.newInstance(requestPath) as SpiceRequest<T>
        mSpiceManager?.execute(requestInstance, EventBusResponseListener())
    }
}