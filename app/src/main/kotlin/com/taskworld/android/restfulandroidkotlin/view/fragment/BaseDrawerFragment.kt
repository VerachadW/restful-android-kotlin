package com.taskworld.android.restfulandroidkotlin.view.fragment

import android.content.res.Configuration
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.taskworld.android.restfulandroidkotlin.R

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

abstract class BaseDrawerFragment : BaseFragment() {

    enum class Direction {
        LEFT,
        RIGHT
    }

    //drawer specific
    var dlDrawer: DrawerLayout? = null
    var mDrawerToggle: ActionBarDrawerToggle? = null

    var mDrawerGravity = GravityCompat.START
    var mDirection: Direction = Direction.LEFT

    public fun setUpAsLeftDrawer(drawerLayout: DrawerLayout, toolbar: Toolbar) {
        setUpDrawer(Direction.LEFT, drawerLayout, toolbar)
    }

    fun setUpDrawer(direction: Direction, drawerLayout: DrawerLayout, toolBar: Toolbar) {
        mDirection = direction
        dlDrawer = drawerLayout

        //        dlDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START)
        mDrawerToggle = object : ActionBarDrawerToggle(
                activity,
                dlDrawer,
                toolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                if (!isAdded) return

                activity.invalidateOptionsMenu()

                onDrawerClosed()
            }

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                if (!isAdded) return

                activity.invalidateOptionsMenu()
                onDrawerOpened()
            }
        }

        dlDrawer?.post { mDrawerToggle?.syncState() }
        dlDrawer?.setDrawerListener(mDrawerToggle)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        mDrawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (mDrawerToggle?.onOptionsItemSelected(item) as Boolean) {
            return true;
        }

        return super.onOptionsItemSelected(item)
    }

    fun closeDrawer() {
        dlDrawer?.closeDrawer(mDrawerGravity)
    }

    open fun onDrawerClosed() {
    }

    fun openDrawer() {
        dlDrawer?.openDrawer(mDrawerGravity)
    }

    open fun onDrawerOpened() {
    }

    fun toggleDrawer() {
        if (dlDrawer!!.isDrawerOpen(mDrawerGravity)) {
            closeDrawer()
        } else {
            openDrawer()
        }
    }
}
