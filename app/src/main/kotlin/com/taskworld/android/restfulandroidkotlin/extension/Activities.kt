package com.taskworld.android.restfulandroidkotlin.extension

import android.app.Activity
import android.view.View
import android.widget.Toast

/**
 * Created by Kittinun Vantasin on 10/17/14.
 */

fun <T : View> Activity.bindView(id: Int): T {
    val view = findViewById(id) ?:
            throw IllegalArgumentException("Given ID $id could not be found in $this!")
    return view as T
}

fun Activity.toast(text: String?) {
    if (text == null) return
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}