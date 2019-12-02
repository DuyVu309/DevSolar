package com.example.duyvd1.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import com.example.duyvd1.R
import com.example.duyvd1.activities.main.adapter.MainPagerAdapter
import com.example.duyvd1.activities.main.fragment.ArticlesFragment
import com.example.duyvd1.databinding.ActivityMainBinding
import com.example.duyvd1.utils.AppConsant
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), MainContract.MainView {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel

    private var mHandleSearch = Handler(Looper.getMainLooper())
    private var mRunableSearch = Runnable {
        getDataWithTab()
    }

    companion object {
        var isFirstInit = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = MainViewModel(this, this)
        mBinding.viewModel = mViewModel
        initFragment()
        initSearch()
    }

    private fun initFragment() {
        mBinding.vPager.adapter = MainPagerAdapter(
            supportFragmentManager,
            mViewModel.getListFragment(),
            mViewModel.getListTab()
        )
        mBinding.tLayoutFragment.setupWithViewPager(mBinding.vPager)
        mBinding.tLayoutFragment.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                getDataWithTab()
            }
        })
    }

    private fun initSearch() {
        mBinding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mHandleSearch.removeCallbacks(mRunableSearch)
                mHandleSearch.postDelayed(mRunableSearch, 1000)
            }
        })
    }

    private fun getDataWithTab() {
        val adapter = (mBinding.vPager.adapter as MainPagerAdapter)
        when (mBinding.tLayoutFragment.selectedTabPosition) {
            AppConsant.TAB_TINTUC -> (adapter.getItem(AppConsant.TAB_TINTUC) as ArticlesFragment).callApi(
                mBinding.edtSearch.text.toString()
            )
            AppConsant.TAB_DA_LUU -> (adapter.getItem(AppConsant.TAB_DA_LUU) as ArticlesFragment).getListDataSaved(
                mBinding.edtSearch.text.toString()
            )
            AppConsant.TAB_YEU_THICH -> (adapter.getItem(AppConsant.TAB_YEU_THICH) as ArticlesFragment).getListDataFavorite(
                mBinding.edtSearch.text.toString()
            )
        }
    }

    override fun setEmptyText() {
        mBinding.edtSearch.setText("")
    }
}

