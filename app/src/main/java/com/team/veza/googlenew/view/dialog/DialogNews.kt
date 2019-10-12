package com.team.veza.googlenew.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.team.veza.googlenew.R
import com.team.veza.googlenew.model.News

abstract class DialogNews {
    private var dialog:AlertDialog
    var news:News?=null
    var btnFirst:TextView
    var btnSecond:TextView

    @SuppressLint("InflateParams")
    constructor(context: Context,pointShow:Point){
        val builder = AlertDialog.Builder(context)
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_news,null)
        btnFirst = v.findViewById<TextView>(R.id.btnSave)
        btnSecond = v.findViewById<TextView>(R.id.btnFavorie)
        btnFirst.setOnClickListener {
            news?.let {
                btnFirstClick(it)
                hide()
            }
        }
        btnSecond.setOnClickListener {
            news?.let {
                btnSecondClick(it)
                hide()
            }
        }
        builder.setView(v)
        dialog = builder.create()
        val wmlp = dialog.window?.attributes?.apply{
            gravity = Gravity.TOP or Gravity.START
            x = pointShow.x
            y = pointShow.y
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    abstract fun btnFirstClick(news:News)
    abstract fun btnSecondClick(news:News)

    fun setTextButtonFirst(idText:Int){
        btnFirst.setText(idText)
        btnFirst.parent.requestLayout()
    }

    fun setTextButtonSecond(idText:Int){
        btnSecond.setText(idText)
        btnFirst.parent.requestLayout()
    }

    fun show(){
        dialog.show()
    }

    fun hide(){
        dialog.cancel()
    }

}