package com.example.sapiii.feature.mutasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sapiii.feature.mutasi.kambing.MutasiKambingFragment
import com.example.sapiii.feature.mutasi.sapi.MutasiSapiFragment
import com.example.sapiii.R
import com.example.sapiii.databinding.FragmentMutasi2Binding

class MutasiFragment2 : Fragment() {

    private lateinit var binding: FragmentMutasi2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMutasi2Binding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        binding.mutasiKambing.setOnClickListener {
            val listKambing = MutasiKambingFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, listKambing)
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.mutasiSapi.setOnClickListener {
            val listsapi = MutasiSapiFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, listsapi)
                ?.addToBackStack(null)
                ?.commit()
        }

        return binding.root
    }

}