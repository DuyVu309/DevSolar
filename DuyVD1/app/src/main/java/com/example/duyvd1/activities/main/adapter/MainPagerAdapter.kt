package com.example.duyvd1.activities.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.duyvd1.activities.main.fragment.ArticlesFragment

internal class MainPagerAdapter
constructor(
        fm: FragmentManager,
        private var mFragments: MutableList<Fragment>,
        private val mlist: MutableList<String>
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mlist[position]
    }
}
