package com.example.sapiii.feature.invest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.domain.Sapi
import com.example.sapiii.util.OnItemClick
import com.example.sapiii.util.moneyFormatter
import kotlinx.android.synthetic.main.grid_view_sapi.view.*

class SapiAdapter2(
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<SapiAdapter2.SapiViewHolder>() {
    private val sapiList = mutableListOf<Sapi>()

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(data: List<Sapi>) {
        sapiList.clear()
        sapiList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SapiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_view_sapi, parent, false)
        return SapiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SapiViewHolder, position: Int) {
        val sapi = sapiList[position]
        if (sapi.data.status == "Siap Jual") {
            holder.bind(sapi)
        } else {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }
        holder.itemView.setOnClickListener {
            onItemClick.onClick(sapi, position)
        }
    }

    override fun getItemCount() = sapiList.size

    inner class SapiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(sapi: Sapi) {
            Glide.with(itemView.image_hewan)
                .load(sapi.image)
                .placeholder(R.drawable.ic_outline_image_24)
                .into(itemView.image_hewan)
            itemView.text_cow_price.text = moneyFormatter(sapi.harga.toLong())
            itemView.text_cow_weight.text = "${sapi.bobot.bobotReal}kg"
            itemView.text_cow_gender.text = sapi.tag
        }
    }
}