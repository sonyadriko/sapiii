package com.example.sapiii.feature.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentDetailGrowthBinding
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel

class DetailGrowthFragment : BaseFragment() {

    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailGrowthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailGrowthBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}