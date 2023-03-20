package com.example.sapiii.feature.mutasi.sapi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.DetailMutasiActivity
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentMutasiSapiBinding
import com.example.sapiii.domain.Artikel
import com.example.sapiii.domain.MutasiSapi
import com.example.sapiii.feature.tips.view.DetailArtikelActivity
import com.example.sapiii.util.OnItemClick

class MutasiSapiFragment : BaseFragment(), OnItemClick {

    private lateinit var binding: FragmentMutasiSapiBinding
    private lateinit var mutasiSapiAdapter: MutasiSapiAdapter
    private lateinit var recyclerView: RecyclerView

    private val viewModel: MutasiSapiViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == DetailMutasiActivity.RESULT_DELETE) {
                onLoadMutasiSapi()
            }
        }


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
        mutasiSapiAdapter = MutasiSapiAdapter(this)
        recyclerView.adapter = mutasiSapiAdapter
    }

    override fun onClick(data: Any, position: Int) {
        val currentItem = data as MutasiSapi
        val detailIntent = Intent(context, DetailMutasiActivity::class.java).apply {
            putExtra("nama", currentItem.nama)
        }
        startForResult.launch(detailIntent)
    }

}