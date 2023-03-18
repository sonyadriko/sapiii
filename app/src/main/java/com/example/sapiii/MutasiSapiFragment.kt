package com.example.sapiii

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentMutasiSapiBinding
import com.example.sapiii.feature.tips.view.TambhDataArtikelActivity
import com.example.sapiii.feature.tips.view.adapter.ArtikelAdapter
import com.example.sapiii.feature.tips.viewmodel.ArtikelViewModel

class MutasiSapiFragment : BaseFragment() {

    private lateinit var binding: FragmentMutasiSapiBinding
    private lateinit var mutasiSapiAdapter: MutasiSapiAdapter
    private lateinit var recyclerView: RecyclerView

    private val viewModel: MutasiSapiViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMutasiSapiBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        initListener()
        setupRecyclerView()
        observe()
        onLoadMutasiSapi()

        return binding.root
    }

    private fun onLoadMutasiSapi() {
        viewModel.loadMutasiSapi()
        showProgressDialog()
    }

    private fun observe() {
        viewModel.mutasisapi.observe(viewLifecycleOwner) {
            dismissProgressDialog()
            mutasiSapiAdapter.updateMutasiSapiList(it)
        }
    }

    private fun initListener() {
//        val btnAdd = binding.btnTambahDataMutasiSapi
        binding.btnTambahDataMutasiSapi.setOnClickListener{
            startActivity(Intent(context, TambahDataMutasiSapiActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
//        val mutasiSapiList = binding.mutasiSapiList
//        mutasiSapiList.adapter = mutasiSapiAdapter

        recyclerView = binding.mutasiSapiList
//        userRecyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setHasFixedSize(true)
        mutasiSapiAdapter = MutasiSapiAdapter()
        recyclerView.adapter = mutasiSapiAdapter
    }

}