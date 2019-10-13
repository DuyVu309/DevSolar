package com.example.duyvd1.db

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import com.example.duyvd1.model.Articles
import com.example.duyvd1.model.Converters

@Database(entities = [Articles::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticlesDataBase : RoomDatabase() {
    abstract fun dataBaseAccess(): DataBaseHelper
}