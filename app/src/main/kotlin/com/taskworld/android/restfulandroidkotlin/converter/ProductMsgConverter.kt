package com.taskworld.android.restfulandroidkotlin.converter

import com.taskworld.android.restfulandroidkotlin.helper.message.DeleteMessage
import com.taskworld.android.restfulandroidkotlin.helper.message.UpdateMessage
import com.taskworld.android.restfulandroidkotlin.helper.message.InsertMessage

/**
 * Created by VerachadW on 10/25/2014 AD.
 */
class ProductMsgConverter(): MessageConverter() {
    override fun generateDeleteEndPoint(message: DeleteMessage): String {
        return "Delete Product: ${message.key}"
    }

    override fun generateUpdateEndPoint(message: UpdateMessage): String {
        var changeBuilder: StringBuilder = StringBuilder()
        for (entry in message.changeSet) {
            changeBuilder.append(entry.getKey()).append(":").append(entry.value).append("\n")
        }
        return "Update Product: ${changeBuilder.toString()}"
    }

    override fun generateInsertEndPoint(message: InsertMessage): String {
        return "Insert Product: ${message.key}"
    }

}
