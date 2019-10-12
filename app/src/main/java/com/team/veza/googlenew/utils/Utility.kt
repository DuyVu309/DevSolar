package com.team.veza.googlenew.utils

import android.widget.Toast
import androidx.core.content.ContextCompat

object Utility {
    fun getString(id:Int) = App.self().resources.getString(id)

    fun getStringArray(id:Int): Array<String> = App.self().resources.getStringArray(id)

    fun getColor(id:Int) = ContextCompat.getColor(App.self(),id)

    fun getSize(id:Int) = App.self().resources.getDimensionPixelSize(id)

    fun showMessage(messageId:Int) = Toast.makeText(App.self(),messageId,Toast.LENGTH_SHORT).show()
    fun showMessage(message:String) = Toast.makeText(App.self(),message,Toast.LENGTH_SHORT).show()
}