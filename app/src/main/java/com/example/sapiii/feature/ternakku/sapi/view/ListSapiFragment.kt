package com.example.sapiii.feature.ternakku.sapi.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentListSapiBinding
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.detail.view.DetailHewanActivity
import com.example.sapiii.feature.detail.view.DetailHewanActivity.Companion.RESULT_DELETE
import com.example.sapiii.feature.kesehatan.sapi.view.KesehatanFragment
import com.example.sapiii.feature.ternakku.sapi.view.adapter.SapiAdapter
import com.example.sapiii.feature.ternakku.sapi.viewmodel.SapiViewModel
import com.example.sapiii.util.OnItemClick
import com.example.sapiii.util.gone
import com.example.sapiii.util.visible

class ListSapiFragment : BaseFragment(), OnItemClick {

    private lateinit var binding: FragmentListSapiBinding
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var adapter: SapiAdapter

    private lateinit var from: String

    private val viewModel: SapiViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_DELETE) {
                loadSapi()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListSapiBinding.inflate(layoutInflater, container, false)
        from = arguments?.getString(ARG_FROM).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        setupRecycler()
        observe()
        loadSapi()
    }

    private fun initView() {
        if (from == ARG_FROM_KESEHATAN) {
            binding.btnTambahDataSapi.gone()
        } else if (from == ARG_FROM_PAKAN) {
            binding.btnTambahDataSapi.gone()
        } else if (from == ARG_FROM_TIMBANGAN) {
            binding.btnTambahDataSapi.gone()
        } else if (from == ARG_FROM_INVES) {
            binding.btnTambahDataSapi.gone()
        } else
            binding.btnTambahDataSapi.visible()
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
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        userRecyclerView = binding.sapiList
        userRecyclerView.addItemDecoration(dividerItemDecoration)
        userRecyclerView.setHasFixedSize(true)
        adapter = SapiAdapter(this)
        userRecyclerView.adapter = adapter
    }

    private fun observe() {
        viewModel.sapi.observe(viewLifecycleOwner) {
            adapter.updateUserList(it)
            dismissProgressDialog()
        }
    }

    override fun onClick(data: Any, position: Int) {
        val currentItem = data as Sapi
        when (from) {
            ARG_FROM_TERNAK -> {
                val detailIntent = Intent(context, DetailHewanActivity::class.java).apply {
                    putExtra("namasapi", currentItem.tag)
                }
                startForResult.launch(detailIntent)
            }
            ARG_FROM_KESEHATAN -> {
                val kesehatanFragment = KesehatanFragment.newInstance(currentItem)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frame_layout, kesehatanFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
            ARG_FROM_INVES -> {

            }
            else -> showToast()
        }
    }

    companion object {
        private const val ARG_FROM = "from_arg"
        private const val ARG_FROM_TERNAK = "from_ternakku_arg"
        private const val ARG_FROM_KESEHATAN = "from_kesehatan_arg"
        private const val ARG_FROM_PAKAN = "from_pakan_arg"
        private const val ARG_FROM_TIMBANGAN = "from_timbangan_arg"
        private const val ARG_FROM_INVES = "from_inves_arg"

        fun fromTernakku(): ListSapiFragment {
            val bundle = Bundle().also {
                it.putString(ARG_FROM, ARG_FROM_TERNAK)
            }
            return ListSapiFragment().apply {
                arguments = bundle
            }
        }

        fun fromInves(): ListSapiFragment {
            val bundle = Bundle().also {
                it.putString(ARG_FROM, ARG_FROM_INVES)
            }
            return ListSapiFragment().apply {
                arguments = bundle
            }
        }

        fun fromKesehatan(): ListSapiFragment {
            val bundle = Bundle().also {
                it.putString(ARG_FROM, ARG_FROM_KESEHATAN)
            }
            return ListSapiFragment().apply {
                arguments = bundle
            }
        }

        fun fromTimbangan(): ListSapiFragment {
            val bundle = Bundle().also {
                it.putString(ARG_FROM, ARG_FROM_TIMBANGAN)
            }
            return ListSapiFragment().apply {
                arguments = bundle
            }
        }

        fun fromPakan(): ListSapiFragment {
            val bundle = Bundle().also {
                it.putString(ARG_FROM, ARG_FROM_PAKAN)
            }
            return ListSapiFragment().apply {
                arguments = bundle
            }
        }
    }
}