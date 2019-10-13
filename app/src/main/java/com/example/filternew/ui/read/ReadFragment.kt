package com.example.filternew.ui.read


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProviders
import com.example.filternew.BaseFragment

import com.example.filternew.R
import kotlinx.android.synthetic.main.fragment_read.*
import com.example.filternew.utils.InjectorUtil
import com.example.filternew.ui.news.NewsViewModel
import com.example.filternew.utils.Const


/**
 * A simple [Fragment] subclass.
 */
class ReadFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(
                activity!!,
                InjectorUtil.getSpecifyNewsFactory(context!!)
        ).get(NewsViewModel::class.java)
    }
    private var url = ""

    override fun onStart() {
        super.onStart()
        setOnBackPressed {
            listener?.onBackFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getString(Const.ARG_URL) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        web_view.clearHistory()
        viewModel.isLoading.value = true
        web_view.loadUrl(url)
        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                // do your logic
                viewModel.isLoading.value = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setOnBackPressed(null)
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String): ReadFragment {
            return ReadFragment().apply {
                arguments = Bundle().apply {
                    putString(Const.ARG_URL, url)
                }
            }
        }
    }

}
