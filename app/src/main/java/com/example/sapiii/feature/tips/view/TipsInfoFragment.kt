package com.example.sapiii.feature.tips.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentTipsInfoBinding
import com.example.sapiii.feature.tips.viewmodel.ArtikelViewModel
import com.example.sapiii.util.OnItemClick

class TipsInfoFragment : BaseFragment(), OnItemClick {
    private lateinit var binding: FragmentTipsInfoBinding
    private lateinit var artikelAdapter: ArtikelAdapter
    private val viewModel: ArtikelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artikelAdapter = ArtikelAdapter(this@TipsInfoFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTipsInfoBinding.inflate(layoutInflater, container, false)

        initListener()
        setupRecyclerView()
        observe()
        onLoadArtikel()
        return binding.root
    }

    private fun onLoadArtikel() {
        viewModel.loadArtikel()
        showProgressDialog()
    }

    private fun observe() {
        viewModel.artikel.observe(viewLifecycleOwner) {
            dismissProgressDialog()
            artikelAdapter.updateArtikelList(it)
        }
    }

    private fun initListener() {
        val btnAdd = binding.btnTambahDataArtikel
        btnAdd.setOnClickListener {
            startActivity(Intent(activity, TambhDataArtikelActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        val artikelList = binding.artikelList
        artikelList.adapter = artikelAdapter
    }

    override fun onClick(data: Any, position: Int) {
        showToast("Artikel")
    }
}