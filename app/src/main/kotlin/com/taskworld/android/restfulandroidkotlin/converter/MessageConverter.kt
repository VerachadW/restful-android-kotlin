package com.taskworld.android.restfulandroidkotlin.converter

import com.taskworld.android.restfulandroidkotlin.extensions.tag
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.helper.message.InsertMessage
import com.taskworld.android.restfulandroidkotlin.helper.message.TransactionMessage
import com.taskworld.android.restfulandroidkotlin.helper.message.DeleteMessage
import com.taskworld.android.restfulandroidkotlin.helper.message.UpdateMessage

/**
 * Created by VerachadW on 10/24/2014 AD.
 */
abstract class MessageConverter() {

    public fun convertTransactionToEndPoint(message: TransactionMessage): String{
       val endpoint: String

       when (message.opt) {
           TransactionMessage.DatabaseOpt.INSERT -> endpoint = generateInsertEndPoint(message as InsertMessage)
           TransactionMessage.DatabaseOpt.UPDATE -> endpoint = generateUpdateEndPoint(message as UpdateMessage)
           TransactionMessage.DatabaseOpt.DELETE -> endpoint = generateDeleteEndPoint(message as DeleteMessage)
           else -> throw IllegalStateException()
       }

       return endpoint
    }

    abstract fun generateInsertEndPoint(message: InsertMessage): String
    abstract fun generateUpdateEndPoint(message: UpdateMessage): String
    abstract fun generateDeleteEndPoint(message: DeleteMessage): String

}

