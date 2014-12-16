package com.taskworld.android.restfulandroidkotlin.network.action

import io.realm.RealmObject
import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.model.Movie

/**
 * Created by VerachadW on 12/2/14.
 */
class ActionExecutorFactory {

    class object {
        fun createExecutor(realm: Realm, clazz: Class<Movie>): MovieActionExecutor {
            return MovieActionExecutor(realm, clazz)
        }
    }

}
