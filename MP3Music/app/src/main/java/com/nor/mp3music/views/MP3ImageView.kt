package com.nor.mp3music.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class MP3ImageView : AppCompatImageView, SCAbstractClickView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun setOnClickListener(l: OnClickListener?) {
        setBackground(context, this, background)
        super.setOnClickListener(l)
    }
}