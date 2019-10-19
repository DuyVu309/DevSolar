package com.nor.mp3music.dao

import android.content.Context
import android.database.Cursor
import com.nor.mp3music.models.MediaInfo
import com.nor.mp3music.models.ModelBase
import java.lang.reflect.Field

class SystemDao(context: Context) {
    private var resolver = context.contentResolver

    fun <T: ModelBase> getMedia(clz: Class<T>) : MutableList<T> {
        val media = clz.newInstance()
        val cursor = resolver.query(
            media.getUri(),
            null,
            null,
            null,
            null
        )
        val data = mutableListOf<T>()
        cursor?.moveToFirst()
        while (cursor?.isAfterLast == false) {
            data.add(getRow(cursor, clz))
            cursor.moveToNext()
        }
        return data
    }

    private fun <T: ModelBase> getRow(cursor: Cursor?, clz: Class<T>) : T {
        val t = clz.newInstance()
        val fields = t.javaClass.declaredFields
        fields.forEach {
            it.isAccessible = true
            val annotation = it.getAnnotation(MediaInfo::class.java)
            val index = cursor?.getColumnIndex(annotation.getFieldName)
            mappingField(cursor!!, index!!, it, t)
        }
        return t
    }

    private fun <T: ModelBase> mappingField(cursor: Cursor,
                                            index: Int, f: Field, t: T) {
        when {
            f.type == Int::class.java
                -> f.setInt(t, cursor.getInt(index))
            f.type == String::class.java
                -> f.set(t, cursor.getString(index))
            f.type == Float::class.java
                -> f.setFloat(t, cursor.getFloat(index))
        }
    }

}