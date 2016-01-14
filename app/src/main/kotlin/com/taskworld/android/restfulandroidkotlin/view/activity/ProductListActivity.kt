package com.taskworld.android.restfulandroidkotlin.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import com.taskworld.android.restfulandroidkotlin.extension.deleteAll
import com.taskworld.android.restfulandroidkotlin.model.Product
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 10/18/14.
 */

class ProductListActivity : BaseActivity() {

    override val mContentLayoutResourceId = R.layout.activity_product_list

    //adapter
    val mProductAdapter: ProductAdapter = ProductAdapter()

    //data
    var mItems by Delegates.observable(arrayListOf<Product>(), { meta, oldItems, newItems ->
        mProductAdapter.clear()
        mProductAdapter.addAll(newItems)
        mProductAdapter.notifyDataSetChanged()
    })

    companion object {
        public fun newInstance(context: Context): Intent {
            return Intent(context, ProductListActivity::class.java)
        }
    }

    override fun setUp() {
        mItems.clear()
        lvProduct.adapter = mProductAdapter
        lvProduct.setOnItemClickListener { adapterView, view, position, id ->
            val selectedProduct = mProductAdapter.getItem(position)
            startActivityForResult(ProductEditActivity.newIntent(this, selectedProduct.name), 999)
        }
        fetchProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.miAddProduct -> navigateToProductEditActivity()
            R.id.miDeleteAll -> {
                val r = Realm.getInstance(this)
                r.deleteAll(Product::class.java)
                fetchProducts()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun navigateToProductEditActivity() {
        startActivityForResult(ProductEditActivity.newIntent(this), 999)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            fetchProducts()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun fetchProducts() {
        mItems = Realm.getInstance(this).where(Product::class.java).findAll().toArrayList()
    }

    inner class ProductAdapter : ArrayAdapter<Product>(this, android.R.layout.simple_expandable_list_item_2, android.R.id.text1, mItems) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view = super.getView(position, convertView, parent)
            val tv1 = view.bindView<TextView>(android.R.id.text1)
            val tv2 = view.bindView<TextView>(android.R.id.text2)
            val product = getItem(position)
            tv1.text = product.name
            tv2.text = "$${product.price.toString()}"
            return view
        }
    }

}
