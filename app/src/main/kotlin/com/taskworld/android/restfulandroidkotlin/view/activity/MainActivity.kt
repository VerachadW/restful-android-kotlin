package com.taskworld.android.restfulandroidkotlin.view.activity

import android.content.Context
import android.content.Intent
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.event.OnDrawerToggledEvent
import com.taskworld.android.restfulandroidkotlin.event.OnToolbarTitleChangedEvent
import com.taskworld.android.restfulandroidkotlin.view.fragment.BaseDrawerFragment
import com.taskworld.android.restfulandroidkotlin.view.fragment.BaseDrawerFragment.Direction
import com.taskworld.android.restfulandroidkotlin.view.fragment.MovieFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId = R.layout.activity_main

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun setUp() {
        setSupportActionBar(tbMain)
        val fgLeftNavigationDrawer = supportFragmentManager.findFragmentById(R.id.fgLeftNavigationDrawer) as BaseDrawerFragment
        fgLeftNavigationDrawer.setUpAsLeftDrawer(dlMain, tbMain)

        val popularCategory = "popular"
        tvBarTitle.setText(popularCategory.toUpperCase())
        val ft = getSupportFragmentManager().beginTransaction()
        ft.replace(R.id.flContainer, MovieFragment.newInstance())
        ft.commit()
    }

    fun onEvent(event: OnDrawerToggledEvent) {
        val fgLeftNavigationDrawer = supportFragmentManager.findFragmentById(R.id.fgLeftNavigationDrawer) as BaseDrawerFragment
        if (event.direction == Direction.LEFT) {
            fgLeftNavigationDrawer.toggleDrawer()
        }
    }

    fun onEvent(event: OnToolbarTitleChangedEvent) {
        tvBarTitle.setText(event.title)
    }
}