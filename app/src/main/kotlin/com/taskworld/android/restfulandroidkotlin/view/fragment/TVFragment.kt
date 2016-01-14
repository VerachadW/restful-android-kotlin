package com.taskworld.android.restfulandroidkotlin.view.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.event.OnToolbarTitleChangedEvent
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import de.greenrobot.event.EventBus

/**
 * Created by Kittinun Vantasin on 11/7/14.
 */
class TVFragment : BaseFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_tv

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
        val pagerAdapter = TVViewPagerAdapter(childFragmentManager)
        val vpTV = getRootView().bindView<ViewPager>(R.id.vpTV)
        vpTV.adapter = pagerAdapter
        vpTV.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
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