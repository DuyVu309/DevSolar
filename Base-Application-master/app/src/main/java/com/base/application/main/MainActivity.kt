package com.base.application.main

import androidx.lifecycle.Observer
import com.base.application.R
import com.base.application.databinding.ActivityMainBinding
import com.base.baselibrary.activity.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class.java), MainLister {


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initAct() {
        super.initAct()
        binding.listener = this
        viewModel.bitmap.observe(this, Observer {
            binding.imDownload.setImageBitmap(it)
        })
    }

    override fun onDownloadClick() {
        val link = binding.edtLink.text.toString()
        if (link.isEmpty()) return
//        viewModel.downloadImage(link)
        viewModel.searchNews(link)
    }
}
