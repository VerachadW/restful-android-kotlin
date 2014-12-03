package com.taskworld.android.restfulandroidkotlin.network2.action

import io.realm.RealmObject
import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.network2.ModelOpts
import io.realm.RealmResults

abstract class ActionExecutor<MODEL: RealmObject>(val realm: Realm, val modelClass: Class<MODEL>): ModelOpts<MODEL> {

    final override fun find(): MODEL {
        return realm.where(modelClass).findFirst()
    }

    final override fun findAll(): RealmResults<MODEL> {
        return realm.where(modelClass).findAll()
    }

    final override fun create(f: (MODEL) -> Unit): MODEL {
        realm.beginTransaction()
        val model = realm.createObject(modelClass)
        f(model)
        realm.commitTransaction()

        return model
    }

    final override fun update(f: (MODEL) -> MODEL): Boolean {
        realm.beginTransaction()
        val model = find()
        f(model)
        realm.commitTransaction()

        return true
    }

    final override fun delete(): Boolean {
        realm.beginTransaction()
        val model = findAll()
        model.clear()
        realm.commitTransaction()

        return true
    }

}