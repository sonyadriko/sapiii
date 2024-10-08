package com.example.sapiii.feature.reproduksi.view.pejantan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.databinding.ListViewPejantanBinding
import com.example.sapiii.domain.MonitoringPejantan
import com.example.sapiii.util.OnItemClick

class MonitoringPejantanAdapter(private val onItemClick: OnItemClick): RecyclerView.Adapter<MonitoringPejantanAdapter.MonitoringPejantanViewHolder>() {
    private val monitoringPejantanList = ArrayList<MonitoringPejantan>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonitoringPejantanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListViewPejantanBinding.inflate(inflater, parent, false)
        return MonitoringPejantanViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MonitoringPejantanViewHolder, position: Int) {
        val currentPejantan = monitoringPejantanList[position]
        holder.namaMS.text = currentPejantan.nama
        holder.dateMS.text = currentPejantan.tanggal.toString()
        holder.kodeKandang.text = currentPejantan.kandang
        holder.itemView.setOnClickListener {
            onItemClick.onClick(currentPejantan, position)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMonitoringPejantanList(data: List<MonitoringPejantan>) {
        monitoringPejantanList.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return monitoringPejantanList.size

    }

    inner class MonitoringPejantanViewHolder(private val binding: ListViewPejantanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val namaMS = binding.pejantanName
        val dateMS = binding.pejantanDate
        val kodeKandang = binding.pejantanKodekandang

    }

}