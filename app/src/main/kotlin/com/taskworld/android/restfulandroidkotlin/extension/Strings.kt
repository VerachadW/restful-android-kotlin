package com.taskworld.android.restfulandroidkotlin.extension

/**
 * Created by Kittinun Vantasin on 10/29/14.
 */

fun String.toStartingLetterUppercase(): String {
    if (this.isEmpty()) return this

    var firstChar = this.charAt(0)
    val valueOfFirstChar = firstChar.toInt()

    if (valueOfFirstChar in 97..122) {
        firstChar = (valueOfFirstChar - 32).toChar()
    }

    val builder = StringBuilder()

    var i = 0
    for (ch in this) {
        if (i == 0) {
            builder.append(firstChar)
        } else {
            builder.append(ch)
        }
        i++
    }

    return builder.toString()
}
