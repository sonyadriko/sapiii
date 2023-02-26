package com.example.sapiii.feature.ternakku.kambing.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.R
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.ternakku.sapi.view.adapter.SapiAdapter
import com.example.sapiii.util.OnItemClick

class KambingAdapter(
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<KambingAdapter.MyViewHolder>() {
    private val kambingList = ArrayList<Kambing>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KambingAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_view_sapi, parent, false)
        return KambingAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KambingAdapter.MyViewHolder, position: Int) {
        val currentItem = kambingList[position]

        holder.namaSapi.text = currentItem.tag
        holder.genderSapi.text = currentItem.kelamin

        holder.itemView.setOnClickListener {

            onItemClick.onClick(currentItem, position)
        }
    }

    override fun getItemCount(): Int {
        return kambingList.size
    }

    fun updateUserList(userList: List<Kambing>) {
        this.kambingList.clear()
        this.kambingList.addAll(userList)
        notifyDataSetChanged()

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaSapi: TextView = itemView.findViewById(R.id.text_cow_name)
        val genderSapi: TextView = itemView.findViewById(R.id.text_cow_gender)


    }
}