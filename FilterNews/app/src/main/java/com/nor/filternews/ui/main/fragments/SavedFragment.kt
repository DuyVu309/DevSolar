package com.nor.filternews.ui.main.fragments

import com.nor.filternews.App
import com.nor.filternews.R
import com.nor.filternews.base.FragmentBase
import com.nor.filternews.databinding.FragmentSavedBinding

class SavedFragment : FragmentBase<FragmentSavedBinding>() {
    
    override fun getLayoutId(): Int {
        return R.layout.fragment_saved
    }

    override fun getTitle(): String {
        return App.instance!!.getString(R.string.title_saved)
    }

}