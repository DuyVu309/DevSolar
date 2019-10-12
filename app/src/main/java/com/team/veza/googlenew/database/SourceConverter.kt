package com.team.veza.googlenew.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.team.veza.googlenew.model.Source

class SourceConverter {
    companion object{
        @JvmStatic
        val gson = Gson()
        @JvmStatic
        @TypeConverter
        fun toType(data:String):Source{
            return gson.fromJson(data,Source::class.javaObjectType)
        }
        @JvmStatic
        @TypeConverter
        fun fromToJson(source: Source) = gson.toJson(source)
    }

}