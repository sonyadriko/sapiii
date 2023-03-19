package com.example.sapiii.mutasi.kambing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.databinding.ListViewMutasiBinding
import com.example.sapiii.domain.MutasiKambing

class MutasiKambingAdapter : RecyclerView.Adapter<MutasiKambingAdapter.MutasiKambingViewHolder>() {
    private val mutasiKambingList = ArrayList<MutasiKambing>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MutasiKambingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListViewMutasiBinding.inflate(inflater, parent, false)
        return MutasiKambingViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MutasiKambingViewHolder, position: Int) {
        val currentMutasiKambing = mutasiKambingList[position]
        holder.namaMS.text = currentMutasiKambing.nama
        holder.dateMS.text = currentMutasiKambing.tanggal.toString()
        holder.tipeMS.text = currentMutasiKambing.tipe

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMutasiKambingList(data: List<MutasiKambing>) {
        mutasiKambingList.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mutasiKambingList.size

    }

    inner class MutasiKambingViewHolder(private val binding: ListViewMutasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val namaMS = binding.mutasiName
        val dateMS = binding.mutasiDate
        val tipeMS = binding.mutasiTipe

    }
}