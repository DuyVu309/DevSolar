package com.example.filternew

import android.app.ProgressDialog
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.filternew.ui.main.SectionsPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.filternew.ui.news.NewsViewModel
import com.example.filternew.ui.read.ReadFragment
import com.example.filternew.utils.InjectorUtil

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),OnFragmentInteractListener {
    private var searchView: SearchView? = null
    private lateinit var viewModel: NewsViewModel
    private var progressDialog: ProgressDialog? = null
    var onBackPressAlternative: (() -> Unit)? = null

    override fun onBackPressed() {
        if (onBackPressAlternative != null) {
            onBackPressAlternative!!()
        } else {
            super.onBackPressed()
        }
    }

    override fun onBackFragment() {
        if (supportFragmentManager.fragments.size > 0) {
            supportFragmentManager.popBackStack()
            contain_main.visibility = View.VISIBLE
            contain_webview.visibility = View.GONE
        }
    }

    override fun onOpenFragment(fragment: Fragment) {
        val mTAG = fragment.javaClass.simpleName
        val sameTagFragment = supportFragmentManager.findFragmentByTag(mTAG)
        if (sameTagFragment != null)
            supportFragmentManager.beginTransaction().remove(sameTagFragment).commit()
        val fragmentTransaction = supportFragmentManager.beginTransaction().replace(R.id.contain_webview, fragment, mTAG).addToBackStack(mTAG)
        fragmentTransaction.commit()
        contain_main.visibility = View.GONE
        contain_webview.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, InjectorUtil.getSpecifyNewsFactory())
            .get(NewsViewModel::class.java)
        setSupportActionBar(toolbar)
        setupViewPager()
        viewModel.isLoading.observe(this, Observer {
            if (it) showProgressDialog()
            else closeProgressDialog()
        })
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.app_bar_search)
        searchView = searchItem?.actionView as SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.tag = query.toString()
                viewModel.getNews()
                searchView?.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
            progressDialog?.setMessage("Loading...")
            progressDialog?.setCanceledOnTouchOutside(false)
        }
        progressDialog?.show()
    }

    private fun closeProgressDialog() {
        progressDialog?.dismiss()
    }
}