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
import com.taskworld.android.restfulandroidkotlin.model.PlayList
import java.util.HashMap
import com.squareup.okhttp.RequestBody
import com.taskworld.android.restfulandroidkotlin.extensions.fromSnakeToCamel

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

    enum class Action {
        NONE
        CREATE {
            override fun getVerb(): String {
                return "post"
            }
        }
        UPDATE {
            override fun getVerb(): String {
                return "put"
            }
        }
        DELETE {
            override fun getVerb(): String {
                return "delete"
            }
        }
        GET {
            override fun getVerb(): String {
                return "get"
            }
        }
        GET_LIST {
            override fun getVerb(): String {
                return "get_list"
            }

            override fun toString(): String {
                return "list"
            }
        }

        open fun getVerb(): String {
            return ""
        }

        override fun toString(): String {
            return this.name().toLowerCase()
        }
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
        mBus.post(entity)

        // TODO: Call Create API
        val action = Action.CREATE
        val path: String? = mResourceRouter.getPathForAction(action, clazz)

        executeWithEventBusListener(action, clazz.getSimpleName(), path!!, entity, clazz)
    }

    fun <T : RealmObject> update(clazz: Class<T>, conditionMap: Map<String, String>, f: (it: RealmResults<T>) -> Unit) {
        mRealm!!.beginTransaction()
        var results = query(clazz, conditionMap)
        f(results)
        mRealm!!.commitTransaction()

        EventBus.getDefault().post(results)

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
        mBus.post(results)

        val action = Action.GET_LIST
        val path = mResourceRouter.getPathForAction(action, clazz, args)

        //network
        executeWithEventBusListener<T>(action, clazz.getSimpleName(), path!!)
    }

    fun <T : RealmObject> find(clazz: Class<T>, id: String) {
        find(clazz, id, null)
    }

    fun <T : RealmObject> find(clazz: Class<T>, id: String, args: Map<String, String>?) {
        val action = Action.GET

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
        executeWithEventBusListener<T>(action, clazz.getSimpleName(), path!!)
    }

    fun <T : RealmObject> executeWithEventBusListener(action: ResourceClient.Action, resourceName: String, requestPath: String) {
        executeWithEventBusListener<T>(action, resourceName, requestPath, null, null)
    }

    fun <T : RealmObject> executeWithEventBusListener(action: ResourceClient.Action, resourceName: String, requestPath: String, entity: T?, clazz: Class<T>?) {
        var extraPath = ""
        when (action) {
            Action.GET -> extraPath = mResourceRouter.extraPathForSingle ?: ""
            Action.GET_LIST -> extraPath = mResourceRouter.extraPathForList ?: ""
        }

        if (mSpiceManager != null) {
            val className = listOf(action.getVerb().fromSnakeToCamel(),
                    resourceName.toStartingLetterUppercase(),
                    extraPath.replace("_", "").toStartingLetterUppercase(),
                    REQUEST_CLASS_SUFFIX).join("")

            [suppress("unchecked_cast")]
            var requestInstance: SpiceRequest<T>
            if (entity != null) {
                val constructorOfClassName = Class.forName(REQUEST_PACKAGE + "." + className).getConstructor(javaClass<String>(), clazz)
                requestInstance = constructorOfClassName.newInstance(requestPath, entity) as SpiceRequest<T>
            } else {
                val constructorOfClassName = Class.forName(REQUEST_PACKAGE + "." + className).getConstructor(javaClass<String>())
                requestInstance = constructorOfClassName.newInstance(requestPath) as SpiceRequest<T>
            }
            mSpiceManager?.execute(requestInstance, EventBusRequestListener.newInstance(action, mBus))
        }
    }
}