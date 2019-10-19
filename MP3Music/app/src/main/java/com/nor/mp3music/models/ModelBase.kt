package com.nor.mp3music.models

import android.net.Uri
import java.io.Serializable

abstract class ModelBase : Serializable {
    abstract fun getUri(): Uri
}