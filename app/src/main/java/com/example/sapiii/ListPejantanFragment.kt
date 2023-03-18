package com.example.sapiii

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentListPejantanBinding
import com.example.sapiii.databinding.FragmentMutasiKambingBinding
import com.example.sapiii.feature.reproduksi.pejantan.MonitoringPejantanActivity
import com.example.sapiii.feature.ternakku.kambing.view.adapter.KambingAdapter
import com.example.sapiii.feature.tips.view.adapter.ArtikelAdapter

class ListPejantanFragment : BaseFragment() {

    private lateinit var binding: FragmentListPejantanBinding
    private lateinit var monitoringPejantanAdapter: MonitoringPejantanAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MonitoringPejantanViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListPejantanBinding.inflate(layoutInflater)


        initListener()
        setupRecyclerView()
        observe()
        onLoadMonitoringPejantan()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun onLoadMonitoringPejantan() {
        viewModel.loadMonitoringPejantan()
        showProgressDialog()
    }

    private fun observe() {
        viewModel.monitoringpejantan.observe(viewLifecycleOwner) {
            dismissProgressDialog()
            monitoringPejantanAdapter.updateMonitoringPejantanList(it)
        }
    }

    private fun initListener() {
//        val btnAdd = binding.btnTambahDataMutasiSapi
        binding.btnTambahDataPejantan.setOnClickListener {
            startActivity(Intent(context, MonitoringPejantanActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
//        val monitoringPejantanList = binding.pejantanList
//        monitoringPejantanList.adapter = monitoringPejantanAdapter

        recyclerView = binding.pejantanList
//        userRecyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setHasFixedSize(true)
        monitoringPejantanAdapter = MonitoringPejantanAdapter()
        recyclerView.adapter = monitoringPejantanAdapter
    }

}