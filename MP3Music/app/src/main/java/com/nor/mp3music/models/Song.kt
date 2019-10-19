package com.nor.mp3music.models

import android.net.Uri
import android.provider.MediaStore

class Song : ModelBase() {
    @MediaInfo(getFieldName = MediaStore.Audio.Media.DATA)
    var data: String? = null

    @MediaInfo(getFieldName = MediaStore.Audio.Media.TITLE)
    var title: String? = null

    @MediaInfo(getFieldName = MediaStore.Audio.Media.DURATION)
    var duration: Int = 0

    @MediaInfo(getFieldName = MediaStore.Audio.Media.SIZE)
    var size: Int = 0



    @MediaInfo(getFieldName = MediaStore.Audio.Media.ARTIST)
    var artist: String? = null

    override fun getUri(): Uri {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }
}