package com.example.sapiii.feature.kesehatan.sapi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.FragmentKesehatanBinding
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.kesehatan.sapi.viewmodel.KesehatanViewModel
import com.example.sapiii.feature.kesehatan.sapi.viewmodel.KesehatanViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [KesehatanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KesehatanFragment : BaseFragment() {
    private lateinit var binding: FragmentKesehatanBinding
    private lateinit var dataSapi: Sapi

    private val viewModel: KesehatanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentKesehatanBinding.inflate(layoutInflater, container, false)
        initBundle()
        initView()
        initListener()
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                KesehatanViewState.LOADING -> {
                    showProgressDialog()
                }
                KesehatanViewState.INITIAL -> {
                    dismissProgressDialog()
                }
            }
        }
    }

    private fun initBundle() {
        val arg = arguments?.getParcelable<Sapi>(ARG_SAPI)
        if (arg != null) {
            dataSapi = arg
            viewModel.initDataSapi(arg)
        } else {
            showToast()
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun initView() = with(binding) {
        if (userRepository.role != Constant.Role.PETERNAK) {
            btnSubmit.isEnabled = false
            etKeterangnKesehatan.isEnabled = false
            checkboxVaccine1.isEnabled = false
            checkboxVaccine2.isEnabled = false
            checkboxVaccine3.isEnabled = false
        }

        tagSapi.text = dataSapi.tag
        etKeterangnKesehatan.setText(dataSapi.kesehatan.keterangan)
        val isSehat: Int = if (dataSapi.kesehatan.sehat) 0 else 1
        spinner.setSelection(isSehat)

        // checkbox vaksin
        checkboxVaccine1.isChecked = dataSapi.kesehatan.vaksinDosis1
        checkboxVaccine2.isChecked = dataSapi.kesehatan.vaksinDosis2
        checkboxVaccine3.isChecked = dataSapi.kesehatan.vaksinDosis3
    }

    private fun initListener() = with(binding) {
        checkboxVaccine1.onCheck(1)
        checkboxVaccine2.onCheck(2)
        checkboxVaccine3.onCheck(3)

        btnSubmit.setOnClickListener {
            viewModel.updateKesehatan(
                sehat = spinner.selectedItemPosition == 0,
                keterangan = "${etKeterangnKesehatan.text ?: ""}"
            ) {
                if (it) {
                    showToast("Sukses")
                    lifecycleScope.launch {
                        delay(500L)
                        this@KesehatanFragment.activity?.supportFragmentManager?.popBackStack()
                    }
                } else showToast("Gagal update data")
            }
        }
    }

    private fun CompoundButton.onCheck(dosis: Int) {
        setOnCheckedChangeListener { _, b ->
            viewModel.updateDosis(dosis, b)
        }
    }

    companion object {
        private const val ARG_SAPI = "sapi_arg"

        fun newInstance(data: Sapi): KesehatanFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARG_SAPI, data)
            return KesehatanFragment().apply {
                arguments = bundle
            }
        }
    }
}