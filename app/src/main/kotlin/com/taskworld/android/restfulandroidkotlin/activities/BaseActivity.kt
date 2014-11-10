package com.taskworld.android.restfulandroidkotlin.activities

import android.os.Bundle
import android.content.Context
import android.content.Intent
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.events.BaseEvent
import android.support.v7.app.ActionBarActivity
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.tag

/**
 * Created by Kittinun Vantasin on 10/17/14.
 */

abstract class BaseActivity : ActionBarActivity() {

    abstract val mContentLayoutResourceId: Int

    //static instantiate
    class object {
        public fun newInstance(context: Context): Intent {
            return Intent(context, javaClass<BaseActivity>())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<ActionBarActivity>.onCreate(savedInstanceState)

        setContentView(mContentLayoutResourceId)

        if (savedInstanceState != null) {
            handleSavedInstanceState(savedInstanceState)
        }

        val intentExtras = getIntent().getExtras()
        if (intentExtras != null) {
            handleIntentExtras(intentExtras)
        }

        setUp()
        Log.v(tag(), tag() + "::onCreate()")
    }

    override fun onResume() {
        super<ActionBarActivity>.onResume()
        EventBus.getDefault().register(this)
        Log.v(tag(), tag() + "::onResume()")
    }

    override fun onPause() {
        super<ActionBarActivity>.onPause()
        EventBus.getDefault().unregister(this)
        Log.v(tag(), tag() + "::onPause()")
    }

    override fun onDestroy() {
        Log.v(tag(), tag() + "::onDestory()")
        super<ActionBarActivity>.onDestroy()
    }

    open fun handleSavedInstanceState(savedInstanceState: Bundle) {
    }

    open fun handleIntentExtras(intentExtras: Bundle) {
    }

    open fun setUp() {
    }

    public fun onEvent(event: BaseEvent) {
    }
}
