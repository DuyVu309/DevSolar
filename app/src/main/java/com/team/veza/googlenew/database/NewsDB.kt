package com.team.veza.googlenew.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.team.veza.googlenew.model.News
import com.team.veza.googlenew.model.Source
import com.team.veza.googlenew.utils.App

@Database(entities = arrayOf(News::class), version = 2)
abstract class NewsDB:RoomDatabase(){
    abstract fun newsDao():NewsDao
    companion object{
        private var instance:NewsDB?= null
        @Synchronized
        fun getInstance(): NewsDB {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    App.self(),
                    NewsDB::class.java, "app_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}