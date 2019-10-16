package com.team.veza.googlenew.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.team.veza.googlenew.model.News

@Dao
interface NewsDao {
    @Query("Select * from tbl_news")
    fun get():LiveData<List<News>>
    @Query("Select * from tbl_news where title like :key")
    fun get(key:String):LiveData<List<News>>
    @Query("Select * from tbl_news where isFavorite=1")
    fun getFavorite():LiveData<List<News>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(news:News):Long
    @Delete
    fun delete(news:News)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(news:News)
}