package com.example.duyvd1.activities.web

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.duyvd1.utils.AppConsant
import com.example.duyvd1.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_webview.*
import com.example.duyvd1.R
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.example.duyvd1.db.ArticlesDbManager
import com.example.duyvd1.model.Articles
import com.example.duyvd1.utils.FileUtils

class WebviewActivity : AppCompatActivity() {

    private var dialog: ProgressDialog? = null
    private var mArticles: Articles? = null
    private val REQUEST_CODE = 1

    companion object {
        fun newInstance(context: Context, articles: Articles): Intent {
            return Intent(context, WebviewActivity::class.java).apply {
                putExtra(AppConsant.KEY_EXTRA_DATA, articles)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        dialog = CommonUtils.showLoadingDialog(this)
        mArticles = intent.getParcelableExtra(AppConsant.KEY_EXTRA_DATA) ?: return

        if(CommonUtils.isNetworkAvailable(this)){
            webview.loadUrl(mArticles?.url)
            webview.webViewClient = MyWebViewClient()
            if(mArticles?.id != null) download()
        }else if(mArticles?.urlParse != null){
            webview.loadData(mArticles?.urlParse, "text/html; charset=utf-8", "UTF-8")
        }
    }

    private fun download() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
            } else {
                FileUtils.newInstance()
                        .parseWebViewToString(mArticles?.url!!)
                        .setCallBack {
                            mArticles?.urlParse = it
                            ArticlesDbManager.newInstance(this).updateToFavorite(mArticles!!)
                        }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            download()
        }
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            dialog?.dismiss()
        }
    }
}
