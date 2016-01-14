package com.taskworld.android.restfulandroidkotlin.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taskworld.android.restfulandroidkotlin.event.BaseEvent
import de.greenrobot.event.EventBus
import kotlin.properties.Delegates

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
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vRoot = inflater!!.inflate(mContentLayoutResourceId, container, false)
        setUpUI(vRoot)
        return vRoot
    }

    override fun onResume() {
        super<Fragment>.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super<Fragment>.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
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
