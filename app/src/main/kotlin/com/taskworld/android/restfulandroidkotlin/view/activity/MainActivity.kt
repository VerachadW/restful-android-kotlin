package com.taskworld.android.restfulandroidkotlin.view.activity

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import com.taskworld.android.restfulandroidkotlin.view.fragment.BaseDrawerFragment
import com.taskworld.android.restfulandroidkotlin.event.OnDrawerToggledEvent
import com.taskworld.android.restfulandroidkotlin.view.fragment.BaseDrawerFragment.Direction
import android.widget.TextView
import com.taskworld.android.restfulandroidkotlin.event.OnToolbarTitleChangedEvent
import com.taskworld.android.restfulandroidkotlin.view.fragment.MovieFragment
import android.content.Context
import android.content.Intent

class MainActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId = R.layout.activity_main

    //widgets
    val tvBarTitle by Delegates.lazy { bindView<TextView>(R.id.tvBarTitle) }

    val fgLeftNavigationDrawer by Delegates.lazy {
        getSupportFragmentManager().findFragmentById(R.id.fgLeftNavigationDrawer) as BaseDrawerFragment
    }

    val dlMain by Delegates.lazy { bindView<DrawerLayout>(R.id.dlMain) }
    val tbMain by Delegates.lazy { bindView<Toolbar>(R.id.tbMain) }

    companion object {
        fun newIntent(context: Context): Intent {
           return Intent(context, javaClass<MainActivity>())
        }
    }

    override fun setUp() {
        setSupportActionBar(tbMain)
        fgLeftNavigationDrawer.setUpAsLeftDrawer(dlMain, tbMain)

        val popularCategory = "popular"
        tvBarTitle.setText(popularCategory.toUpperCase())
        val ft = getSupportFragmentManager().beginTransaction()
        ft.replace(R.id.flContainer, MovieFragment.newInstance())
        ft.commit()
    }

    fun onEvent(event: OnDrawerToggledEvent) {
        when (event.direction) {
            Direction.LEFT -> fgLeftNavigationDrawer.toggleDrawer()
        }
    }

    fun onEvent(event: OnToolbarTitleChangedEvent) {
        tvBarTitle.setText(event.title)
    }
}