package com.taskworld.android.restfulandroidkotlin.extensions

import android.app.Activity
import android.view.View
import android.widget.Toast

/**
 * Created by Kittinun Vantasin on 10/17/14.
 */

fun <T> Activity.findView(id: Int) : T {
    val view: View? = findViewById(id)
    if (view == null) throw IllegalArgumentException("Given ID $id could not be found in $this!")
    return view as T
}

fun Activity.toast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}