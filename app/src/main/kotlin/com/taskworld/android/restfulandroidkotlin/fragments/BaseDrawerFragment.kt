package com.taskworld.android.restfulandroidkotlin.fragments

import android.view.View
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import com.taskworld.android.restfulandroidkotlin.R
import android.view.MenuItem
import android.content.res.Configuration
import android.support.v4.view.GravityCompat

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

abstract class BaseDrawerFragment : BaseFragment() {

    enum class Direction {
        LEFT
        RIGHT
    }

    //drawer specific
    var dlDrawer: DrawerLayout? = null
    var mDrawerToggle: ActionBarDrawerToggle? = null

    var mDirection: Direction = Direction.LEFT

    public fun setUpAsLeftDrawer(drawerLayout: DrawerLayout, toolbar: Toolbar) {
        setUpDrawer(Direction.LEFT, drawerLayout, toolbar)
    }

    public fun setUpAsRightDrawer(drawerLayout: DrawerLayout, toolbar: Toolbar) {
        setUpDrawer(Direction.RIGHT, drawerLayout, toolbar)
    }

    fun setUpDrawer(direction: Direction, drawerLayout: DrawerLayout, toolBar: Toolbar) {
        mDirection = direction
        dlDrawer = drawerLayout

//        dlDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START)
        mDrawerToggle = object : ActionBarDrawerToggle(
                getActivity(),
                dlDrawer,
                toolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            override fun onDrawerClosed(drawerView: View?) {
                super<ActionBarDrawerToggle>.onDrawerClosed(drawerView)
                if (!isAdded()) return

                getActivity().invalidateOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View?) {
                super<ActionBarDrawerToggle>.onDrawerOpened(drawerView)
                if (!isAdded()) return

                getActivity().invalidateOptionsMenu()
            }
        }

        dlDrawer?.post { mDrawerToggle?.syncState() }
        dlDrawer?.setDrawerListener(mDrawerToggle)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super<BaseFragment>.onConfigurationChanged(newConfig)

        mDrawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (mDrawerToggle?.onOptionsItemSelected(item) as Boolean) {
            return true;
        }

        return super<BaseFragment>.onOptionsItemSelected(item)
    }

    fun closeDrawer() {
        var gravity = GravityCompat.START
        when (mDirection) {
            is Direction.LEFT -> gravity = GravityCompat.START
            is Direction.RIGHT -> gravity = GravityCompat.END
        }
        dlDrawer?.closeDrawer(gravity)
    }

    fun openDrawer() {
        var gravity = GravityCompat.START
        when (mDirection) {
            is Direction.LEFT -> gravity = GravityCompat.START
            is Direction.RIGHT -> gravity = GravityCompat.END
        }
        dlDrawer?.openDrawer(gravity)
    }

}
