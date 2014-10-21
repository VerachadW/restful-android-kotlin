package com.taskworld.android.restfulandroidkotlin.extensions

import android.view.View

/**
 * Created by Kittinun Vantasin on 10/18/14.
 */

fun <T> View.findView(id: Int): T {
    val view = findViewById(id)
    if (view == null) throw IllegalArgumentException("Given ID $id could not be found in $this!")
    return view as T
}
