package com.example.sapiii.feature.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sapiii.*
import com.example.sapiii.feature.timbangan.FragmentTimbangan
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentHomeBinding
import com.example.sapiii.feature.invest.InvestasiFragment
import com.example.sapiii.feature.perkawinan.PerkawinanReproduksiFragment
import com.example.sapiii.feature.ternakku.TernakkuFragment
import com.example.sapiii.feature.tips.view.TipsInfoFragment
import com.example.sapiii.feature.mutasi.MutasiFragment2
import java.util.*

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.tvWelcome.text = "Selamat Datang " + userRepository.nama

        binding.ivCameraQr.setOnClickListener {
            startActivity(Intent(context, ScanActivity::class.java))

        }

        binding.menuMutasi.setOnClickListener {
            val mutasiFragment = MutasiFragment2()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, mutasiFragment)?.addToBackStack(null)?.commit()
        }


        binding.menuTipsinfo.setOnClickListener {
            val tipsInfoFragment = TipsInfoFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, tipsInfoFragment)?.addToBackStack(null)?.commit()
        }

        binding.menuTimbangan.setOnClickListener {
            val timbanganFragment = FragmentTimbangan()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, timbanganFragment)?.addToBackStack(null)?.commit()
        }


        binding.menuTernakku.setOnClickListener {
            val ternakkuFragment = TernakkuFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, ternakkuFragment)?.addToBackStack(null)?.commit()
        }

        binding.menuPakan.setOnClickListener {
            val pakanMenu = PakanFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, pakanMenu)?.addToBackStack(null)?.commit()
        }

        binding.menuKesehatan.setOnClickListener {
            val kesehatanFragment = KesehatanFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, kesehatanFragment)?.addToBackStack(null)?.commit()
        }

        binding.menuPerkawinan.setOnClickListener {
            val perkawinanReproduksi = PerkawinanReproduksiFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, perkawinanReproduksi)?.addToBackStack(null)?.commit()
        }

        binding.menuInvestasi.setOnClickListener {
            val investasi = InvestasiFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, investasi)?.addToBackStack(null)?.commit()

        }

        binding.menuLogout.setOnClickListener {
            logout()
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}