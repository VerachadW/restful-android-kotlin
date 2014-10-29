package com.taskworld.android.restfulandroidkotlin.network.resource.router

import io.realm.RealmObject
import com.taskworld.android.restfulandroidkotlin.extensions.plus

/**
 * Created by Kittinun Vantasin on 10/28/14.
 */

trait ResourceRouter {

    fun <T : RealmObject> pathForAction(action: String, clazz: Class<T>): String? {
        return pathForAction(action, clazz, null)
    }

    fun <T : RealmObject> pathForAction(action: String, clazz: Class<T>, args: Map<String, Any>?): String? {
        if (action.equalsIgnoreCase("list")) {
            return pathForListOnResource(clazz, args)
        } else if (action.equalsIgnoreCase("single")) {
            return pathForSingleOnResource(clazz, args)
        }
        return null
    }

    fun <T : RealmObject> pathForListOnResource(clazz: Class<T>, args: Map<String, Any>?): String
    fun <T : RealmObject> pathForSingleOnResource(clazz: Class<T>, args: Map<String, Any>?): String
}

class ResourceRouterImpl(val extraPath: String) : ResourceRouter {

    override fun <T : RealmObject> pathForListOnResource(clazz: Class<T>, args: Map<String, Any>?): String {
        val builder = StringBuilder(clazz.getSimpleName().toLowerCase())
        return (builder + "/" + extraPath).toString()
    }

    override fun <T : RealmObject> pathForSingleOnResource(clazz: Class<T>, args: Map<String, Any>?): String {
        val idValue = args?.get("id")
        if (idValue == null) throw IllegalArgumentException()

        val builder = StringBuilder(clazz.getSimpleName().toLowerCase())

        return (builder + "/" + idValue.toString()).toString()
    }
}
