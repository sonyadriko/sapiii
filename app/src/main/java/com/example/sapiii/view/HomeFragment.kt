package com.example.sapiii.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.sapiii.R

class HomeFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_home, container, false)
        val ternakku = v.findViewById<LinearLayout>(R.id.menu_ternakku)
        val kesehatan = v.findViewById<LinearLayout>(R.id.menu_kesehatan)

        val kawinrepro = v.findViewById<LinearLayout>(R.id.menu_perkawinan)



        ternakku.setOnClickListener {
            val ternakkuFragment = TernakkuFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, ternakkuFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        kesehatan.setOnClickListener {
            val kesehatanFragment = KesehatanFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, kesehatanFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        kawinrepro.setOnClickListener {
            val perkawinanReproduksi = PerkawinanReproduksiFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, perkawinanReproduksi)
                ?.addToBackStack(null)
                ?.commit()
        }
        // Inflate the layout for this fragment
        return v
    }
}