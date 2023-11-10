package com.example.veterinaria.Controllers

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.veterinaria.Models.Raza

@Dao
interface RazaDao {
    @Insert
    fun insert(raza: Raza) : Long
    @Query("SELECT * FROM ${Raza.TABLE_NAME}")
    fun get(): List<Raza>
    @Query("SELECT * FROM ${Raza.TABLE_NAME} WHERE ${Raza.COL_ID} = :RazaId")
    fun get(RazaId: Long): Raza?

    @Update
    fun update(raza: Raza)

    @Delete
    fun delete(raza: Raza)
}