package com.taskworld.android.restfulandroidkotlin.activities

import com.taskworld.android.restfulandroidkotlin.R
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.content.Context
import android.os.Bundle
import io.realm.Realm
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.EditText
import android.widget.Button
import android.app.Activity
import com.taskworld.android.restfulandroidkotlin.extensions.delete
import com.taskworld.android.restfulandroidkotlin.model.Product

/**
 * Created by Kittinun Vantasin on 10/20/14.
 */

class ProductEditActivity : BaseActivity() {

    override val mContentLayoutResourceId = R.layout.activity_product_edit

    //widgets
    val etName by Delegates.lazy { bindView<EditText>(R.id.etName) }
    val etPrice by Delegates.lazy { bindView<EditText>(R.id.etPrice) }
    val btDelete by Delegates.lazy { bindView<Button>(R.id.btDelete) }

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
        mProductName = etName.getText().toString()
        val price = etPrice.getText().toString().toInt()
        setResult(Activity.RESULT_OK)
        finish()
    }

    fun deleteProduct() {
        if (mProductName == null) return
        setResult(Activity.RESULT_OK)
        finish()
    }
}
