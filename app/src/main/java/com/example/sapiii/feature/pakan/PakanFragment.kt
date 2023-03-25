package com.example.sapiii.feature.pakan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sapiii.databinding.FragmentPakanBinding

class PakanFragment : Fragment() {


    private lateinit var binding: FragmentPakanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPakanBinding.inflate(layoutInflater)



        val jadwalPagi = "Pagi : 07.00-08.00 dan 09.00-10.00\n"
        val jadwalSiang = "Siang : 13.00-14.00 dan 15.00-16.00\n"
        val jadwalMalam = "Malam : 18.00-19.00 dan 20.00-21.00"

        val jadwal = "$jadwalPagi$jadwalSiang$jadwalMalam"

        binding.tvJadwal.text = jadwal

        return binding.root
    }

}