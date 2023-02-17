package com.example.sapiii.feature.ternakku.sapi.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.R
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.detail.DetailSapiActivity

class SapiAdapter(private val context: Context) : RecyclerView.Adapter<SapiAdapter.MyViewHolder>() {
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

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailSapiActivity::class.java).apply {
                putExtra("namasapi", currentItem.tag)
                putExtra("jeniskelamin", currentItem.kelamin)
            }
            context.startActivity(intent)

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


    }
}

