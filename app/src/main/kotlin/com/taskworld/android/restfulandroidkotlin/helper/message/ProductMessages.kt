package com.taskworld.android.restfulandroidkotlin.helper.message

import com.taskworld.android.restfulandroidkotlin.helper.InsertMessage
import com.taskworld.android.restfulandroidkotlin.helper.UpdateMessage
import com.taskworld.android.restfulandroidkotlin.helper.DeleteMessage
import com.taskworld.android.restfulandroidkotlin.Product

/**
 * Created by VerachadW on 10/24/2014 AD.
 */

val TABLE_NAME = javaClass<Product>().getSimpleName()

class InsertProductMessage(key: String): InsertMessage(TABLE_NAME, key)

class UpdateProductMessage(key: String, changeSet: Map<String, String>): UpdateMessage(TABLE_NAME, key, changeSet)

class DeleteProductMessage(key: String): DeleteMessage(TABLE_NAME, key)

