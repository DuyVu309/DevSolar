package com.nor.filternews.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nor.filternews.base.FragmentBase

class MainPagerAdapter : FragmentPagerAdapter {
    private val fms: Array<out FragmentBase<*>>

    constructor(fm: FragmentManager, vararg fms: FragmentBase<*>) : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        this.fms = fms
    }

    override fun getItem(position: Int): Fragment {
        return fms[position]
    }

    override fun getCount(): Int {
        return fms.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fms[position].getTitle()
    }
}
