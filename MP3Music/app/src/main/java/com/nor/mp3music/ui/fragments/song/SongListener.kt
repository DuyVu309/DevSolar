package com.nor.mp3music.ui.fragments.song

import com.nor.filternews.base.AdapterBase
import com.nor.mp3music.models.Song

interface SongListener : AdapterBase.ListItemListener {
    fun onItemSongClicked(item: Song)
}