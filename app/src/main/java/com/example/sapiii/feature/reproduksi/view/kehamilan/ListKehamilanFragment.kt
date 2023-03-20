package com.example.sapiii.feature.reproduksi.view.kehamilan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentListKehamilanBinding
import com.example.sapiii.feature.reproduksi.view.MonitoringKehamilanActivity
import com.example.sapiii.feature.reproduksi.view.MonitoringPejantanActivity

class ListKehamilanFragment : BaseFragment() {

    private lateinit var binding: FragmentListKehamilanBinding
    private lateinit var monitoringKehamilanAdapter: MonitoringKehamilanAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MonitoringKehamilanViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListKehamilanBinding.inflate(layoutInflater)


        initListener()
        setupRecyclerView()
        observe()
        onLoadMonitoringKehamilan()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun onLoadMonitoringKehamilan() {
        viewModel.loadMonitoringKehamilan()
        showProgressDialog()
    }

    private fun observe() {
        viewModel.monitoringkehamilan.observe(viewLifecycleOwner) {
            dismissProgressDialog()
            monitoringKehamilanAdapter.updateMonitoringPejantanList(it)
        }
    }

    private fun initListener() {
//        val btnAdd = binding.btnTambahDataMutasiSapi
        binding.btnTambahDataKehamilan.setOnClickListener {
            startActivity(Intent(context, MonitoringKehamilanActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
//        val monitoringPejantanList = binding.pejantanList
//        monitoringPejantanList.adapter = monitoringPejantanAdapter

        recyclerView = binding.kehamilanList
//        userRecyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setHasFixedSize(true)
        monitoringKehamilanAdapter = MonitoringKehamilanAdapter()
        recyclerView.adapter = monitoringKehamilanAdapter
    }

}