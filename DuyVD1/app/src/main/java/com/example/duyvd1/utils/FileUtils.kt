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
                val document = Jsoup.connect(params[0]).get()

                // Get document (HTML page) title
                val title = document.title()
                buffer.append("Title:$title\r\n")

                // Get meta info
                val metaElems = document.select("meta")
                buffer.append("META DATA\r\n")
                for (metaElem in metaElems) {
                    val name = metaElem.attr("name")
                    val content = metaElem.attr("content")
                    buffer.append("name [$name] - content [$content] \r\n")
                }

                val topicList = document.select("h2.topic")
                buffer.append("Topic list\r\n")
                for (topic in topicList) {
                    val data = topic.text()

                    buffer.append("Data [$data] \r\n")
                }

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