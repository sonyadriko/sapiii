package com.example.sapiii.feature.reproduksi.view.pejantan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.EditKehamilanActivity
import com.example.sapiii.EditPejantanActivity
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentListPejantanBinding
import com.example.sapiii.domain.MonitoringKehamilan
import com.example.sapiii.domain.MonitoringPejantan
import com.example.sapiii.feature.mutasi.sapi.DetailMutasiActivity
import com.example.sapiii.feature.reproduksi.view.MonitoringPejantanActivity
import com.example.sapiii.util.OnItemClick

class ListPejantanFragment : BaseFragment(), OnItemClick {

    private lateinit var binding: FragmentListPejantanBinding
    private lateinit var monitoringPejantanAdapter: MonitoringPejantanAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MonitoringPejantanViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == DetailMutasiActivity.RESULT_DELETE) {
                onLoadMonitoringPejantan()
            }
        }

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
        monitoringPejantanAdapter = MonitoringPejantanAdapter(this)
        recyclerView.adapter = monitoringPejantanAdapter
    }

    override fun onClick(data: Any, position: Int) {
        val currentItem = data as MonitoringPejantan
        val detailIntent = Intent(context, EditPejantanActivity::class.java).apply {
            putExtra("nama", currentItem.nama)
        }
        startForResult.launch(detailIntent)
    }

}