package com.taskworld.android.restfulandroidkotlin.resource.router

import io.realm.RealmObject
import com.taskworld.android.restfulandroidkotlin.extension.plus
import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient.Action

/**
 * Created by Kittinun Vantasin on 10/28/14.
 */

trait ResourceRouter {

    val extraPathForList: String?
    val extraPathForSingle: String?


    fun <T : RealmObject> getPathForAction(action: Action, clazz: Class<T>): String? {
        return getPathForAction(action, clazz, null)
    }

    fun <T : RealmObject> getPathForAction(action: Action, clazz: Class<T>, args: Map<String, Any>?): String? {
        when(action){
            Action.GET_LIST -> return getPathForListOnResource(clazz, args)
            Action.CREATE -> return getPathForCreateResource(clazz, args)
            Action.GET -> getPathForSingleOnResource(clazz, args)
        }
        return null
    }

    fun <T : RealmObject> getPathForListOnResource(clazz: Class<T>, args: Map<String, Any>?): String
    fun <T : RealmObject> getPathForSingleOnResource(clazz: Class<T>, args: Map<String, Any>?): String
    fun <T : RealmObject> getPathForCreateResource(clazz: Class<T>, args: Map<String, Any>?): String

}

class ResourceRouterImpl private (override val extraPathForList: String?, override val extraPathForSingle: String?) : ResourceRouter {

    val mResourceUrlMap: Map<String, String> = mapOf("playlist" to "list")

    class object {
        fun newInstance() = ResourceRouterImpl(null, null)
        fun newInstance(extraPath: String?) = ResourceRouterImpl(extraPath, null)
        fun newInstance(extraPathForList: String?, extraPathForSingle: String?) = ResourceRouterImpl(extraPathForList, extraPathForSingle)
    }

    override fun <T : RealmObject> getPathForCreateResource(clazz: Class<T>, args: Map<String, Any>?): String {
        return mapToRealPath(clazz)
    }

    override fun <T : RealmObject> getPathForListOnResource(clazz: Class<T>, args: Map<String, Any>?): String {
        return mapToRealPath(clazz)
    }

    override fun <T : RealmObject> getPathForSingleOnResource(clazz: Class<T>, args: Map<String, Any>?): String {
        val idValue = args?.get("id")
        if (idValue == null) throw IllegalArgumentException()

        val builder = StringBuilder(mapToRealPath(clazz))

        return (builder + "/" + idValue.toString()).toString()
    }

    private fun <T : RealmObject> mapToRealPath(clazz: Class<T>): String {
        val resourceName = clazz.getSimpleName().toLowerCase()
        if (mResourceUrlMap.contains(resourceName)) {
            return mResourceUrlMap.get(resourceName)!!
        } else {
            return resourceName
        }
    }
}
