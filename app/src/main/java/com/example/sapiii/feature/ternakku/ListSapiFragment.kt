package com.example.sapiii.feature.ternakku

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.R
import com.example.sapiii.TambahDataSapiActivity
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.DatabaseReference

class ListSapiFragment : Fragment() {

    private lateinit var dbref : DatabaseReference
    private lateinit var sapiRecyclerView : RecyclerView
    private lateinit var sapiArrayList : ArrayList<Sapi>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_list_sapi, container, false)
        val tmbhsapi = v.findViewById<Button>(R.id.btn_tambah_data_sapi)

        tmbhsapi.setOnClickListener{
            startActivity(Intent(activity, TambahDataSapiActivity::class.java))
        }

        sapiRecyclerView = v.findViewById(R.id.sapi_list)
        sapiRecyclerView.layoutManager = LinearLayoutManager(this)
        sapiRecyclerView.setHasFixedSize(true)
        

        // Inflate the layout for this fragment
        return v
    }
}