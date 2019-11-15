package com.base.baselibrary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseTitleFragment<BD: ViewDataBinding> : Fragment() {
    /**
     * Fragment with title, can use in view pager
     */
    protected lateinit var binding: BD

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
            getLayoutId(), container, false)
        return binding.root
    }

    abstract fun getLayoutId(): Int

    abstract fun getTitle(): String

}