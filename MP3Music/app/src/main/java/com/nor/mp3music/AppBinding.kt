package com.nor.mp3music

import androidx.databinding.BindingAdapter
import com.nor.mp3music.views.MP3TextView
import java.text.SimpleDateFormat

object AppBinding {

    private val format = SimpleDateFormat("mm:ss")

    @JvmStatic
    @BindingAdapter("duration", "position")
    fun setTime(tv: MP3TextView, duration: Int, position: Int) {
        tv.text = "${format.format(position)}/${format.format(duration)}";
    }
}