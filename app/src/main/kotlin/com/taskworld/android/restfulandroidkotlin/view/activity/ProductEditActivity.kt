package com.taskworld.android.restfulandroidkotlin.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.model.Product
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_product_edit.*

/**
 * Created by Kittinun Vantasin on 10/20/14.
 */

class ProductEditActivity : BaseActivity() {

    override val mContentLayoutResourceId = R.layout.activity_product_edit

    //data
    var mProductName: String? = null
    var mProduct: Product? = null

    companion object {
        val ARG_PRODUCT_NAME = "product_name"

        public fun newIntent(context: Context): Intent {
            return Intent(context, ProductEditActivity::class.java)
        }

        public fun newIntent(context: Context, productName: String): Intent {
            var intent = Intent(context, ProductEditActivity::class.java)
            intent.putExtra(ARG_PRODUCT_NAME, productName)
            return intent
        }
    }

    override fun setUp() {
        if (mProductName == null) return

        val realm = Realm.getInstance(this)
        mProduct = realm.where(Product::class.java).equalTo(Product.Field.name.toString(), mProductName).findFirst()

        btDelete.setOnClickListener { view -> deleteProduct() }

        etName.setText(mProduct!!.name)
        etPrice.setText(mProduct!!.price.toString())
    }

    override fun handleIntentExtras(intentExtras: Bundle) {
        mProductName = intentExtras.getString(ARG_PRODUCT_NAME)
        super.handleIntentExtras(intentExtras)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.miSave -> saveProduct()
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveProduct() {
        mProductName = etName.text.toString()
        val price = etPrice.text.toString().toInt()
        setResult(Activity.RESULT_OK)
        finish()
    }

    fun deleteProduct() {
        if (mProductName == null) return
        setResult(Activity.RESULT_OK)
        finish()
    }
}
