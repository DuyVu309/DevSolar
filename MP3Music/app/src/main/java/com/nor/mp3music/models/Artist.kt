package com.nor.mp3music.models

import android.net.Uri
import android.provider.MediaStore

class Artist : ModelBase() {

    @MediaInfo(MediaStore.Audio.Artists.ARTIST)
    var name: String? = null

    @MediaInfo(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)
    var numberOAlbum:  Int  = 0

    @MediaInfo(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)
    var numberOTrask: Int = 0

    override fun getUri(): Uri {
        return MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
    }

}