package com.example.veterinaria.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = Raza.TABLE_NAME, indices = [
    Index(value = ["name"], unique = true)
] )

data class Raza(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_ID) var id:Long,
    @ColumnInfo(name = COL_NAME) var name:String
){
    override fun toString(): String {
        return "$name"
    }

    companion object {
        const val TABLE_NAME = "Raza"
        const val COL_ID = "id"
        const val  COL_NAME = "name"
    }
}