package com.example.sapiii.feature.invest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.constanst.Constant.STATUS_SIAP_JUAL
import com.example.sapiii.databinding.FragmentListSapiInvesBinding
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.ternakku.sapi.viewmodel.SapiViewModel
import com.example.sapiii.util.OnItemClick

class ListSapiInvesFragment : BaseFragment(), OnItemClick {

    private lateinit var adapterSapi: SapiAdapter2
    private lateinit var adapterKambing: KambingAdapter2
    private lateinit var binding: FragmentListSapiInvesBinding

    private var featureExtra: String? = null

    private val viewModel: SapiViewModel by viewModels()

    companion object {
        const val RESULT_DELETE = 10
        private const val FEATURE_EXTRA = "feature_for"

        fun newInstance(featureName: String): ListSapiInvesFragment {
            val bundle = Bundle()
            bundle.putString(FEATURE_EXTRA, featureName)
            return ListSapiInvesFragment().apply {
                arguments = bundle
            }
        }
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == DetailInvesmentActivity.RESULT_DELETE) {
                refreshDataTernak()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListSapiInvesBinding.inflate(layoutInflater)

        adapterSapi = SapiAdapter2(this)
        adapterKambing = KambingAdapter2(this)

        featureExtra = arguments?.getString(FEATURE_EXTRA)
        initView()
        refreshDataTernak()
        observe()

        return binding.root
    }

    private fun initView() = with(binding) {
        when (featureExtra) {
            "sapi" -> {
                cover.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_cow, null))
                title.text = getString(R.string.katalog_sapi)
            }
            "kambing" -> {
                cover.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_goat, null))
                title.text = getString(R.string.katalog_kambing)
            }
            else -> {
                dismissProgressDialog()
                showToast()
            }
        }
    }

    private fun observe() {
        viewModel.sapi.observe(viewLifecycleOwner) { listSapi ->
            val sapiInves = listSapi.filter { it.data.status.equals(STATUS_SIAP_JUAL, true) }
            adapterSapi.addAll(sapiInves)
            binding.sapiListInves.adapter = adapterSapi
            dismissProgressDialog()
        }

        viewModel.kambing.observe(viewLifecycleOwner) { listKambing ->
            val kambingInves = listKambing.filter { it.data.status.equals(STATUS_SIAP_JUAL, true) }
            adapterKambing.addAll(kambingInves)
            binding.sapiListInves.adapter = adapterKambing
            dismissProgressDialog()
        }
    }

    private fun refreshDataTernak() {
        showProgressDialog()
        when (featureExtra) {
            "sapi" -> viewModel.loadSapi()
            "kambing" -> viewModel.loadKambing()
            else -> {
                dismissProgressDialog()
                showToast()
            }
        }
    }

    override fun onClick(data: Any, position: Int) {
        val extraName = when (data) {
            is Sapi -> "datasapiinves"
            is Kambing -> "datakambinginves"
            else -> ""
        }

        val detailIntent = Intent(context, DetailInvesmentActivity::class.java).apply {
            putExtra(extraName, data as java.io.Serializable)
        }
        startForResult.launch(detailIntent)
    }
}