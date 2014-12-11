package com.taskworld.android.restfulandroidkotlin.extension

import android.view.View

/**
 * Created by Kittinun Vantasin on 10/18/14.
 */

fun <T: View> View.bindView(id: Int): T {
    val view = findViewById(id) ?:
            throw IllegalArgumentException("Given ID $id could not be found in $this!")
    [suppress("unchecked_cast")]
    return view as T
}
