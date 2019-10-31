package com.nor.mp3music.ui.fragments.song

import android.os.Bundle
import android.widget.Toast
import com.nor.filternews.base.AdapterBase
import com.nor.filternews.base.FragmentBase
import com.nor.mp3music.R
import com.nor.mp3music.dao.SystemDao
import com.nor.mp3music.databinding.FragmentSongBinding
import com.nor.mp3music.models.Song
import com.nor.mp3music.ui.MainActivity

class SongFragment : FragmentBase<FragmentSongBinding> (), SongListener {

    private lateinit var adapter: AdapterBase<Song>
    override fun getLayoutId(): Int {
        return R.layout.fragment_song
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = AdapterBase(activity!!.layoutInflater, R.layout.item_song)
        binding.lvSong.adapter = adapter
        adapter.listener = this
        SystemDao(context!!).apply {
            adapter.setData(getMedia(Song::class.java))
        }
    }

    override fun onItemSongClicked(item: Song) {
        (activity as MainActivity).service?.apply {
            data = adapter.getData()
            create(adapter.getData()?.indexOf(item) ?: 0)
        }

    }

}
