package com.example.sapiii.feature.invest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.R
import com.example.sapiii.domain.Sapi
import com.example.sapiii.util.OnItemClick
import kotlinx.android.synthetic.main.list_view_sapi.view.*

class SapiAdapter2(
    private val onItemClick: OnItemClick
    ) : RecyclerView.Adapter<SapiAdapter2.SapiViewHolder>() {
    private val sapiList = ArrayList<Sapi>()

    inner class SapiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sapi: Sapi) {


            itemView.text_cow_name.text = sapi.tag
//            itemView.beratTextView.text = sapi.berat.toString()
            itemView.text_cow_gender.text = sapi.data.status

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SapiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_sapi, parent, false)
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
}