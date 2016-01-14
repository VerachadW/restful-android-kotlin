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

class MovieFragment : BaseFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_movie

    //data
    val mMovieCategories = listOf("now_playing", "top_rated")

    companion object {
        fun newInstance(): MovieFragment {
            return MovieFragment()
        }
    }

    override fun setUp() {
    }

    override fun setUpUI(view: View) {
        val pagerAdapter = MovieViewPagerAdapter(childFragmentManager)
        var vpMovie = getRootView().bindView<ViewPager>(R.id.vpMovie)
        vpMovie.adapter = pagerAdapter
        vpMovie.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                EventBus.getDefault().post(OnToolbarTitleChangedEvent(pagerAdapter.getPageTitle(position).toString()))
            }
        })
    }

    inner class MovieViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return mMovieCategories.size
        }

        override fun getItem(position: Int): Fragment? {
            return MovieGridFragment.newInstance(mMovieCategories[position])
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mMovieCategories[position].toUpperCase()
        }
    }
}
