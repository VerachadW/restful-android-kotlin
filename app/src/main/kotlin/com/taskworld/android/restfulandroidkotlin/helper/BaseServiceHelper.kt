package com.taskworld.android.restfulandroidkotlin.helper

import io.realm.RealmObject
import android.content.Context
import io.realm.Realm
import kotlin.properties.Delegates
import android.util.Log

abstract class BaseServiceHelper<T: RealmObject>(var ctx: Context, var clazz: Class<T>) {

    val realmDatabase: Realm by Delegates.lazy {
        Realm.getInstance(ctx)
    }

    abstract val transactionConverter: MessageConverter

    fun addTransactionMessage(message: TransactionMessage) {
        val endpoint = transactionConverter.convertTransactionToEndPoint(message)
        Log.d("ServiceHelper", endpoint)
    }
}