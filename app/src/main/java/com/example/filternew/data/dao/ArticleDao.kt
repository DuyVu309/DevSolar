package com.example.filternew.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filternew.data.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

    @Query("select * from article_table order by id DESC")
    fun getArticles(): LiveData<List<Article>>

    @Query("DELETE FROM article_table")
    fun deleteAll()
}