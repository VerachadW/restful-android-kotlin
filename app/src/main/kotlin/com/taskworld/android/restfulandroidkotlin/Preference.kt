package com.taskworld.android.restfulandroidkotlin

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.Delegates

/**
 * Created by VerachadW on 11/4/14.
 */

class Preference private (){

    class object {
        private var mSharedPreferences: SharedPreferences by Delegates.notNull()
        private val NAME = "movie"
        private val SESSION_ID = "session_id"
        private var mInstance: Preference? = null

        fun getInstance(context: Context): Preference {
            if (mInstance == null) {
                mInstance = Preference()
                mSharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            }
            return mInstance!!
        }
    }


    public fun setSessionId(session: String) {
        val editor = mSharedPreferences.edit()
        editor.putString(SESSION_ID, session)
        editor.apply()
    }

    public fun getSessionId(): String? {
        return mSharedPreferences.getString(SESSION_ID, null)
    }
}
