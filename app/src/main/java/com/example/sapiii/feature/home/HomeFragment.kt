package com.example.sapiii.feature.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sapiii.InvestasiFragment
import com.example.sapiii.KesehatanFragment
import com.example.sapiii.feature.invest.InvestasiFragment
import com.example.sapiii.feature.invest.InvestasiFragment
import com.example.sapiii.feature.timbangan.FragmentTimbangan
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.feature.perkawinan.PerkawinanReproduksiFragment
import com.example.sapiii.feature.ternakku.TernakkuFragment
import com.example.sapiii.feature.ternakku.sapi.view.ListSapiFragment
import com.example.sapiii.feature.tips.view.TipsInfoFragment

class HomeFragment : BaseFragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_home, container, false)
        val ternakku = v.findViewById<LinearLayout>(R.id.menu_ternakku)
        val kesehatan = v.findViewById<LinearLayout>(R.id.menu_kesehatan)
        val timbangan = v.findViewById<LinearLayout>(R.id.menu_timbangan)
        val kawinrepro = v.findViewById<LinearLayout>(R.id.menu_perkawinan)
        val pakan = v.findViewById<LinearLayout>(R.id.menu_pakan)
        val logout = v.findViewById<LinearLayout>(R.id.menu_logout)
        val tipsinfo = v.findViewById<LinearLayout>(R.id.menu_tipsinfo)
        val menuInvestasi = v.findViewById<LinearLayout>(R.id.menu_investasi)

        tipsinfo.setOnClickListener {
            val tipsInfoFragment = TipsInfoFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, tipsInfoFragment)?.addToBackStack(null)?.commit()
        }

        timbangan.setOnClickListener {
            val timbanganFragment = FragmentTimbangan()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, timbanganFragment)?.addToBackStack(null)?.commit()
        }


        ternakku.setOnClickListener {
            val ternakkuFragment = TernakkuFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, ternakkuFragment)?.addToBackStack(null)?.commit()
        }

        pakan.setOnClickListener {
            val listSapi = ListSapiFragment.fromPakan()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, listSapi)?.addToBackStack(null)?.commit()
        }

        kesehatan.setOnClickListener {
            val kesehatanFragment = KesehatanFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, kesehatanFragment)?.addToBackStack(null)?.commit()
//            val listSapi = ListSapiFragment.fromKesehatan()
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.frame_layout, listSapi)?.addToBackStack(null)?.commit()
        }

        kawinrepro.setOnClickListener {
            val perkawinanReproduksi = PerkawinanReproduksiFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, perkawinanReproduksi)?.addToBackStack(null)?.commit()
        }

        menuInvestasi.setOnClickListener {
            val perkawinanReproduksi = InvestasiFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, perkawinanReproduksi)?.addToBackStack(null)?.commit()
        }

        logout.setOnClickListener {
            logout()
        }
        // Inflate the layout for this fragment
        return v
    }
}