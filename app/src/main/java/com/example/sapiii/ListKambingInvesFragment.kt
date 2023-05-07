package com.example.sapiii

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.FragmentListKambingInvesBinding
import com.example.sapiii.databinding.FragmentListSapiInvesBinding
import com.example.sapiii.domain.Kambing
import com.example.sapiii.feature.invest.DetailInvesmentActivity
import com.example.sapiii.feature.invest.SapiAdapter2
import com.example.sapiii.feature.ternakku.kambing.viewmodel.KambingViewModel
import com.example.sapiii.util.OnItemClick

class ListKambingInvesFragment : BaseFragment(), OnItemClick {

    private lateinit var adapter: KambingAdapter2
    private lateinit var binding: FragmentListKambingInvesBinding

    private val viewModel: KambingViewModel by viewModels()

    companion object {
        const val RESULT_DELETE = 10
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == DetailInvesmentActivity.RESULT_DELETE) {
                refreshListKambing()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListKambingInvesBinding.inflate(layoutInflater)

        adapter = KambingAdapter2(this)
        refreshListKambing()
        observe()

        return binding.root
    }

    private fun observe() {
        viewModel.kambing.observe(viewLifecycleOwner) { listKambing ->
            val kambingInves = listKambing.filter { it.data.status.equals(Constant.STATUS_SIAP_JUAL, true) }
            setupRecyclerView(kambingInves)
        }
    }

    private fun refreshListKambing() {
        showProgressDialog()
        viewModel.loadKambing()
    }

    private fun setupRecyclerView(data: List<Kambing>) {
        adapter.addAll(data)
        binding.kambingListInves.adapter = adapter
        dismissProgressDialog()
    }



    override fun onClick(data: Any, position: Int) {
        val currentItem = data as Kambing

        openWhatsApp(currentItem.pemilik.noTelepon, "Hai, Saya tertarik dengan Sapi ${data.tag}")
    }
}