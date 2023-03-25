package com.example.sapiii.feature.invest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.constanst.Constant.STATUS_SIAP_JUAL
import com.example.sapiii.databinding.FragmentListSapiInvesBinding
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.ternakku.sapi.viewmodel.SapiViewModel
import com.example.sapiii.util.OnItemClick

class ListSapiInvesFragment : BaseFragment(), OnItemClick {

    private lateinit var adapter: SapiAdapter2
    private lateinit var binding: FragmentListSapiInvesBinding

    private val viewModel: SapiViewModel by viewModels()

    companion object {
        const val RESULT_DELETE = 10
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == DetailInvesmentActivity.RESULT_DELETE) {
                refreshListSapi()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListSapiInvesBinding.inflate(layoutInflater)

        adapter = SapiAdapter2(this)
        refreshListSapi()
        observe()

        return binding.root
    }

    private fun observe() {
        viewModel.sapi.observe(viewLifecycleOwner) { listSapi ->
            val sapiInves = listSapi.filter { it.data.status.equals(STATUS_SIAP_JUAL, true) }
            setupRecyclerView(sapiInves)
        }
    }

    private fun refreshListSapi() {
        showProgressDialog()
        viewModel.loadSapi()
    }

    private fun setupRecyclerView(data: List<Sapi>) {
        adapter.addAll(data)
        binding.sapiListInves.adapter = adapter
        dismissProgressDialog()
    }

    override fun onClick(data: Any, position: Int) {
        val currentItem = data as Sapi
        val detailIntent = Intent(context, DetailInvesmentActivity::class.java).apply {
            putExtra("namasapiinves", currentItem.tag)
        }
        startForResult.launch(detailIntent)
    }
}