package com.team.veza.googlenew.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.team.veza.googlenew.R
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.team.veza.googlenew.adapter.PagerAdapter
import com.team.veza.googlenew.databinding.ActivityMainBinding
import com.team.veza.googlenew.utils.Utility
import com.team.veza.googlenew.view.custom.TabSelectedListener
import com.team.veza.googlenew.viewmodel.NewsViewModel


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private var searchView:SearchView?=null
    private val pagerAdapter = PagerAdapter(supportFragmentManager)
    lateinit var viewModel:NewsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        initViewModel()
        initToolbar()
        initView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initView() {
        vp_category.adapter = pagerAdapter
        tl_category.setupWithViewPager(vp_category)
        initDividerTabLayout()
        tl_category.addOnTabSelectedListener(object:TabSelectedListener(){
            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewModel.currentTabFocus = tl_category.selectedTabPosition
            }
        })
        vp_category.offscreenPageLimit = 2
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initDividerTabLayout() {
        val root = tl_category.getChildAt(0)
        if (root is LinearLayout) {
            root.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(Utility.getColor(R.color.divider_color))
            drawable.setSize(2, 1)
            root.dividerPadding = resources.getDimensionPixelSize(R.dimen.tablayout_divider_padding)
            root.dividerDrawable = drawable
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        val miSearch = menu?.findItem(R.id.mi_search)
        miSearch?.let {
            searchView = miSearch.actionView as SearchView
            searchView?.setOnQueryTextListener(this)
            (searchView?.findViewById(androidx.appcompat.R.id.search_src_text) as EditText?)?.apply {
                setTextColor(Color.WHITE)
            }
            (searchView?.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView?)?.apply {
                setImageResource(R.drawable.ic_close_white_24dp)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val textSearch = query?:""
        viewModel.textSearch.value = textSearch
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return false
    }

}
