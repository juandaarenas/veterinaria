package com.example.veterinaria.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = Mascotas.TABLE_NAME, indices = [
    Index(value = ["id"], unique = true)
] )

data class Mascotas (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_ID) var id:Long,
    @ColumnInfo(name = COL_NAME) var name:String,
    @ColumnInfo(name = COL_IDENTIFICACION) var identificacion:String,
    @ColumnInfo(name = COL_RAZA) var raza:String,
    @ColumnInfo(name = COL_TIPOMASCOTA) var tipomascota:String,
    @ColumnInfo(name = COL_VACUNA) var vacuna:String

){
    override fun toString(): String {
        return "$name"
    }

    companion object {
        const val TABLE_NAME = "Mascotas"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_IDENTIFICACION = "identificacion"
        const val COL_RAZA = "raza"
        const val COL_TIPOMASCOTA = "tipomascota"
        const val COL_VACUNA = "vacuna"
    }
}