package com.example.duyvd1.db

import androidx.room.*
import com.example.duyvd1.model.Articles

@Dao
interface DataBaseHelper {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOnlySingleArticles(articles: Articles)

    @Query("SELECT * FROM Articles")
    fun getAllArticles(): MutableList<Articles>

    @Query("SELECT * FROM Articles WHERE title LIKE '%' || :search || '%'")
    fun filterAllArticles(search : String): MutableList<Articles>

    @Query("SELECT * FROM Articles WHERE isFavorite = 1 AND title LIKE '%' || :search || '%'")
    fun filterListFavoriteArticles(search : String): MutableList<Articles>

    @Query("SELECT * FROM Articles WHERE id = :id")
    fun fetchOneArticlesById(id: Long): Articles

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateArticles(articles: Articles)

    @Delete
    fun deleteMovie(articles: Articles)
}
