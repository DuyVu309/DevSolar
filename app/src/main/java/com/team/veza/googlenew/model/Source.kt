package com.team.veza.googlenew.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Source{
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id = ""
    @SerializedName("name")
    @Expose
    var name =""

    override fun toString(): String {
        return "{\"id\":\"$id\",\"name\":\"$name\"}"
    }
}