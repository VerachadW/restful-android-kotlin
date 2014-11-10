package com.taskworld.android.restfulandroidkotlin.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import com.taskworld.android.restfulandroidkotlin.events.BaseEvent
import de.greenrobot.event.EventBus
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import kotlin.properties.Delegates
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.tag

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

abstract class BaseFragment : Fragment() {

    abstract val mContentLayoutResourceId: Int

    //widgets
    var vRoot: View by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Fragment>.onCreate(savedInstanceState)

        //handle savedInstanceState
        if (savedInstanceState != null) {
            handleSavedInstanceState(savedInstanceState)
        }

        //handle arguments
        val args = getArguments();
        if (args != null) {
            handleArguments(args)
        }

        setUp()
        Log.v(tag(), tag() + "::onCreate()")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vRoot = inflater!!.inflate(mContentLayoutResourceId, container, false)
        setUpUI(vRoot)
        Log.v(tag(), tag() + "::onCreateView()")
        return vRoot
    }

    override fun onResume() {
        super<Fragment>.onResume()
        EventBus.getDefault().register(this)
        Log.v(tag(), tag() + "::onResume()")
    }

    override fun onPause() {
        super<Fragment>.onPause()
        EventBus.getDefault().unregister(this)
        Log.v(tag(), tag() + "::onPause()")
    }

    override fun onDestroy() {
        Log.v(tag(), tag() + "::onDestory()")
        super<Fragment>.onDestroy()
    }

    open fun handleSavedInstanceState(savedInstanceState: Bundle) {
    }

    open fun handleArguments(args: Bundle) {
    }

    open fun setUp() {
    }

    open fun setUpUI(view: View) {
    }

    fun getRootView(): View {
        return vRoot
    }

    public fun onEvent(event: BaseEvent) {
    }
}
