package com.nor.filternews.ui.main

import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.nor.filternews.R
import com.nor.filternews.base.ActivityBase
import com.nor.filternews.databinding.ActivityMainBinding
import com.nor.filternews.ui.main.fragments.NewsFragment
import com.nor.filternews.ui.main.fragments.SavedFragment

class MainActivity : ActivityBase<ActivityMainBinding>(), SearchView.OnQueryTextListener {

    private lateinit var adapter: MainPagerAdapter
    private val fmNews = NewsFragment()
    private val fmSaved = SavedFragment()
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initAct() {
        super.initAct()
        adapter = MainPagerAdapter(supportFragmentManager,
            fmNews, fmSaved)
        binding.pager.adapter = adapter
        binding.tab.setupWithViewPager(binding.pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.search)?.actionView as SearchView
        search.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        fmNews.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}
