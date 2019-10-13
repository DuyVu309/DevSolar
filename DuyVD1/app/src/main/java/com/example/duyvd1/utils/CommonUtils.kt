package com.example.duyvd1.utils

import android.app.ProgressDialog
import android.content.Context
import com.example.duyvd1.R
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.annotation.SuppressLint
import kotlin.math.roundToInt

object CommonUtils {
    fun showLoadingDialog(context: Context?): ProgressDialog {
        val pD = ProgressDialog(context)
        pD.setMessage(context?.getString(R.string.loading))
        pD.isIndeterminate = true
        pD.setCancelable(false)
        pD.setCanceledOnTouchOutside(false)
        pD.show()
        return pD
    }

    @SuppressLint("MissingPermission")
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun parsePixel(context: Context, dp: Float): Int {
        return (context.resources.displayMetrics.density * dp).roundToInt()
    }
}