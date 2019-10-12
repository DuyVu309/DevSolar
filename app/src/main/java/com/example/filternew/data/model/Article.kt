package com.example.filternew.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "article_table")
class Article {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @Ignore
    @SerializedName("source")
    @Expose
    var source: Source? = null

    @ColumnInfo(name = "author")
    @SerializedName("author")
    @Expose
    var author: String? = null

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title: String? = null

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    var description: String? = null

    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    var url: String? = null

    @ColumnInfo(name = "urlToImage")
    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String? = null

    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null

    @ColumnInfo(name = "content")
    @SerializedName("content")
    @Expose
    var content: String? = null

    constructor() : this("", "", "", "", "", "", "")
    @Ignore
    constructor(
        author: String,
        title: String,
        description: String,
        url: String,
        urlToImage: String,
        content: String,
        publishedAt: String
    ) {
        this.author = author
        this.title = title
        this.description = description
        this.url = url
        this.urlToImage = urlToImage
        this.content = content
        this.publishedAt = publishedAt
    }

}