package com.taskworld.android.restfulandroidkotlin.extensions

import io.realm.Realm
import io.realm.RealmObject

/**
 * Created by Kittinun Vantasin on 10/20/14.
 */

fun <T : RealmObject> Realm.create(clazz: Class<T>, f: (it: T) -> Unit): T {
    beginTransaction()
    var realmObject = createObject(clazz)
    f(realmObject)
    commitTransaction()
    return realmObject
}

fun <T: RealmObject> Realm.update(clazz: Class<T>, key: String, value: String, f: (it: T) -> Unit): T {
    beginTransaction()
    var realmObject = where(clazz).equalTo(key, value).findFirst()
    f(realmObject)
    commitTransaction()
    return realmObject
}

