package com.taskworld.android.restfulandroidkotlin.helper

import com.taskworld.android.restfulandroidkotlin.helper.TransactionMessage.DatabaseOpt

/**
 * Created by VerachadW on 10/24/2014 AD.
 */
abstract class TransactionMessage(val opt: DatabaseOpt, val table: String, val key: String) {

    enum class DatabaseOpt {
        INSERT
        UPDATE
        DELETE
    }

}

abstract class InsertMessage(table: String, key: String): TransactionMessage(DatabaseOpt.INSERT, table, key)

abstract class UpdateMessage(table: String, key: String, var changeSet: Map<String, String>): TransactionMessage(DatabaseOpt.UPDATE, table, key)

abstract class DeleteMessage(table: String, key: String): TransactionMessage(DatabaseOpt.DELETE, table, key)
