package com.example.veterinaria.Controllers

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.veterinaria.Models.Vacunas

@Dao
interface VacunasDao {
    @Insert
    fun insert(vacuna: Vacunas) : Long
    @Query("SELECT * FROM ${Vacunas.TABLE_NAME}")
    fun get(): List<Vacunas>
    @Query("SELECT * FROM ${Vacunas.TABLE_NAME} WHERE ${Vacunas.COLUM_ID} = :VacunaId")
    fun get(VacunaId: Long): Vacunas?

    @Update
    fun update(vacuna: Vacunas)

    @Delete
    fun delete(vacuna: Vacunas)
}