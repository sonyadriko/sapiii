package com.example.sapiii.feature.ternakku.kambing.view

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
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.FragmentListKambingBinding
import com.example.sapiii.domain.Kambing
import com.example.sapiii.feature.detail.view.DetailHewanActivity
import com.example.sapiii.feature.detail.view.DetailHewanActivity.Companion.RESULT_DELETE
import com.example.sapiii.feature.kesehatan.kambing.view.KesehatanKambingFragment
import com.example.sapiii.feature.ternakku.kambing.view.adapter.KambingAdapter
import com.example.sapiii.feature.ternakku.kambing.viewmodel.KambingViewModel
import com.example.sapiii.util.OnItemClick
import com.example.sapiii.util.gone
import com.example.sapiii.util.visible

class ListKambingFragment : BaseFragment(), OnItemClick {

    private lateinit var binding: FragmentListKambingBinding
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var adapter: KambingAdapter

    private lateinit var from: String

    private val viewModel: KambingViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_DELETE) {
                loadKambing()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListKambingBinding.inflate(layoutInflater, container, false)
        from = arguments?.getString(ListKambingFragment.ARG_FROM).toString()
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        setupRecycler()
        observe()
        loadKambing()
    }

    private fun loadKambing() {
        viewModel.loadKambing()
        showProgressDialog()
    }

    private fun initView() {
        when (from) {
            ARG_FROM_KESEHATAN, ARG_FROM_PAKAN, ARG_FROM_TIMBANGAN, ARG_FROM_INVES -> {
                binding.btnTambahDataKambing.gone()
            }
            else -> {
                if (userRepository.role == Constant.Role.PETERNAK) {
                    binding.btnTambahDataKambing.visible()
                } else binding.btnTambahDataKambing.gone()
            }
        }
    }

    private fun setupRecycler() {
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        userRecyclerView = binding.kambingList
        userRecyclerView.addItemDecoration(dividerItemDecoration)
        userRecyclerView.setHasFixedSize(true)
        adapter = KambingAdapter(this)
        userRecyclerView.adapter = adapter
    }

    private fun observe() {
        viewModel.kambing.observe(viewLifecycleOwner) {
            adapter.updateUserList(it)
            dismissProgressDialog()
        }
    }

    private fun initListener() {
        binding.btnTambahDataKambing.setOnClickListener {
            startActivity(Intent(activity, TambahDataKambingActivity::class.java))
        }
    }

    companion object {
        private const val ARG_FROM = "from_arg"
        private const val ARG_FROM_TERNAK = "from_ternakku_arg"
        private const val ARG_FROM_KESEHATAN = "from_kesehatan_arg"
        private const val ARG_FROM_PAKAN = "from_pakan_arg"
        private const val ARG_FROM_TIMBANGAN = "from_timbangan_arg"
        private const val ARG_FROM_INVES = "from_inves_arg"

        fun fromTernakku(): ListKambingFragment {
            val bundle = Bundle().also {
                it.putString(ARG_FROM, ARG_FROM_TERNAK)
            }
            return ListKambingFragment().apply {
                arguments = bundle
            }
        }

        fun fromInves(): ListKambingFragment {
            val bundle = Bundle().also {
                it.putString(ARG_FROM, ARG_FROM_INVES)
            }
            return ListKambingFragment().apply {
                arguments = bundle
            }
        }

        fun fromKesehatan(): ListKambingFragment {
            val bundle = Bundle().also {
                it.putString(ARG_FROM, ARG_FROM_KESEHATAN)
            }
            return ListKambingFragment().apply {
                arguments = bundle
            }
        }

        fun fromTimbangan(): ListKambingFragment {
            val bundle = Bundle().also {
                it.putString(ARG_FROM, ARG_FROM_TIMBANGAN)
            }
            return ListKambingFragment().apply {
                arguments = bundle
            }
        }

        fun fromPakan(): ListKambingFragment {
            val bundle = Bundle().also {
                it.putString(ARG_FROM, ARG_FROM_PAKAN)
            }
            return ListKambingFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onClick(data: Any, position: Int) {
        val currentItem = data as Kambing
        when (from) {
            ARG_FROM_TERNAK -> {
                val detailIntent = Intent(context, DetailHewanActivity::class.java).apply {
                    putExtra("namakambing", currentItem.tag)
                }
                startForResult.launch(detailIntent)
            }
            ARG_FROM_KESEHATAN -> {
                val kesehatanKambingFragment = KesehatanKambingFragment.newInstance(currentItem)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frame_layout, kesehatanKambingFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }

            else -> showToast()
        }
    }


}