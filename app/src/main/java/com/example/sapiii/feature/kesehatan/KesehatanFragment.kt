package com.example.sapiii.feature.kesehatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sapiii.databinding.FragmentKesehatanBinding

/**
 * A simple [Fragment] subclass.
 * Use the [KesehatanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KesehatanFragment : Fragment() {
    private lateinit var binding: FragmentKesehatanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentKesehatanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}