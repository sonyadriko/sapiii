package com.example.sapiii.feature.mutasi.sapi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.databinding.ListViewMutasiBinding
import com.example.sapiii.domain.MutasiSapi
import com.example.sapiii.domain.Sapi
import com.example.sapiii.util.OnItemClick

class MutasiSapiAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<MutasiSapiAdapter.MutasiSapiViewHolder>() {
    private val mutasiSapiList = ArrayList<MutasiSapi>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MutasiSapiViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListViewMutasiBinding.inflate(inflater, parent, false)
        return MutasiSapiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MutasiSapiViewHolder, position: Int) {
        val currentMutasiSapi = mutasiSapiList[position]
        holder.namaMS.text = currentMutasiSapi.nama
        holder.dateMS.text = currentMutasiSapi.tanggal
        holder.tipeMS.text = currentMutasiSapi.tipe
        holder.itemView.setOnClickListener {
            onItemClick.onClick(currentMutasiSapi, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMutasiSapiList(data: List<MutasiSapi>) {
//        mutasiSapiList.apply {
//            clear()
//            addAll(data)
//        }
//        notifyDataSetChanged()
        this.mutasiSapiList.clear()
        this.mutasiSapiList.addAll(data)
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