package com.team.veza.googlenew.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.team.veza.googlenew.R
import com.team.veza.googlenew.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_URL = "extra.url"
    }

    private lateinit var binding:ActivityDetailBinding
    private var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        url = intent.getStringExtra(EXTRA_URL)?:""
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initView(){
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.loadUrl(url)
    }
}
