package com.taskworld.android.restfulandroidkotlin.fragments

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.support.v4.view.ViewPager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.view.View
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.events.OnToolbarTitleChangedEvent
import android.support.v4.view.PagerAdapter

/**
 * Created by Kittinun Vantasin on 11/7/14.
 */

class TVFragment : BaseFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_tv

    //widgets
//    val vpTV by Delegates.lazy { getRootView().bindView<ViewPager>(R.id.vpTV) }
    var vpTV: ViewPager by Delegates.notNull()

    //data
    val mTVCategories = listOf("airing_today", "popular")

    class object {
        fun newInstance(): TVFragment {
           return TVFragment()
        }
    }

    override fun setUp() {
    }

    override fun setUpUI(view: View) {
        vpTV = view.bindView<ViewPager>(R.id.vpTV)
        val adapter = TVViewPagerAdapter(getFragmentManager())
        vpTV.setAdapter(adapter)
        vpTV.setOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                EventBus.getDefault().post(OnToolbarTitleChangedEvent(adapter.getPageTitle(position).toString()))
            }
        })
        adapter.notifyDataSetChanged()
    }

    inner class TVViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return mTVCategories.size
        }

        override fun getItem(position: Int): Fragment? {
            return TVGridFragment.newInstance(mTVCategories[position])
        }

        override fun getItemPosition(`object`: Any?): Int {
            return PagerAdapter.POSITION_NONE
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTVCategories[position].toUpperCase()
        }
    }
}