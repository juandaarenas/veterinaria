package com.example.veterinaria.Controllers

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.veterinaria.Models.TiposMascotas

@Dao
interface TipoMascotaDao {
    @Insert
    fun insert(Tmascot: TiposMascotas) : Long
    @Query("SELECT * FROM ${TiposMascotas.TABLE_NAME}")
    fun get(): List<TiposMascotas>
    @Query("SELECT * FROM ${TiposMascotas.TABLE_NAME} WHERE ${TiposMascotas.COLUM_ID} = :TmId")
    fun get(TmId: Long): TiposMascotas?

    @Update
    fun update(Tmascot: TiposMascotas)

    @Delete
    fun delete(Tmascot: TiposMascotas)
}