package com.nor.mp3music.models

import android.net.Uri
import android.provider.MediaStore

class Album : ModelBase() {

    @MediaInfo(MediaStore.Audio.Albums.ALBUM)
    var name: String? = null

    @MediaInfo(MediaStore.Audio.Albums.ARTIST)
    var artist: String? = null

    @MediaInfo(MediaStore.Audio.Albums.NUMBER_OF_SONGS)
    var numberOSong: Int = 0

    @MediaInfo(MediaStore.Audio.Albums.ALBUM_ART)
    var image: String? = null

    override fun getUri(): Uri {
        return MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
    }

}