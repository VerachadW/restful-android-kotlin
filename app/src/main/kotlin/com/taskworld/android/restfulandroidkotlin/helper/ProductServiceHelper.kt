package com.taskworld.android.restfulandroidkotlin.helper

import com.taskworld.android.restfulandroidkotlin.Product
import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.extensions.create
import android.content.Context
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.update
import com.taskworld.android.restfulandroidkotlin.helper.message.InsertProductMessage
import com.taskworld.android.restfulandroidkotlin.helper.message.UpdateProductMessage
import com.taskworld.android.restfulandroidkotlin.extensions.delete
import com.taskworld.android.restfulandroidkotlin.helper.message.DeleteProductMessage

/**
 * Created by VerachadW on 10/23/2014 AD.
 */

class ProductServiceHelper(ctx: Context, clazz: Class<Product>): BaseServiceHelper<Product>(ctx, clazz) {
    override val transactionConverter: MessageConverter = ProductConverter()

    public fun createProduct(name: String, price: Int): Product{
        var product = realmDatabase.create(clazz, {product ->
            product.setName(name)
            product.setPrice(price)
        })
        val message = InsertProductMessage(name)
        addTransactionMessage(message)
        return product
    }

    public fun updateProduct(key: String, newPrice: Int): Product{
        var (newProduct, changeSet) = realmDatabase.update(clazz, "name", key, {product, changes ->

            product.setPrice(newPrice)
            changes.put(Product.Field.price.name(), newPrice.toString())
        })
        val message = UpdateProductMessage(key, changeSet)
        addTransactionMessage(message)
        return newProduct
    }

    public fun deleteProduct(key: String) {
        realmDatabase.delete(clazz, "name", key)
        val message = DeleteProductMessage(key)
        addTransactionMessage(message)
    }


}
