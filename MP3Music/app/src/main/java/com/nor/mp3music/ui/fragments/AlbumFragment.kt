package com.nor.mp3music.ui.fragments

import com.nor.filternews.base.FragmentBase
import com.nor.mp3music.R
import com.nor.mp3music.databinding.FragmentSongBinding

class AlbumFragment : FragmentBase<FragmentSongBinding> () {
    override fun getLayoutId(): Int {
        return R.layout.fragment_album
    }

}
