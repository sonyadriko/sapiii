package com.example.sapiii.feature.home

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getSystemService
import com.example.sapiii.KesehatanFragment
import com.example.sapiii.NotificationReceiver
import com.example.sapiii.feature.timbangan.FragmentTimbangan
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentHomeBinding
import com.example.sapiii.feature.invest.InvestasiFragment
import com.example.sapiii.feature.perkawinan.PerkawinanReproduksiFragment
import com.example.sapiii.feature.ternakku.TernakkuFragment
import com.example.sapiii.feature.ternakku.sapi.view.ListSapiFragment
import com.example.sapiii.feature.tips.view.TipsInfoFragment
import java.util.*

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.tvWelcome.text = "Selamat Datang "+userRepository.nama


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
            val listSapi = ListSapiFragment.fromPakan()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, listSapi)?.addToBackStack(null)?.commit()
        }

        binding.menuKesehatan.setOnClickListener {
            val kesehatanFragment = KesehatanFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, kesehatanFragment)?.addToBackStack(null)?.commit()
//            val listSapi = ListSapiFragment.fromKesehatan()
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.frame_layout, listSapi)?.addToBackStack(null)?.commit()
        }

        binding.menuPerkawinan.setOnClickListener {
            val perkawinanReproduksi = PerkawinanReproduksiFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, perkawinanReproduksi)?.addToBackStack(null)?.commit()
        }

        binding.menuInvestasi.setOnClickListener {
            val perkawinanReproduksi = InvestasiFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, perkawinanReproduksi)?.addToBackStack(null)?.commit()
        }

        binding.menuLogout.setOnClickListener {
            logout()
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}