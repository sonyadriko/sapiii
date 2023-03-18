package com.example.sapiii

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentMutasiKambingBinding

class MutasiKambingFragment : BaseFragment() {

    private lateinit var binding: FragmentMutasiKambingBinding
    private lateinit var mutasiKambbingAdapter: MutasiKambingAdapter
    private val viewModel: MutasiKambingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMutasiKambingBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        initListener()
        setupRecyclerView()
        observe()
        onLoadMutasiKambing()




        return binding.root
    }

    private fun onLoadMutasiKambing() {
        viewModel.loadMutasiSapi()
        showProgressDialog()
    }

    private fun observe() {
        viewModel.mutasikambing.observe(viewLifecycleOwner) {
            dismissProgressDialog()
            mutasiKambbingAdapter.updateMutasiKambingList(it)
        }
    }

    private fun initListener() {
//        val btnAdd = binding.btnTambahDataMutasiSapi
        binding.btnTambahDataMutasi.setOnClickListener {
            startActivity(Intent(context, TambahDataMutasiActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        val mutasiKambingList = binding.mutasiKambingList
        mutasiKambingList.adapter = mutasiKambbingAdapter
    }


}