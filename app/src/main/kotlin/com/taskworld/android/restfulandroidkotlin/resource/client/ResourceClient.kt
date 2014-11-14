package com.taskworld.android.restfulandroidkotlin.resource.client

import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.resource.router.ResourceRouter
import io.realm.Realm
import io.realm.RealmObject
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.network.response.EventBusRequestListener
import com.taskworld.android.restfulandroidkotlin.extensions.toStartingLetterUppercase
import com.octo.android.robospice.request.SpiceRequest
import io.realm.RealmResults
import io.realm.RealmQuery
import com.taskworld.android.restfulandroidkotlin.resource.router.ResourceRouterImpl

class ResourceClient(builder: ResourceClient.Builder) {

    private var mSpiceManager: SpiceManager?
    private var mResourceRouter: ResourceRouter
    private var mRealm: Realm?
    private var mBus: EventBus

    //initialize
    {
        mSpiceManager = builder.manager
        mResourceRouter = builder.router ?: ResourceRouterImpl.newInstance()
        mRealm = builder.realm
        mBus = builder.bus ?: EventBus.getDefault()
    }

    class object {
        private val REQUEST_PACKAGE = "com.taskworld.android.restfulandroidkotlin.network.request"
        private val REQUEST_CLASS_SUFFIX = "SpiceRequest"

        inner class Builder {

            var manager: SpiceManager? = null
            var router: ResourceRouter? = null
            var realm: Realm? = null
            var bus: EventBus? = null

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

            fun setEventBus(bus: EventBus): Builder {
                this.bus = bus
                return this
            }

            fun build(): ResourceClient {
                return ResourceClient(this)
            }
        }
    }

    fun <T : RealmObject> create(clazz: Class<T>, f: (it: T) -> Unit) {
        mRealm!!.beginTransaction()
        var entity: T = mRealm!!.createObject(clazz)
        f(entity)
        mRealm!!.commitTransaction()
        EventBus.getDefault().post(entity)

        // TODO: Call Create API
    }

    fun <T : RealmObject> update(clazz: Class<T>, conditionMap: Map<String, String>, f: (it: RealmResults<T>) -> Unit) {
        mRealm!!.beginTransaction()
        var results = query(clazz, conditionMap)
        f(results)
        mRealm!!.commitTransaction()

        EventBus.getDefault().post(results)
        // TODO: Send change set to Controller

        // TODO: Call Update API
    }

    fun <T: RealmObject> delete(clazz: Class<T>, conditionMap: Map<String, String>?) {
        mRealm!!.beginTransaction()
        var results = query(clazz, conditionMap)

        // TODO: keep track delete item here

        results.clear()
        mRealm!!.commitTransaction()

        // TODO: Send change set to Controller

        // TODO: Call Delete API
    }

    private fun <T : RealmObject> query(clazz: Class<T>, conditionMap: Map<String, String>?): RealmResults<T>{
        var query: RealmQuery<T> = mRealm!!.where(clazz)
        if (conditionMap != null) {
            for ((key, value) in conditionMap) {
                // TODO: Need to support other logical operator
                query.equalTo(key, value)
            }
        }

        var results = query.findAll()

        return results
    }

    fun <T: RealmObject> findAll(clazz: Class<T>) {
        findAll(clazz, null)
    }

    fun <T: RealmObject> findAll(clazz: Class<T>, args: Map<String, String>?) {
        //db call
        val results = query(clazz, args)
        EventBus.getDefault().post(results)

        val httpVerb = "get"
        val action = "list"
        val path = mResourceRouter.getPathForAction(action, clazz, args)

        //network
        executeWithEventBusListener<T>(httpVerb, action, clazz.getSimpleName(), path!!)
    }

    fun <T : RealmObject> find(clazz: Class<T>, id: String) {
        find(clazz, id, null)
    }

    fun <T : RealmObject> find(clazz: Class<T>, id: String, args: Map<String, String>?) {
        val httpVerb = "get"
        val action = ""

        var newArgs = hashMapOf("id" to id)
        if (args != null) {
            newArgs.putAll(args)
        }

        val path = mResourceRouter.getPathForAction(action, clazz, newArgs)

        //db call
        val result = mRealm?.where(clazz)?.equalTo("id", id)?.findFirst()
        if (result != null) {
            EventBus.getDefault().post(result)
        }

        //network
        executeWithEventBusListener<T>(httpVerb, action, clazz.getSimpleName(), path!!)
    }

    fun <T> executeWithEventBusListener(httpVerb: String, action: String, resourceName: String, requestPath: String) {
        var extraPath = ""
        when (action) {
            "" -> extraPath = mResourceRouter.extraPathForSingle ?: ""
            "list" -> extraPath = mResourceRouter.extraPathForList ?: ""
        }

        val className = listOf(httpVerb.toStartingLetterUppercase(),
                action.toStartingLetterUppercase(),
                resourceName.toStartingLetterUppercase(),
                extraPath.replace("_", "").toStartingLetterUppercase(),
                REQUEST_CLASS_SUFFIX).join("")
        val constructorOfClassName = Class.forName(REQUEST_PACKAGE + "." + className).getConstructor(javaClass<String>())

        [suppress("unchecked_cast")]
        val requestInstance = constructorOfClassName.newInstance(requestPath) as SpiceRequest<T>
        mSpiceManager?.execute(requestInstance, EventBusRequestListener.newInstance(mBus))
    }
}