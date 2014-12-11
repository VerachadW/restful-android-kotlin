package com.taskworld.android.restfulandroidkotlin.extension

import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

fun Fragment.toast(text: String?): Unit {
    if (text == null) return
    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show()
}
