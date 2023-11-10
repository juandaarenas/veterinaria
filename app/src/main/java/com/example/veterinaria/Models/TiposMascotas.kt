package com.example.veterinaria.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = TiposMascotas.TABLE_NAME, indices = [
    Index(value = ["name"], unique = true)
] )

data class TiposMascotas(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUM_ID) var id:Long,
    @ColumnInfo(name = COLUM_NAME) var name:String
){
    override fun toString(): String {
        return "$name"
    }

    companion object {
        const val TABLE_NAME = "TiposMascotas"
        const val COLUM_ID = "id"
        const val  COLUM_NAME = "name"
    }
}