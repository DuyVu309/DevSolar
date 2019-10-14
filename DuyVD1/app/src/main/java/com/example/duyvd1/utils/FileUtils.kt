package com.example.duyvd1.utils

import android.os.AsyncTask
import org.jsoup.Jsoup
import java.io.IOException

class FileUtils {
    private var mCallBack: (String) -> Unit = {}

    companion object {
        private var mInstance: FileUtils? = null
        fun newInstance(): FileUtils {
            if (mInstance == null) mInstance = FileUtils()
            return mInstance!!
        }
    }

    fun setCallBack(callBack: (String) -> Unit) {
        mCallBack = callBack
    }

    fun parseWebViewToString(url: String) : FileUtils{
        PareseURL().execute(url)
        return this
    }

    inner class PareseURL : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String {
            val buffer = StringBuffer()
            try {
                val doc = Jsoup.connect(params[0])
                    .get()
                val ele = doc.select("#mp-itn b a")
                buffer.append( ele.toString())

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return buffer.toString()
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            mCallBack(s)
        }
    }
}