package com.taskworld.android.restfulandroidkotlin.extension

import io.realm.Realm
import io.realm.RealmObject

/**
 * Created by Kittinun Vantasin on 10/20/14.
 */

fun <T: RealmObject> Realm.createOrUpdate(clazz: Class<T>, keyValue: Pair<String, Int>, createBlock: (clazz: Class<T>) -> Unit, updateBlock: (clazz: Class<T>, it: T) -> Unit) {
    beginTransaction()
    var realmObject = where(clazz).equalTo(keyValue.first, keyValue.second).findFirst()

    if (realmObject != null) {
        updateBlock(clazz, realmObject)
    } else {
        createBlock(clazz)
    }
    commitTransaction()
}
