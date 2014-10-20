package com.taskworld.android.restfulandroidkotlin.activities

import com.taskworld.android.restfulandroidkotlin.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.findView
import android.widget.ListView
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import android.widget.ArrayAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.taskworld.android.restfulandroidkotlin.Product
import android.app.Activity
import com.taskworld.android.restfulandroidkotlin.extensions.TAG
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.deleteAll

/**
 * Created by Kittinun Vantasin on 10/18/14.
 */

class ProductListActivity : BaseActivity() {

    override val mContentLayoutResourceId = R.layout.activity_list_product

    //widgets
    val lvProduct by Delegates.lazy { findView<ListView>(R.id.lvProduct) }

    //adapter
    val mProductAdapter: ProductAdapter by Delegates.lazy { ProductAdapter() }

    //data
    var mItems by Delegates.observable(listOf<Product>(), {
        meta, oldItems, newItems ->
        Log.i(TAG(), "${oldItems.size} -> ${newItems.size}")
        mProductAdapter.clear()
        mProductAdapter.addAll(newItems)
        mProductAdapter.notifyDataSetChanged()
    })

    class object {
        public fun newInstance(context: Context): Intent {
            return Intent(context, javaClass<ProductListActivity>())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseActivity>.onCreate(savedInstanceState)

        lvProduct.setAdapter(mProductAdapter)
        lvProduct.setOnItemClickListener { (adapterView, view, position, id) ->
            val selectedProduct = mProductAdapter.getItem(position)
            startActivityForResult(ProductEditActivity.newInstance(this, selectedProduct.getName()), 999)
        }
        fetchProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_list_product, menu)
        return super<BaseActivity>.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.miAddProduct -> navigateToProductEditActivity()
            R.id.miDeleteAll -> {
                val r = Realm.getInstance(this)
                r.deleteAll(javaClass<Product>())
                fetchProducts()
            }
        }
        return super<BaseActivity>.onOptionsItemSelected(item)
    }

    fun navigateToProductEditActivity() {
        startActivityForResult(ProductEditActivity.newInstance(this), 999)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            fetchProducts()
        }
        super<BaseActivity>.onActivityResult(requestCode, resultCode, data)
    }

    fun fetchProducts() {
        mItems = Realm.getInstance(this).where(javaClass<Product>()).findAll()
    }

    inner class ProductAdapter : ArrayAdapter<Product>(this,
            android.R.layout.simple_expandable_list_item_2, android.R.id.text1, mItems) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view = super<ArrayAdapter>.getView(position, convertView, parent)
            val tv1 = view.findView<TextView>(android.R.id.text1)
            val tv2 = view.findView<TextView>(android.R.id.text2)
            val product = getItem(position)
            tv1.setText(product.getName())
            tv2.setText("$${product.getPrice().toString()}")
            return view
        }
    }

}
