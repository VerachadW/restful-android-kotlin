package com.taskworld.android.restfulandroidkotlin.fragments

import android.view.View
import kotlin.properties.Delegates
import android.widget.ListView
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.ArrayAdapter
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.content.res.Configuration
import android.view.MenuItem
import com.taskworld.android.restfulandroidkotlin.R

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

class MainNavigationDrawerFragment : BaseDrawerFragment() {

    var mCurrentSelectedPosition = 0

    //widgets
    val lvDrawer by Delegates.lazy { getRootView()!!.bindView<ListView>(R.id.lvMainNavigation) }

    override val mContentLayoutResourceId: Int = R.layout.fragment_main_navigation_drawer

    override fun setUp() {
    }

    override fun setUpUI(view: View?) {
        lvDrawer.setOnItemClickListener { parent, view, position, id -> selectItem(position) }
        lvDrawer.setAdapter(ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                listOf("1", "2", "3")))
        lvDrawer.setItemChecked(mCurrentSelectedPosition, true)
    }

    fun selectItem(position: Int) {
        mCurrentSelectedPosition = position

        lvDrawer.setItemChecked(position, true)
        closeDrawer()
    }

}

