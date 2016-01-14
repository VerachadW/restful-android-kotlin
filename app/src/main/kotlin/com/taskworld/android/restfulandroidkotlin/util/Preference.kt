package com.taskworld.android.restfulandroidkotlin.util

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object Preference {

    private val NAME = "movie"

    private var mSharedPreferences: SharedPreferences? = null

    //
    var username: String? by PreferenceDelegate()
    var sessionId: String? by PreferenceDelegate()

    fun with(context: Context): Preference {
        mSharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return this
    }

    class PreferenceDelegate<T>() : ReadWriteProperty<Any?, T> {
        private var value = "" as T

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {

            if (mSharedPreferences == null) throw NullPointerException("Preference.with() must be called before acessing properties")

            when (value) {
                is String -> value = mSharedPreferences!!.getString(property.name, null) as T
                is Float -> value = mSharedPreferences!!.getFloat(property.name, 0.0f) as T
                is Int -> value = mSharedPreferences!!.getInt(property.name, 0) as T
                is Boolean -> value = mSharedPreferences!!.getBoolean(property.name, false) as T
                is Long -> value = mSharedPreferences!!.getLong(property.name, 0L) as T
                else -> throw IllegalArgumentException("${property.name} variable type is not supported yet!!")
            }
            return value
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {

            if (mSharedPreferences == null) throw NullPointerException("Preference.with() must be called before acessing properties")

            val editor = mSharedPreferences!!.edit()
            when (value) {
                is String -> editor.putString(property.name, value)
                is Float -> editor.putFloat(property.name, value)
                is Int -> editor.putInt(property.name, value)
                is Boolean -> editor.putBoolean(property.name, value)
                is Long -> editor.putLong(property.name, value)
                else -> throw IllegalArgumentException(" variable type is not supported yet!!")
            }
            this.value = value
            editor.apply()
        }
    }
}