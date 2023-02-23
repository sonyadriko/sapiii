package com.example.sapiii.feature.tips

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.R
import com.example.sapiii.TambhDataArtikelActivity
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.feature.ternakku.sapi.view.TambahDataSapiActivity

class TipsInfoFragment : Fragment(), OnClickListener {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_tips_info, container, false)
        val btnAdd = v.findViewById<Button>(R.id.btn_tambah_data_artikel)

        btnAdd.setOnClickListener{
            startActivity(Intent(activity, TambhDataArtikelActivity::class.java))
        }


        return v
    }


    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}