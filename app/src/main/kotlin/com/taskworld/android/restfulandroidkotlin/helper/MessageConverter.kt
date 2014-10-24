package com.taskworld.android.restfulandroidkotlin.helper

/**
 * Created by VerachadW on 10/24/2014 AD.
 */
abstract class MessageConverter() {

    public fun convertTransactionToEndPoint(message: TransactionMessage): String{
       val endpoint: String

       when (message.opt) {
           TransactionMessage.DatabaseOpt.INSERT -> endpoint = generateInsertEndPoint(message)
           else -> endpoint = "Error"

       }

       return endpoint
    }

    abstract fun generateInsertEndPoint(message: TransactionMessage): String

}

class ProductConverter(): MessageConverter() {

    override fun generateInsertEndPoint(message: TransactionMessage): String {
        return "INSERT new Product : ${message.key}"
    }

}
