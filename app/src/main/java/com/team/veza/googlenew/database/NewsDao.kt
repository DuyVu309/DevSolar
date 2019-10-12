package com.team.veza.googlenew.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.team.veza.googlenew.model.News

@Dao
interface NewsDao {
    @Query("Select * from tbl_news where title like :key or content like :key or description like :key")
    fun get(key:String):LiveData<List<News>>
    @Query("Select * from tbl_news where title like :key or content like :key or description like :key and isFavorite='true'")
    fun getFavorite(key:String):LiveData<List<News>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(news:News):Long
    @Delete
    fun delete(news:News)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(news:News)
}