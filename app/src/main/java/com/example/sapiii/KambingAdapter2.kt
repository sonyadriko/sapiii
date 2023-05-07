package com.example.sapiii

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sapiii.domain.Kambing
import com.example.sapiii.util.OnItemClick
import com.example.sapiii.util.moneyFormatter
import kotlinx.android.synthetic.main.grid_view_sapi.view.*

class KambingAdapter2(
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<KambingAdapter2.KambingViewHolder>() {
    private val kmbgList = mutableListOf<Kambing>()

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(data: List<Kambing>) {
        kmbgList.clear()
        kmbgList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KambingViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_view_sapi, parent, false)
        return KambingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KambingViewHolder, position: Int) {
        val kmbg = kmbgList[position]
        if (kmbg.data.status == "Siap Jual") {
            holder.bind(kmbg)
        } else {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }
        holder.itemView.setOnClickListener {
            onItemClick.onClick(kmbg, position)
        }
    }

    override fun getItemCount() = kmbgList.size

    inner class KambingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(kmbg: Kambing) {
            Glide.with(itemView.image_hewan)
                .load(kmbg.image)
                .placeholder(R.drawable.ic_outline_image_24)
                .into(itemView.image_hewan)
            itemView.text_cow_price.text = moneyFormatter(kmbg.harga.toLong())
            itemView.text_cow_weight.text = "${kmbg.bobot.bobotReal}kg"
            itemView.text_cow_gender.text = kmbg.tag

        }
    }
}