package com.team.veza.googlenew.model

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.team.veza.googlenew.database.SourceConverter

@Entity(tableName = "tbl_news")
class News{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "countId")
    var countId = 0
    @ColumnInfo(name = "source")
    @TypeConverters(SourceConverter::class)
    @SerializedName("source")
    @Expose
    var source = Source()
    @ColumnInfo(name = "author")
    @SerializedName("author")
    @Expose
    var author = ""
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title = ""
    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    var description = ""
    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    var url = ""
    @ColumnInfo(name = "urlToImage")
    @SerializedName("urlToImage")
    @Expose
    var urlToImage = ""
    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    @Expose
    var publishedAt = ""
    @ColumnInfo(name = "content")
    @SerializedName("content")
    @Expose
    var content = ""

    @ColumnInfo(name = "isFavorite")
    var isFavorite = false

    @ColumnInfo(name = "savedPath")
    var savedPath = ""

    @Ignore
    constructor(
        source: Source,
        author: String,
        title: String,
        description: String,
        url: String,
        urlToImage: String,
        publishedAt: String,
        content: String
    ) {
        this.source = source
        this.author = author
        this.title = title
        this.description = description
        this.url = url
        this.urlToImage = urlToImage
        this.publishedAt = publishedAt
        this.content = content
    }

    constructor()
}