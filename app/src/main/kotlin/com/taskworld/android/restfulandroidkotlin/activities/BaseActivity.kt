package com.taskworld.android.restfulandroidkotlin.activities

import android.support.v4.app.FragmentActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.events.BaseEvent

/**
 * Created by Kittinun Vantasin on 10/17/14.
 */

abstract class BaseActivity : FragmentActivity() {

    class object {
        public fun newInstance(context: Context): Intent {
            return Intent(context, javaClass<BaseActivity>())
        }
    }

    abstract val mContentLayoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super<FragmentActivity>.onCreate(savedInstanceState)

        setContentView(mContentLayoutResourceId)

        if (savedInstanceState != null) {
            handleSavedInstanceState(savedInstanceState)
        }

        val intentExtras: Bundle? = getIntent().getExtras()
        if (intentExtras != null) {
            handleIntentExtras(intentExtras)
        }

        setUp()
    }

    override fun onResume() {
        super<FragmentActivity>.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super<FragmentActivity>.onPause()
        EventBus.getDefault().unregister(this)
    }

    open fun handleSavedInstanceState(savedInstanceState: Bundle) {
    }

    open fun handleIntentExtras(intentExtras: Bundle) {
    }

    open fun setUp() {
    }

    public fun onEvent(event: BaseEvent) {

    }

    public final fun tag(): String {
        return this.getLocalClassName()
    }
}
