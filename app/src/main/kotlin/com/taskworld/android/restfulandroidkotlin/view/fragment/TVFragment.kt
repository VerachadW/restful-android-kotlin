package com.taskworld.android.restfulandroidkotlin.view.fragment

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import android.support.v4.view.ViewPager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.view.View
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.event.OnToolbarTitleChangedEvent

/**
 * Created by Kittinun Vantasin on 11/7/14.
 */

class TVFragment : BaseFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_tv

    //widgets
    val vpTV by Delegates.lazy { getRootView().bindView<ViewPager>(R.id.vpTV) }

    //data
    val mTVCategories = listOf("airing_today", "popular")

    companion object {
        fun newInstance(): TVFragment {
           return TVFragment()
        }
    }

    override fun setUp() {
    }

    override fun setUpUI(view: View) {
        val pagerAdapter = TVViewPagerAdapter(getChildFragmentManager())
        vpTV.setAdapter(pagerAdapter)
        vpTV.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                EventBus.getDefault().post(OnToolbarTitleChangedEvent(pagerAdapter.getPageTitle(position).toString()))
            }
        })
    }

    inner class TVViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return mTVCategories.size
        }

        override fun getItem(position: Int): Fragment? {
            return TVGridFragment.newInstance(mTVCategories[position])
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTVCategories[position].toUpperCase()
        }
    }
}