package com.taskworld.android.restfulandroidkotlin.resource.router

import io.realm.RealmObject
import com.taskworld.android.restfulandroidkotlin.extensions.plus

/**
 * Created by Kittinun Vantasin on 10/28/14.
 */

trait ResourceRouter {

    fun <T : RealmObject> getPathForAction(action: String, clazz: Class<T>): String? {
        return getPathForAction(action, clazz, null)
    }

    fun <T : RealmObject> getPathForAction(action: String, clazz: Class<T>, args: Map<String, Any>?): String? {
        if (action.equalsIgnoreCase("list")) {
            return getPathForListOnResource(clazz, args)
        } else if (action.isEmpty()) {
            return getPathForSingleOnResource(clazz, args)
        }
        return null
    }

    fun <T : RealmObject> getPathForListOnResource(clazz: Class<T>, args: Map<String, Any>?): String
    fun <T : RealmObject> getPathForSingleOnResource(clazz: Class<T>, args: Map<String, Any>?): String
}

class ResourceRouterImpl private (val extraPath: String?) : ResourceRouter {

    class object {
        fun newInstance() = ResourceRouterImpl(null)
        fun newInstance(extraPath: String) = ResourceRouterImpl(extraPath)
    }

    override fun <T : RealmObject> getPathForListOnResource(clazz: Class<T>, args: Map<String, Any>?): String {
        val builder = StringBuilder(clazz.getSimpleName().toLowerCase())
        if (extraPath != null) {
            builder + "/" + extraPath
        }
        return builder.toString()
    }

    override fun <T : RealmObject> getPathForSingleOnResource(clazz: Class<T>, args: Map<String, Any>?): String {
        val idValue = args?.get("id")
        if (idValue == null) throw IllegalArgumentException()

        val builder = StringBuilder(clazz.getSimpleName().toLowerCase())

        return (builder + "/" + idValue.toString()).toString()
    }
}
