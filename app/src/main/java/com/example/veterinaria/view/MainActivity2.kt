package com.example.veterinaria.view

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.veterinaria.DataBase.DB
import com.example.veterinaria.Models.Mascotas
import com.example.veterinaria.Models.Raza
import com.example.veterinaria.Models.TiposMascotas
import com.example.veterinaria.Models.Vacunas
import com.example.veterinaria.R
import com.example.veterinaria.adapter.AdapterMascota
import com.example.veterinaria.databinding.ActivityMain2Binding
import com.example.veterinaria.databinding.AgregaritemsBinding
import com.example.veterinaria.databinding.AlertagregarBinding
import com.example.veterinaria.databinding.AlerteditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    private lateinit var OtraMascota: Mascotas
    private lateinit var OtrasRaza: Raza
    private lateinit var OtroTmascota: TiposMascotas
    private lateinit var OtraVacuna: Vacunas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        pantalla_Completa()
        listas()
        binding.apply {
            CoroutineScope(Dispatchers.IO).launch {
                var lista = DB.GetDataBase(this@MainActivity2).mascotaDAO().get()
                runOnUiThread {
                    Recycl.layoutManager = LinearLayoutManager(this@MainActivity2)
                    Recycl.adapter = AdapterMascota(lista.toMutableList())
                }
            }
            btnAgregarItem.setOnClickListener {
                AgregarItems()
            }
            btnAgregar.setOnClickListener {
                agregarMascota()
            }
            btnRecargarRecy.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    var lista = DB.GetDataBase(this@MainActivity2).mascotaDAO().get()
                    runOnUiThread {
                        Recycl.layoutManager = LinearLayoutManager(this@MainActivity2)
                        Recycl.adapter = AdapterMascota(lista.toMutableList())
                    }
                }
            }
            buscarMascota.addTextChangedListener {
                var busqueda=buscarMascota.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    var lista = DB.GetDataBase(this@MainActivity2).mascotaDAO().getIdentificacion(busqueda)
                    runOnUiThread {
                        Recycl.layoutManager = LinearLayoutManager(this@MainActivity2)
                        Recycl.adapter = AdapterMascota(lista.toMutableList())
                    }
                }
            }
        }
    }

    fun pantalla_Completa() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).hide(WindowInsetsCompat.Type.systemBars())
    }

    fun llenarRecyclear() {
        binding.apply {
            CoroutineScope(Dispatchers.IO).launch {
                var lista = DB.GetDataBase(this@MainActivity2).mascotaDAO().get()
                runOnUiThread {
                    Recycl.layoutManager = LinearLayoutManager(this@MainActivity2)
                    Recycl.adapter = AdapterMascota(lista.toMutableList())
                }
            }
        }
    }

    fun agregarMascota() {
        var binding2: AlertagregarBinding
        binding2 = AlertagregarBinding.inflate(LayoutInflater.from(binding.root.context))
        var alert = AlertDialog.Builder(binding.root.context).setView(binding2.root).create().show()
        binding2.apply {
            CoroutineScope(Dispatchers.IO).launch {
                var raza = DB.GetDataBase(this@MainActivity2).razaDAO().get().toMutableList()
                var tipoM =
                    DB.GetDataBase(this@MainActivity2).tipoMascotaDAO().get().toMutableList()
                var vacuna = DB.GetDataBase(this@MainActivity2).vacunaDao().get().toMutableList()
                runOnUiThread {
                    binding2.spinRaza.adapter = ArrayAdapter<Raza>(
                        this@MainActivity2,
                        android.R.layout.simple_spinner_dropdown_item,
                        raza
                    )
                    binding2.spinTipoM.adapter = ArrayAdapter<TiposMascotas>(
                        this@MainActivity2,
                        android.R.layout.simple_spinner_dropdown_item,
                        tipoM
                    )
                    binding2.spinVacuna.adapter = ArrayAdapter<Vacunas>(
                        this@MainActivity2,
                        android.R.layout.simple_spinner_dropdown_item,
                        vacuna
                    )
                }
            }
            btnAgregarMascota.setOnClickListener {
                try {
                    var raza = spinRaza.selectedItem as Raza
                    var tipoMascot = spinTipoM.selectedItem as TiposMascotas
                    var vacuna = spinVacuna.selectedItem as Vacunas
                    var nombreraza = raza.name
                    OtraMascota = Mascotas(
                        0,
                        textNombre.text.toString(),
                        textIdentificacion.text.toString(),
                        nombreraza,
                        tipoMascot.name,
                        vacuna.name
                    )
                    AgregarMascota(OtraMascota)
                    CoroutineScope(Dispatchers.IO).launch {
                        var id: Int = 1
                        var lista = DB.GetDataBase(binding.root.context).mascotaDAO().get()
                        runOnUiThread {
                            binding.Recycl.layoutManager = LinearLayoutManager(binding.root.context)
                            binding.Recycl.adapter = AdapterMascota(lista.toMutableList())
                        }
                    }
                    textIdentificacion.setText("")
                    textNombre.setText("")
                    Toast.makeText(
                        binding.root.context,
                        "se agrego una nueva Mascota",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (o: Exception) {
                    Toast.makeText(binding.root.context, o.message, Toast.LENGTH_SHORT).show()
                }
                CoroutineScope(Dispatchers.IO).launch {
                    var lista = DB.GetDataBase(this@MainActivity2).mascotaDAO().get()
                    runOnUiThread {
                        binding.Recycl.layoutManager = LinearLayoutManager(this@MainActivity2)
                        binding.Recycl.adapter = AdapterMascota(lista.toMutableList())
                    }
                }
            }
        }
    }

    fun AgregarItems() {
        var binding4: AgregaritemsBinding
        binding4 = AgregaritemsBinding.inflate(LayoutInflater.from(binding.root.context))
        var alert = AlertDialog.Builder(binding.root.context).setView(binding4.root).create().show()
        binding4.apply {
            btnAddRaza.setOnClickListener {
                try {
                    OtrasRaza = Raza(0, textRaza.text.toString().trim())
                    agregarRaza(OtrasRaza)
                    textRaza.setText("")
                    Toast.makeText(
                        binding.root.context,
                        "se agrego una nueva raza",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (o: Exception) {
                    Toast.makeText(binding.root.context, o.message, Toast.LENGTH_SHORT).show()
                }
            }
            btnAddTipoM.setOnClickListener {
                try {
                    OtroTmascota = TiposMascotas(0, textTipoMascota.text.toString().trim())
                    agregarTipoM(OtroTmascota)
                    textTipoMascota.setText("")
                    Toast.makeText(
                        binding.root.context,
                        "se agrego una nuevo tipo de mascotas",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (o: Exception) {
                    Toast.makeText(binding.root.context, o.message, Toast.LENGTH_SHORT).show()
                }
            }
            btnAddVacuna.setOnClickListener {
                try {
                    OtraVacuna = Vacunas(0, textVacuna.text.toString().trim())
                    AgregarVacuna(OtraVacuna)
                    textVacuna.setText("")
                    Toast.makeText(
                        binding.root.context,
                        "se agrego una nueva vacuna",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (o: Exception) {
                    Toast.makeText(binding.root.context, o.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun agregarRaza(OtrasRaza: Raza) {
        CoroutineScope(Dispatchers.IO).launch {
            DB.GetDataBase(this@MainActivity2).razaDAO().insert(OtrasRaza)
        }
    }

    fun agregarTipoM(OtroTmascota: TiposMascotas) {
        CoroutineScope(Dispatchers.IO).launch {
            DB.GetDataBase(this@MainActivity2).tipoMascotaDAO().insert(OtroTmascota)
        }
    }

    fun AgregarVacuna(OtraVacuna: Vacunas) {
        CoroutineScope(Dispatchers.IO).launch {
            DB.GetDataBase(this@MainActivity2).vacunaDao().insert(OtraVacuna)
        }
    }

    fun AgregarMascota(OtraMascota: Mascotas) {
        CoroutineScope(Dispatchers.IO).launch {
            DB.GetDataBase(this@MainActivity2).mascotaDAO().insert(OtraMascota)
        }
    }

    fun listas() {
        CoroutineScope(Dispatchers.IO).launch {
            listaRaza = DB.GetDataBase(this@MainActivity2).razaDAO().get().toMutableList()
            listaTipoM = DB.GetDataBase(this@MainActivity2).tipoMascotaDAO().get().toMutableList()
            listaVacuna = DB.GetDataBase(this@MainActivity2).vacunaDao().get().toMutableList()
        }
    }

    companion object {
        lateinit var listaRaza: MutableList<Raza>
        lateinit var listaTipoM: MutableList<TiposMascotas>
        lateinit var listaVacuna: MutableList<Vacunas>
        lateinit var bindn: ActivityMain2Binding
        fun llenarRecicler() {
            CoroutineScope(Dispatchers.IO).launch {
                var lista = DB.GetDataBase(bindn.root.context).mascotaDAO().get()
                bindn.Recycl.layoutManager = LinearLayoutManager(bindn.root.context)
                bindn.Recycl.adapter = AdapterMascota(lista.toMutableList())
            }
        }
    }
}