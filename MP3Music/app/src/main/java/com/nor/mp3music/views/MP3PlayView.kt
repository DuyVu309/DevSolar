package com.nor.mp3music.views

import android.content.Context
import android.database.Observable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nor.mp3music.databinding.PlayViewBinding
import com.nor.mp3music.service.MP3Service

class MP3PlayView : FrameLayout {

    private val binding = PlayViewBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        visibility = GONE
    }

    var service: MP3Service? = null
        set(value) {
            binding.service = value
            value?.apply {
                item?.observe(context as AppCompatActivity, Observer {
                    if (it == null) {
                        visibility = GONE
                    }else {
                        visibility = View.VISIBLE
                        binding.item = it
                    }
                })
                position.observe(context as AppCompatActivity, Observer {
                    binding.position = it
                })

                isPlaying.observe(context as AppCompatActivity, Observer {
                    binding.isPlaying = it
                })
            }
        }
}