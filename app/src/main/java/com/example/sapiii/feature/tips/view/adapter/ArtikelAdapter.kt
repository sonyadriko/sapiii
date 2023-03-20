package com.example.sapiii.feature.tips.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.databinding.ListItemTipsInfoBinding
import com.example.sapiii.domain.Artikel
import com.example.sapiii.util.OnItemClick

class ArtikelAdapter(
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder>() {
    private val artikelList = ArrayList<Artikel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTipsInfoBinding.inflate(inflater, parent, false)
        return ArtikelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        val currentArtikel = artikelList[position]
        holder.judulAr.text = currentArtikel.judul
        val into = Glide.with(holder.itemView.context)
            .load(currentArtikel.image.toUri())
            .placeholder(R.drawable.ic_outline_image_24)
            .into(holder.imageAr)
        holder.itemView.setOnClickListener {
            onItemClick.onClick(currentArtikel, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateArtikelList(data: List<Artikel>) {
        artikelList.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return artikelList.size
    }

    inner class ArtikelViewHolder(private val binding: ListItemTipsInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val judulAr = binding.tvArtikel
        val imageAr = binding.ivArtikel
    }
}