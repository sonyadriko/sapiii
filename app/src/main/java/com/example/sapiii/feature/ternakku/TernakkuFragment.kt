package com.example.sapiii.feature.ternakku

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.sapiii.R
import com.example.sapiii.feature.ternakku.kambing.view.ListKambingFragment
import com.example.sapiii.feature.ternakku.sapi.view.ListSapiFragment

class TernakkuFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_ternakku, container, false)
        val ternakkuSapi = v.findViewById<LinearLayout>(R.id.ternakku_sapi)
        val ternakkuKambing = v.findViewById<LinearLayout>(R.id.ternakku_kambing)

        ternakkuSapi.setOnClickListener {
            val listSapi = ListSapiFragment.fromTernakku()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, listSapi)
                ?.addToBackStack(null)
                ?.commit()
        }

        ternakkuKambing.setOnClickListener {
            val listKambing = ListKambingFragment.fromTernakku()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, listKambing)
                ?.addToBackStack(null)
                ?.commit()
        }
        // Inflate the layout for this fragment
        return v
    }
}