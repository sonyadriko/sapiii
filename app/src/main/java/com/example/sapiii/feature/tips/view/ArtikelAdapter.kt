package com.example.sapiii.feature.tips.view

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
import kotlinx.android.synthetic.main.activity_tambh_data_artikel.view.*

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
        holder.bind(currentArtikel, position)
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
//        val descAr = binding.

        fun bind(artikel: Artikel, position: Int) {
            judulAr.text = artikel.judul
            val into = Glide.with(imageAr)
                .load(artikel.image.toUri())
                .placeholder(R.drawable.ic_outline_image_24)
                .into(imageAr)
            binding.root.setOnClickListener {
                onItemClick.onClick(artikel, position)
            }
        }
    }
}