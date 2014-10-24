package com.taskworld.android.restfulandroidkotlin.helper

import com.taskworld.android.restfulandroidkotlin.Product
import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.extensions.create
import android.content.Context
import com.taskworld.android.restfulandroidkotlin.extensions.updateOrCreate
import android.util.Log

/**
 * Created by VerachadW on 10/23/2014 AD.
 */

class ProductHelper(ctx: Context, clazz: Class<Product>): BaseServiceHelper<Product>(ctx, clazz) {
    override val transactionConverter: MessageConverter = ProductConverter()

    public fun createProduct(name: String, price: Int) {
        realmDatabase.beginTransaction()
        val product = realmDatabase.createObject(clazz)
        product.setName(name)
        product.setPrice(price)
        realmDatabase.commitTransaction()
        val message = ProductMessage(TransactionMessage.DatabaseOpt.INSERT, name)
        addTransactionMessage(message)
    }


}
