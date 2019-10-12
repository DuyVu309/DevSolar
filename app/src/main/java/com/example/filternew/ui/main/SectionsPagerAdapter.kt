package com.example.filternew.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.filternew.R
import com.example.filternew.ui.news.NewsFragment
import com.example.filternew.utils.Const


private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    companion object{
        const val NUM_ITEMS = 3
    }

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a Fragment (defined as a static inner class below).
        return when(position){
            Const.KEY_NEWS_FRAGMENT-> NewsFragment.newInstance(Const.KEY_NEWS_FRAGMENT)
            Const.KEY_SAVES_FRAGMENT-> NewsFragment.newInstance(Const.KEY_SAVES_FRAGMENT)
            Const.KEY_FAVORITE_FRAGMENT-> NewsFragment.newInstance(Const.KEY_FAVORITE_FRAGMENT)
            else -> NewsFragment.newInstance(Const.KEY_NEWS_FRAGMENT)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return NUM_ITEMS
    }
}