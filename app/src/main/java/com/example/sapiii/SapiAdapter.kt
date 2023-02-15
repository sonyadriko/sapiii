package com.example.sapiii

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.domain.Sapi
import org.w3c.dom.Text

class SapiAdapter : RecyclerView.Adapter<SapiAdapter.MyViewHolder>() {

    private val sapiList = ArrayList<Sapi>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_view_sapi, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = sapiList[position]

        holder.namaSapi.text = currentItem.namaSapi
        holder.genderSapi.text = currentItem.jkSapi


    }

    override fun getItemCount(): Int {
        return sapiList.size
    }

    fun updateUserList(userList : List<Sapi>){

        this.sapiList.clear()
        this.sapiList.addAll(userList)
        notifyDataSetChanged()

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaSapi : TextView = itemView.findViewById(R.id.text_cow_name)
        val genderSapi : TextView = itemView.findViewById(R.id.text_cow_gender)
    }


}

