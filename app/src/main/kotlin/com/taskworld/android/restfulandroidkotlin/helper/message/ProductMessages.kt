package com.taskworld.android.restfulandroidkotlin.helper.message

import com.taskworld.android.restfulandroidkotlin.model.Product

/**
 * Created by VerachadW on 10/24/2014 AD.
 */


class InsertProductMessage(key: String): InsertMessage(Product.TABLE_NAME, key)

class UpdateProductMessage(key: String, changeSet: Map<String, String>): UpdateMessage(Product.TABLE_NAME, key, changeSet)

class DeleteProductMessage(key: String): DeleteMessage(Product.TABLE_NAME, key)

