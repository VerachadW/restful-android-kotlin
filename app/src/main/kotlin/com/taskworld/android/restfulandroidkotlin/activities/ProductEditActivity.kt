package com.taskworld.android.restfulandroidkotlin.activities

import com.taskworld.android.restfulandroidkotlin.R
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.content.Context
import android.os.Bundle
import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.Product
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.findView
import android.widget.EditText
import android.widget.Button
import com.taskworld.android.restfulandroidkotlin.extensions.create
import com.taskworld.android.restfulandroidkotlin.extensions.update
import android.app.Activity

/**
 * Created by Kittinun Vantasin on 10/20/14.
 */

class ProductEditActivity : BaseActivity() {

    override val mContentLayoutResourceId = R.layout.activity_product_edit

    //widgets
    val etName by Delegates.lazy { findView<EditText>(R.id.etName) }
    val etPrice by Delegates.lazy { findView<EditText>(R.id.etPrice) }
    val btDelete by Delegates.lazy { findView<Button>(R.id.btDelete) }

    //data
    var mProductName: String? = null
    var mProduct: Product? = null

    class object {
        val ARG_PRODUCT_NAME = "product_name"

        public fun newInstance(context: Context): Intent {
            return Intent(context, javaClass<ProductEditActivity>())
        }

        public fun newInstance(context: Context, productName: String): Intent {
            var intent = Intent(context, javaClass<ProductEditActivity>())
            intent.putExtra(ARG_PRODUCT_NAME, productName)
            return intent
        }
    }

    override fun setUp() {
        if (mProductName == null) return

        val realm = Realm.getInstance(this)
        mProduct = realm.where(javaClass<Product>()).equalTo(Product.Field.name.toString(), mProductName).findFirst()

        btDelete.setOnClickListener { view -> deleteProduct() }

        etName.setText(mProduct!!.getName())
        etPrice.setText(mProduct!!.getPrice().toString())
    }

    override fun handleIntentExtras(intentExtras: Bundle) {
        mProductName = intentExtras.getString(ARG_PRODUCT_NAME)
        super<BaseActivity>.handleIntentExtras(intentExtras)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_product, menu)
        return super<BaseActivity>.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.miSave -> saveProduct()
        }
        return super<BaseActivity>.onOptionsItemSelected(item)
    }

    fun saveProduct() {
        val r = Realm.getInstance(this)
        if (mProduct == null) {
            r.create(javaClass<Product>(), { it ->
                it.setName(etName.getText().toString())
                it.setPrice(etPrice.getText().toString().toInt())
            })
        } else {
            r.update(javaClass<Product>(), Product.Field.name.toString(), mProductName!!, { it ->
                it.setName(etName.getText().toString())
                it.setPrice(etPrice.getText().toString().toInt())
            })
        }
        setResult(Activity.RESULT_OK)
        finish()
    }

    fun deleteProduct() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
