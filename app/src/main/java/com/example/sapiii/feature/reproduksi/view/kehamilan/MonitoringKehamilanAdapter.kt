package com.example.sapiii.feature.reproduksi.view.kehamilan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.databinding.ListViewKehamilanBinding
import com.example.sapiii.domain.MonitoringKehamilan
import com.example.sapiii.util.OnItemClick

class MonitoringKehamilanAdapter(private val onItemClick: OnItemClick): RecyclerView.Adapter<MonitoringKehamilanAdapter.MonitoringKehamilanViewHolder>() {
    private val monitoringKehamilanList = ArrayList<MonitoringKehamilan>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonitoringKehamilanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListViewKehamilanBinding.inflate(inflater, parent, false)
        return MonitoringKehamilanViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MonitoringKehamilanViewHolder, position: Int) {
        val currentKehamilan = monitoringKehamilanList[position]
        holder.namaMK.text = currentKehamilan.nama
        holder.dateMK.text = currentKehamilan.tanggalAwal.toString()
        holder.kodeKandang.text = currentKehamilan.kandang
        holder.perkiraanMK.text = currentKehamilan.tanggalPerkiraan.toString()
        holder.itemView.setOnClickListener {
            onItemClick.onClick(currentKehamilan, position)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMonitoringPejantanList(data: List<MonitoringKehamilan>) {
        monitoringKehamilanList.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return monitoringKehamilanList.size

    }

    inner class MonitoringKehamilanViewHolder(private val binding: ListViewKehamilanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val namaMK = binding.kehamilanName
        val dateMK = binding.kehamilanDate
        val kodeKandang = binding.kehamilanKodekandang
        val perkiraanMK = binding.kehamilanPerkiraan

    }
}