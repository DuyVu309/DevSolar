package com.team.veza.googlenew.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.team.veza.googlenew.R
import com.team.veza.googlenew.utils.Utility
import com.team.veza.googlenew.view.pager.FragmentNews

class PagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ) {

    private val titles = Utility.getStringArray(R.array.pager_titles)
    private val listFragment = ArrayList<Fragment>()

    init {
        listFragment.add(FragmentNews().apply {
            frmId = FragmentNews.ID_NEWS
        })
        listFragment.add(FragmentNews().apply {
            frmId = FragmentNews.ID_SAVED
        })
        listFragment.add(FragmentNews().apply {
            frmId = FragmentNews.ID_FAVORITE
        })
    }

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }


}