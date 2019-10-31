package com.nor.mp3music.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import com.nor.mp3music.R

interface SCAbstractClickView{
    fun setBackground(context: Context, view: View, background: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var background = background
            if (background == null) {
                val outValue = TypedValue()
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                background = context.getDrawable(outValue.resourceId)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    val ripple = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        RippleDrawable(ColorStateList.valueOf(context.getColor(R.color.abc_color_highlight_material)), background, null)
                    } else {
                        RippleDrawable(ColorStateList.valueOf(context.resources.getColor(R.color.abc_color_highlight_material)), background, null)
                    }
                    view.background = ripple
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}