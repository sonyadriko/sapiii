package com.example.sapiii.feature.detail.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.sapiii.EditDataHewanActivity
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentDetailHewanBinding
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.detail.view.DetailHewanActivity.Companion.RESULT_DELETE
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel
import com.example.sapiii.util.generateBarcode
import com.example.sapiii.util.gone
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailHewanFragment : BaseFragment() {

    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailHewanBinding

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    delay(500)
                    viewModel.getDataHewan(true)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailHewanBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.buttonEditHewan.setOnClickListener{
            val editHewan = Intent(context, EditDataHewanActivity::class.java).apply {
                when (viewModel.feature) {
                    DetailViewModel.Companion.DetailFeature.SAPI -> putExtra("namasapi", viewModel.namaHewan)
                    DetailViewModel.Companion.DetailFeature.KAMBING -> putExtra("namakambing", viewModel.namaHewan)
                    DetailViewModel.Companion.DetailFeature.UNKNOWN -> {
                        // no operation
                    }
                }
            }
            startForResult.launch(editHewan)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initListener()
        viewModel.getDataHewan()
    }

    private fun observe() {
        viewModel.viewEffect.observe(viewLifecycleOwner) {
            when (it) {
                is DetailViewModel.ViewEffect.OnDataDeleted -> {
                    if (it.isSuccess) {
                        activity?.apply {
                            setResult(RESULT_DELETE)
                            finish()
                        }
                    } else showToast("Gagal menghapus data")
                }
                is DetailViewModel.ViewEffect.OnDataGetResult -> {
                    showQrCode(it.qrContent)

                    if (it.data is Sapi?) setDetailSapi(it.data)
                    else if (it.data is Kambing?) setDetailKambing(it.data)
                }
                is DetailViewModel.ViewEffect.ShowToast -> {
                    showToast(it.message)
                }
            }
        }
    }

    private fun initListener() = with(binding) {
        buttonDelete.setOnClickListener {
            alertDialog
                .setMessage("Apakah anda yakin ingin menghapus data?")
                .setPositiveButton("Ya") { _, _ -> viewModel.deleteHewan() }
                .setNegativeButton("Tidak") { p0, _ -> p0.dismiss() }
                .show()

        }
    }

    private fun showQrCode(content: String) {
        binding.ivQrCode.generateBarcode(content)
    }

    private fun setDetailKambing(kambing: Kambing?) = with(binding) {
        if (kambing != null) {
            Glide.with(ivSapi)
                .load(kambing.image)
                .placeholder(R.drawable.ic_outline_image_24)
                .into(ivSapi)
            namaSapiDetail.text = kambing.tag
            jkSapiDetail.text = kambing.kelamin
            jenisSapiDetail.text = kambing.jenis
            pmkSapiDetail.text = kambing.idpmk
            kandangSapiDetail.text = kambing.kodekandang
            kdtgSapiDetail.text = kambing.kedatangan.kedatanganHewan
            bbawalSapiDetail.text = kambing.kedatangan.beratBadanAwal
            usiadtgSapiDetail.text = kambing.kedatangan.usiaKedatangan
            usiaskgSapiDetail.text = kambing.data.usia
            bbskgSapiDetail.text = kambing.data.beratBadan.toString()
            sttsSapiDetail.text = kambing.data.status
            asalSapiDetail.text = kambing.asal

            tvNamaPemilik.text = kambing.pemilik.nama
            tvNomorTelpon.text = kambing.pemilik.noTelepon
            tvAlamatPemilik.text = kambing.pemilik.alamat
        } else showToast("Kambing is not found")
    }

    private fun setDetailSapi(sapi: Sapi?) = with(binding) {
        if (sapi != null) {
            Glide.with(ivSapi)
                .load(sapi.image)
                .placeholder(R.drawable.ic_outline_image_24)
                .into(ivSapi)
            asalSapiContainer.gone()

            namaSapiDetail.text = sapi.tag
            jkSapiDetail.text = sapi.kelamin
            jenisSapiDetail.text = sapi.jenis
            pmkSapiDetail.text = sapi.idpmk
            kandangSapiDetail.text = sapi.kodekandang
            kdtgSapiDetail.text = sapi.kedatangan.kedatanganHewan
            bbawalSapiDetail.text = sapi.kedatangan.beratBadanAwal
            usiadtgSapiDetail.text = sapi.kedatangan.usiaKedatangan
            usiaskgSapiDetail.text = sapi.data.usia
            bbskgSapiDetail.text = sapi.data.beratBadan.toString()
            sttsSapiDetail.text = sapi.data.status

            tvNamaPemilik.text = sapi.pemilik.nama
            tvNomorTelpon.text = sapi.pemilik.noTelepon
            tvAlamatPemilik.text = sapi.pemilik.alamat
        } else showToast("data Sapi tidak ditemukan")
    }

}