package com.example.sapiii.feature.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentDetailGrowthBinding
import com.example.sapiii.databinding.FragmentDetailHewanBinding
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.detail.view.DetailHewanActivity.Companion.RESULT_DELETE
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel
import com.example.sapiii.util.generateBarcode

class DetailGrowthFragment : BaseFragment() {

    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailGrowthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailGrowthBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initListener()
    }

    private fun observe() {
        viewModel.viewEffect.observe(viewLifecycleOwner) {
            when (it) {
                is DetailViewModel.ViewEffect.OnDataDeleted -> {
                    if (it.isSuccess) {
                        activity?.apply {
                            setResult(RESULT_DELETE)
                            finish()
                        }
                    } else showToast("Gagal menghapus data")
                }
                is DetailViewModel.ViewEffect.OnDataGetResult -> {
//                    if (it.data is Sapi?) setDetailSapi(it.data)
//                    else if (it.data is Kambing?) setDetailKambing(it.data)
                }
                is DetailViewModel.ViewEffect.ShowToast -> {
                    showToast(it.message)
                }
                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun initListener() = with(binding) {
        buttonDelete.setOnClickListener {
            viewModel.deleteHewan()
        }
    }
}