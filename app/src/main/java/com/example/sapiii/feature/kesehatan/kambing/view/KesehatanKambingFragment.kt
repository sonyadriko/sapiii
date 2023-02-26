package com.example.sapiii.feature.kesehatan.kambing.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentKesehatanBinding
import com.example.sapiii.databinding.FragmentKesehatanKambingBinding
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.kesehatan.kambing.viewmodel.KesehatanKambingViewModel
import com.example.sapiii.feature.kesehatan.kambing.viewmodel.KesehatanKambingViewState
import com.example.sapiii.feature.kesehatan.sapi.view.KesehatanFragment
import com.example.sapiii.feature.kesehatan.sapi.viewmodel.KesehatanViewModel
import com.example.sapiii.feature.kesehatan.sapi.viewmodel.KesehatanViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class KesehatanKambingFragment : BaseFragment() {

    private lateinit var binding: FragmentKesehatanKambingBinding
    private lateinit var dataKambing: Kambing

    private lateinit var checkboxes: List<CompoundButton>

    private val viewModel: KesehatanKambingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentKesehatanKambingBinding.inflate(layoutInflater, container, false)
        checkboxes =
            listOf(binding.checkboxVaccine1, binding.checkboxVaccine2, binding.checkboxVaccine3)
        initBundle()
        initView()
        initListener()
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                KesehatanKambingViewState.LOADING -> {
                    showProgressDialog()
                }
                KesehatanKambingViewState.INITIAL -> {
                    dismissProgressDialog()
                }
            }
        }
    }
    private fun initBundle() {
        val arg = arguments?.getParcelable<Kambing>(KesehatanKambingFragment.ARG_KAMBING)
        if (arg != null) {
            dataKambing = arg
            viewModel.initDataKambing(arg)
        } else {
            showToast()
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun initView() {
        binding.tagKambing.text = dataKambing.tag
        val isSehat: Int = if (dataKambing.kesehatan.sehat) 0 else 1
        binding.spinner.setSelection(isSehat)
        // checkboxes iku sebuah list of checkBox
        // list of checkbox iku index 0 sama dgn dosis 1
        // jika dosis 1 maka checkbox index 0 harus centang
        // maka vaksinDosis iku adalah index+1
        checkboxes.forEachIndexed { index, compoundButton ->
            if ((dataKambing.kesehatan.vaksinDosis - 1) == index) compoundButton.isChecked = true
        }
    }

    private fun initListener() = with(binding) {
        checkboxVaccine1.onCheck(1)
        checkboxVaccine2.onCheck(2)
        checkboxVaccine3.onCheck(3)

        btnSubmit.setOnClickListener {
            viewModel.updateKesehatan(sehat = spinner.selectedItemPosition == 0) {
                if (it) {
                    showToast("Sukses")
                    lifecycleScope.launch {
                        delay(500L)
                        this@KesehatanKambingFragment.activity?.supportFragmentManager?.popBackStack()
                    }
                } else showToast("Gagal update data")
            }
        }
    }

    private fun CompoundButton.onCheck(dosis: Int) {
        setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                viewModel.updateDosis(dosis)
                checkboxes.forEach {
                    if (it != compoundButton) it.isChecked = false
                }
            }
        }
    }
    companion object {
        private const val ARG_KAMBING = "kambing_arg"

        fun newInstance(data: Kambing): KesehatanKambingFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARG_KAMBING, data)
            return KesehatanKambingFragment().apply {
                arguments = bundle
            }
        }
    }

}