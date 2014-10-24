package com.taskworld.android.restfulandroidkotlin.extensions

/**
 * Created by Kittinun Vantasin on 10/18/14.
 */

fun <T> T.tag(): String {
    return javaClass.getSimpleName()
}

