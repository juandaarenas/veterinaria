package com.example.veterinaria.Controllers

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.veterinaria.Models.Mascotas

@Dao
interface MascotaDao {
    @Insert
    fun insert(mascota: Mascotas) : Long
    @Query("SELECT * FROM ${Mascotas.TABLE_NAME}")
    fun get(): List<Mascotas>
    @Query("SELECT * FROM ${Mascotas.TABLE_NAME} WHERE ${Mascotas.COL_ID} = :Id")
    fun get(Id: Long): Mascotas?
    @Query("SELECT * FROM ${Mascotas.TABLE_NAME} WHERE ${Mascotas.COL_IDENTIFICACION}=:Identificacion")
    fun getIdentificacion(Identificacion:String):List<Mascotas>
    @Update
    fun update(mascota: Mascotas)

    @Delete
    fun delete(mascota: Mascotas)
}