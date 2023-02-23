package com.example.sapiii

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.domain.Artikel
import com.example.sapiii.domain.Sapi
import com.example.sapiii.util.OnItemClick
import kotlinx.android.synthetic.main.activity_tambh_data_artikel.view.*

class ArtikelAdapter(
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder>() {
    private val artikelList = ArrayList<Artikel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_tips_info, parent, false)
        return ArtikelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        val currentArtikel = artikelList[position]

        holder.judulAr.text = currentArtikel.judul
        holder.descAr.text = currentArtikel.desc

    }

    override fun getItemCount(): Int {
        return artikelList.size
    }

    class ArtikelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val judulAr: TextView = itemView.findViewById(R.id.et_judul_artikel)
        val descAr: TextView = itemView.findViewById(R.id.et_desc_artikel)
    }
}