package com.example.veterinaria.adapter

import android.R
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinaria.DataBase.DB
import com.example.veterinaria.Models.Mascotas
import com.example.veterinaria.Models.Raza
import com.example.veterinaria.Models.TiposMascotas
import com.example.veterinaria.Models.Vacunas
import com.example.veterinaria.databinding.AlerteditBinding
import com.example.veterinaria.databinding.ItemrecyclearBinding
import com.example.veterinaria.view.MainActivity2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdapterMascota (var listaM: MutableList<Mascotas>): RecyclerView.Adapter<AdapterMascota.MascotaHolder>(){

    inner class MascotaHolder(var binding: ItemrecyclearBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaHolder {
        return MascotaHolder(ItemrecyclearBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return listaM.size
    }
    override fun onBindViewHolder(holder: MascotaHolder, position: Int) {
        var pet = listaM[position];
        var binding = holder.binding
        pet.apply {
            binding.apply {
                textIdentificacion.setText(identificacion)
                textNombre.setText(name)
                textRaza.setText(raza)
                textTipoMascota.setText(tipomascota)
                textVacuna.setText(vacuna)
                btnEliminar.setOnClickListener {
                    AlertDialog.Builder(binding.root.context)
                        .setMessage("¿Desea eliminar la raza $name?")
                        .setPositiveButton("Ok") { view, b ->
                            Toast.makeText(binding.root.context, "Usted ha eliminado ha $name", Toast.LENGTH_SHORT)
                                .show()
                            CoroutineScope(Dispatchers.IO).launch {
                                DB.GetDataBase(binding.root.context).mascotaDAO().delete(pet)

                            }
                            listaM.remove(pet)
                            this@AdapterMascota.notifyItemRemoved(position)
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            Toast.makeText(binding.root.context, "Usted ha cancelado la eliminacion", Toast.LENGTH_SHORT)
                                .show()
                        }.create().show()
                }
                btnEditar.setOnClickListener {
                    var binding1:AlerteditBinding
                    binding1 = AlerteditBinding.inflate(LayoutInflater.from(binding.root.context))
                    var alert = AlertDialog.Builder(binding.root.context).setView(binding1.root).create().show()
                    binding1.apply {
                        spinRaza.adapter= ArrayAdapter<Raza>(binding1.root.context,R.layout.simple_spinner_dropdown_item,MainActivity2.listaRaza)
                        spinTipoM.adapter= ArrayAdapter<TiposMascotas>(binding1.root.context,R.layout.simple_spinner_dropdown_item,MainActivity2.listaTipoM)
                        spinVacuna.adapter= ArrayAdapter<Vacunas>(binding1.root.context,R.layout.simple_spinner_dropdown_item,MainActivity2.listaVacuna)
                        textIdentificacion.setText(identificacion)
                        textNombre.setText(name)
                        btnEditars.setOnClickListener {
                            var raza = spinRaza.selectedItem as Raza
                            var tipoMascot = spinTipoM.selectedItem as TiposMascotas
                            var vacuna = spinVacuna.selectedItem as Vacunas
                            pet.name=textNombre.text.toString()
                            pet.identificacion=textIdentificacion.text.toString()
                            pet.raza=raza.name
                            pet.tipomascota=tipoMascot.name
                            pet.vacuna=vacuna.name
                            try {
                                CoroutineScope(Dispatchers.IO).launch {
                                    DB.GetDataBase(binding.root.context).mascotaDAO().update(pet)
                                }
                                    Toast.makeText(binding.root.context, "se Actualizo", Toast.LENGTH_SHORT).show()
                            }catch (e:Exception){
                                    Toast.makeText(binding.root.context,e.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

            }
        }
    }
}