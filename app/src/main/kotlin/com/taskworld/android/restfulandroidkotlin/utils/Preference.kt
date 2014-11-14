package com.taskworld.android.restfulandroidkotlin.utils

class Preference private () {

    class object {
        private var mSharedPreferences: android.content.SharedPreferences by kotlin.properties.Delegates.notNull()
        private val NAME = "movie"
        private val SESSION_ID = "session_id"
        private var mInstance: Preference? = null

        fun getInstance(context: android.content.Context): Preference {
            if (mInstance == null) {
                mInstance = Preference()
                mSharedPreferences = context.getSharedPreferences(NAME, android.content.Context.MODE_PRIVATE)
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