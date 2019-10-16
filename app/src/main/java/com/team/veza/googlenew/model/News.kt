package com.team.veza.googlenew.model

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.team.veza.googlenew.database.SourceConverter

@Entity(tableName = "tbl_news")
class News{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "countId")
    var countId:Int = 0
    @ColumnInfo(name = "source")
    @TypeConverters(SourceConverter::class)
    @SerializedName("source")
    @Expose
    var source = Source()

    @ColumnInfo(name = "author")
    @SerializedName("author")
    @Expose
    var author:String? = ""

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title:String? = ""

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    var description:String = ""

    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    var url:String = ""

    @ColumnInfo(name = "urlToImage")
    @SerializedName("urlToImage")
    @Expose
    var urlToImage:String = ""

    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    @Expose

    var publishedAt:String? = ""
    @ColumnInfo(name = "content")
    @SerializedName("content")
    @Expose
    var content:String = ""

    @ColumnInfo(name = "isFavorite")
    var isFavorite:Boolean = false

    @ColumnInfo(name = "savedPath")
    var savedPath:String = ""

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

    @Ignore
    override fun toString(): String {
        return "NewsInfo [Title: $title]"
    }

    constructor()
}