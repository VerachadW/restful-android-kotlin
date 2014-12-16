package com.taskworld.android.restfulandroidkotlin.network

import io.realm.RealmObject
import io.realm.RealmResults

/**
 * Created by VerachadW on 12/1/14.
 */
trait ModelOpts<MODEL: RealmObject> {

    fun find(): MODEL
    fun findAll(): RealmResults<MODEL>

    fun create(f: (MODEL) -> Unit): MODEL
    fun update(f: (MODEL) -> MODEL): Boolean
    fun delete(): Boolean
}
