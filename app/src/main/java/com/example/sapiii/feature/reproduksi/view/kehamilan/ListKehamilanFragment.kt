package com.example.sapiii.feature.reproduksi.view.kehamilan

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
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentListKehamilanBinding
import com.example.sapiii.domain.MonitoringKehamilan
import com.example.sapiii.domain.MutasiHewan
import com.example.sapiii.feature.mutasi.kambing.DetailMutasiKambingActivity
import com.example.sapiii.feature.mutasi.sapi.DetailMutasiActivity
import com.example.sapiii.feature.reproduksi.view.MonitoringKehamilanActivity
import com.example.sapiii.feature.reproduksi.view.MonitoringPejantanActivity
import com.example.sapiii.util.OnItemClick

class ListKehamilanFragment : BaseFragment(), OnItemClick {

    private lateinit var binding: FragmentListKehamilanBinding
    private lateinit var monitoringKehamilanAdapter: MonitoringKehamilanAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MonitoringKehamilanViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == DetailMutasiActivity.RESULT_DELETE) {
                onLoadMonitoringKehamilan()
            }
        }


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
        monitoringKehamilanAdapter = MonitoringKehamilanAdapter(this)
        recyclerView.adapter = monitoringKehamilanAdapter
    }

    override fun onClick(data: Any, position: Int) {
        val currentItem = data as MonitoringKehamilan
        val detailIntent = Intent(context, EditKehamilanActivity::class.java).apply {
            putExtra("nama", currentItem.nama)
        }
        startForResult.launch(detailIntent)
    }

}