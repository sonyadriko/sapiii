package com.example.sapiii

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sapiii.databinding.FragmentKesehatan2Binding
import com.example.sapiii.feature.ternakku.kambing.view.ListKambingFragment
import com.example.sapiii.feature.ternakku.sapi.view.ListSapiFragment
import kotlinx.android.synthetic.main.fragment_kesehatan2.*

class KesehatanFragment : Fragment() {

    private lateinit var binding: FragmentKesehatan2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKesehatan2Binding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        binding.kesehatanSapi.setOnClickListener {
            val listSapi = ListSapiFragment.fromKesehatan()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, listSapi)?.addToBackStack(null)?.commit()
        }

        binding.kesehatanKambing.setOnClickListener {
            val listKambing = ListKambingFragment.fromKesehatan()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, listKambing)?.addToBackStack(null)?.commit()
        }

        return binding.root
    }


}