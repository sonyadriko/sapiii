package com.example.sapiii.feature.mutasi.kambing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.feature.mutasi.sapi.DetailMutasiActivity
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentMutasiKambingBinding
import com.example.sapiii.domain.MutasiHewan
import com.example.sapiii.util.OnItemClick

class MutasiKambingFragment : BaseFragment(), OnItemClick {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentMutasiKambingBinding
    private lateinit var mutasiKambbingAdapter: MutasiKambingAdapter
    private val viewModel: MutasiKambingViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == DetailMutasiActivity.RESULT_DELETE) {
                onLoadMutasiKambing()
            }
        }


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
//        val mutasiKambingList = binding.mutasiKambingList
//        mutasiKambingList.adapter = mutasiKambbingAdapter

        recyclerView = binding.mutasiKambingList
//        userRecyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setHasFixedSize(true)
        mutasiKambbingAdapter = MutasiKambingAdapter(this)
        recyclerView.adapter = mutasiKambbingAdapter
    }
    override fun onClick(data: Any, position: Int) {
        val currentItem = data as MutasiHewan
        val detailIntent = Intent(context, DetailMutasiKambingActivity::class.java).apply {
            putExtra("nama", currentItem.nama)
        }
        startForResult.launch(detailIntent)
    }


}