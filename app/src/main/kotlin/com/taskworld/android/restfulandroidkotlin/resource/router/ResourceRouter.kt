package com.taskworld.android.restfulandroidkotlin.resource.router

import io.realm.RealmObject

/**
 * Created by Kittinun Vantasin on 10/28/14.
 */

interface ResourceRouter {

    val extraPathForList: String?
    val extraPathForSingle: String?

    fun <T : RealmObject> getPathForAction(action: String, clazz: Class<T>): String? {
        return getPathForAction(action, clazz, null)
    }

    fun <T : RealmObject> getPathForAction(action: String, clazz: Class<T>, args: Map<String, Any>?): String? {
        if (action.equals("list")) {
            return getPathForListOnResource(clazz, args)
        } else if (action.isEmpty()) {
            return getPathForSingleOnResource(clazz, args)
        }
        return null
    }

    fun <T : RealmObject> getPathForListOnResource(clazz: Class<T>, args: Map<String, Any>?): String
    fun <T : RealmObject> getPathForSingleOnResource(clazz: Class<T>, args: Map<String, Any>?): String
}

class ResourceRouterImpl(override val extraPathForList: String?, override val extraPathForSingle: String?) : ResourceRouter {

    companion object {
        fun newInstance() = ResourceRouterImpl(null, null)
        fun newInstance(extraPath: String?) = ResourceRouterImpl(extraPath, null)
        fun newInstance(extraPathForList: String?, extraPathForSingle: String?) = ResourceRouterImpl(extraPathForList, extraPathForSingle)
    }

    override fun <T : RealmObject> getPathForListOnResource(clazz: Class<T>, args: Map<String, Any>?): String {
        return clazz.getSimpleName().toLowerCase()
    }

    override fun <T : RealmObject> getPathForSingleOnResource(clazz: Class<T>, args: Map<String, Any>?): String {
        val idValue = args?.get("id")
        if (idValue == null) throw IllegalArgumentException()

        val builder = StringBuilder(clazz.getSimpleName().toLowerCase())

        return (builder.toString() + "/" + idValue.toString()).toString()
    }
}
