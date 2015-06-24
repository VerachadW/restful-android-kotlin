package com.taskworld.android.restfulandroidkotlin.view.activity

import android.os.Bundle
import android.content.Context
import android.content.Intent
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.event.BaseEvent
import android.support.v7.app.ActionBarActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extension.tag
import android.view.View

/**
 * Created by Kittinun Vantasin on 10/17/14.
 */

abstract class BaseActivity : AppCompatActivity() {

    abstract val mContentLayoutResourceId: Int

    //static instantiate
    companion object {
        public fun newIntent(context: Context): Intent {
            return Intent(context, javaClass<BaseActivity>())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (mContentLayoutResourceId != 0) {
            setContentView(mContentLayoutResourceId)
        } else {
            setContentView(createContentView())
        }

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

    open fun createContentView(): View {
        throw UnsupportedOperationException("If mContentLayoutResourceId == 0, createContentView() must be implemented by subclass")
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
        Log.v(tag(), tag() + "::onResume()")
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
        Log.v(tag(), tag() + "::onPause()")
    }

    override fun onDestroy() {
        Log.v(tag(), tag() + "::onDestory()")
        super.onDestroy()
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
