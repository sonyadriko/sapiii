package com.example.sapiii.feature.ternakku.sapi.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentListSapiBinding
import com.example.sapiii.feature.ternakku.sapi.viewmodel.SapiViewModel

class ListSapiFragment : BaseFragment() {

    private lateinit var binding: FragmentListSapiBinding
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var adapter: SapiAdapter

    private val viewModel: SapiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListSapiBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        setupRecycler()
        observe()
        loadSapi()
    }

    private fun loadSapi() {
        viewModel.loadSapi()
        showProgressDialog()
    }

    private fun initListener() {
        binding.btnTambahDataSapi.setOnClickListener {
            startActivity(Intent(activity, TambahDataSapiActivity::class.java))
        }
    }

    private fun setupRecycler() {
        userRecyclerView = binding.sapiList
        userRecyclerView.setHasFixedSize(true)
        adapter = SapiAdapter()
        userRecyclerView.adapter = adapter
    }

    private fun observe() {
        viewModel.sapi.observe(viewLifecycleOwner) {
            adapter.updateUserList(it)
            dismissProgressDialog()
        }
    }
}