package com.taskworld.android.restfulandroidkotlin.util

import kotlin.properties.Delegates
import android.content.SharedPreferences
import android.content.Context
import kotlin.properties.ReadWriteProperty

object Preference {

    private val NAME = "movie"

    private var mSharedPreferences: SharedPreferences?  = null

    //
    var username: String? by PreferenceDelegate()
    var sessionId: String? by PreferenceDelegate()

    fun with(context: Context): Preference {
        mSharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return this
    }

    class PreferenceDelegate<T>() : ReadWriteProperty<Any?, T> {
        private var value: T = null

        override fun get(thisRef: Any?, desc: PropertyMetadata): T {

            if (mSharedPreferences == null) throw NullPointerException("Preference.with() must be called before acessing properties")

            [suppress("unchecked_cast")]
            when (value) {
                is String -> value = mSharedPreferences!!.getString(desc.name, null) as T
                is Float -> value = mSharedPreferences!!.getFloat(desc.name, 0.0f) as T
                is Int -> value = mSharedPreferences!!.getInt(desc.name, 0) as T
                is Boolean -> value = mSharedPreferences!!.getBoolean(desc.name, false) as T
                is Long -> value = mSharedPreferences!!.getLong(desc.name, 0L) as T
                else -> throw IllegalArgumentException("${desc.name} variable type is not supported yet!!")
            }
            return value
        }

        override fun set(thisRef: Any?, desc: PropertyMetadata, value: T) {

            if (mSharedPreferences == null) throw NullPointerException("Preference.with() must be called before acessing properties")

            val editor = mSharedPreferences!!.edit()
            when (value) {
                is String -> editor.putString(desc.name, value)
                is Float -> editor.putFloat(desc.name, value)
                is Int -> editor.putInt(desc.name, value)
                is Boolean -> editor.putBoolean(desc.name, value)
                is Long -> editor.putLong(desc.name, value)
                else -> throw IllegalArgumentException(" variable type is not supported yet!!")
            }
            this.value = value
            editor.apply()
        }
    }
}