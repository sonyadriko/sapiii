package com.example.sapiii.feature.kesehatan.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentKesehatanBinding
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.kesehatan.viewmodel.KesehatanViewModel
import com.example.sapiii.feature.kesehatan.viewmodel.KesehatanViewState
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

    private lateinit var checkboxes: List<CompoundButton>

    private val viewModel: KesehatanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentKesehatanBinding.inflate(layoutInflater, container, false)
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

    private fun initView() {
        binding.tagSapi.text = dataSapi.tag
        val isSehat: Int = if (dataSapi.kesehatan.sehat) 0 else 1
        binding.spinner.setSelection(isSehat)
        // checkboxes iku sebuah list of checkBox
        // list of checkbox iku index 0 sama dgn dosis 1
        // jika dosis 1 maka checkbox index 0 harus centang
        // maka vaksinDosis iku adalah index+1
        checkboxes.forEachIndexed { index, compoundButton ->
            if ((dataSapi.kesehatan.vaksinDosis - 1) == index) compoundButton.isChecked = true
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
                        this@KesehatanFragment.activity?.supportFragmentManager?.popBackStack()
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