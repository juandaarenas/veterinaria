package com.example.veterinaria.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.veterinaria.Controllers.MascotaDao
import com.example.veterinaria.Controllers.RazaDao
import com.example.veterinaria.Controllers.TipoMascotaDao
import com.example.veterinaria.Controllers.VacunasDao
import com.example.veterinaria.Models.Mascotas
import com.example.veterinaria.Models.Raza
import com.example.veterinaria.Models.TiposMascotas
import com.example.veterinaria.Models.Vacunas

@Database(entities = [ Mascotas::class,Raza::class,TiposMascotas::class,Vacunas::class], version = 1, exportSchema = true)
abstract class DB : RoomDatabase() {
    abstract  fun mascotaDAO(): MascotaDao
    abstract  fun razaDAO(): RazaDao
    abstract  fun tipoMascotaDAO(): TipoMascotaDao
    abstract  fun vacunaDao(): VacunasDao

    companion object {
        @Volatile
        private var INSTANCIA: DB? = null

        fun GetDataBase(context: Context): DB {
            if (INSTANCIA != null) {
                return INSTANCIA!!
            }

            INSTANCIA = Room.databaseBuilder(
                context.applicationContext,
                DB::class.java,
                "DBTest"
            ).build()
            return INSTANCIA!!

        }
    }
}