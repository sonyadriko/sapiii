package com.example.sapiii.feature.ternakku.sapi.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.domain.Sapi
import com.example.sapiii.util.OnItemClick

class SapiAdapter(
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<SapiAdapter.MyViewHolder>() {
    private val sapiList = ArrayList<Sapi>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_view_sapi, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = sapiList[position]

        holder.namaSapi.text = currentItem.tag
        holder.genderSapi.text = currentItem.kelamin
        val into = Glide.with(holder.itemView.context)
            .load(currentItem.image.toUri())
            .placeholder(R.drawable.ic_outline_image_24)
            .into(holder.imageSapi)

        holder.itemView.setOnClickListener {

            onItemClick.onClick(currentItem, position)
        }
    }

    override fun getItemCount(): Int {
        return sapiList.size
    }

    fun updateUserList(userList: List<Sapi>) {
        this.sapiList.clear()
        this.sapiList.addAll(userList)
        notifyDataSetChanged()

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaSapi: TextView = itemView.findViewById(R.id.text_cow_name)
        val genderSapi: TextView = itemView.findViewById(R.id.text_cow_gender)
        val imageSapi: ImageView = itemView.findViewById(R.id.image_hewan)


    }
}

