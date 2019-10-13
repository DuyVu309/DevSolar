package com.example.duyvd1.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.duyvd1.utils.AppConsant
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "Articles")
@Parcelize
class Articles(

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @Expose
    @ColumnInfo(name = "author")
    @SerializedName("author")
    var author: String? = null,

    @Expose
    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String? = null,

    @Expose
    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String? = null,

    @Expose
    @ColumnInfo(name = "url")
    @SerializedName("url")
    var url: String? = null,

    @Expose
    @ColumnInfo(name = "urlToImage")
    @SerializedName("urlToImage")
    var urlToImage: String? = null,

    @Expose
    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    var publishedAt: Date? = null,

    @Expose
    @ColumnInfo(name = "content")
    @SerializedName("content")
    var content: String? = null,

    @ColumnInfo(name = "urlParse")
    var urlParse: String? = null,

    var isFavorite: Boolean? = false

) : Parcelable