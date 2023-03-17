package com.example.sapiii

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.databinding.ListItemTipsInfoBinding
import com.example.sapiii.databinding.ListViewMutasiBinding
import com.example.sapiii.domain.Artikel
import com.example.sapiii.domain.MutasiSapi
import com.example.sapiii.feature.tips.view.adapter.ArtikelAdapter

class MutasiSapiAdapter : RecyclerView.Adapter<MutasiSapiAdapter.MutasiSapiViewHolder>(){
    private val mutasiSapiList = ArrayList<MutasiSapi>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MutasiSapiAdapter.MutasiSapiViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListViewMutasiBinding.inflate(inflater, parent, false)
        return MutasiSapiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MutasiSapiAdapter.MutasiSapiViewHolder, position: Int) {
        val currentMutasiSapi = mutasiSapiList[position]
        holder.namaMS.text = currentMutasiSapi.nama
        holder.dateMS.text = currentMutasiSapi.tanggal.toString()
        holder.tipeMS.text = currentMutasiSapi.tipe

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMutasiSapiList(data: List<MutasiSapi>) {
        mutasiSapiList.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mutasiSapiList.size

    }
    inner class MutasiSapiViewHolder(private val binding: ListViewMutasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val namaMS = binding.mutasiName
        val dateMS = binding.mutasiDate
        val tipeMS = binding.mutasiTipe

    }

}