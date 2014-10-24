package com.taskworld.android.restfulandroidkotlin.helper

import com.taskworld.android.restfulandroidkotlin.helper.TransactionMessage.DatabaseOpt

/**
 * Created by VerachadW on 10/24/2014 AD.
 */
abstract class TransactionMessage(var opt: DatabaseOpt, var table: String, var key: String) {

    enum class DatabaseOpt {
        INSERT
        UPDATE
        DELETE
    }

}
