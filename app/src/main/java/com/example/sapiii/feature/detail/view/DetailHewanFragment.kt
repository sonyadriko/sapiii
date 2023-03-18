package com.example.sapiii.feature.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentDetailHewanBinding
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.detail.view.DetailHewanActivity.Companion.RESULT_DELETE
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel
import com.example.sapiii.util.generateBarcode

class DetailHewanFragment : BaseFragment() {

    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailHewanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailHewanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initListener()
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
                    if (it.data is Sapi?) setDetailSapi(it.data)
                    else if (it.data is Kambing?) setDetailKambing(it.data)
                }
                is DetailViewModel.ViewEffect.ShowToast -> {
                    showToast(it.message)
                }
                is DetailViewModel.ViewEffect.ShowQrCode -> {
                    showQrCode(it.content)
                }
            }
        }
    }

    private fun initListener() = with(binding) {
        buttonDelete.setOnClickListener {
            viewModel.deleteHewan()
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
            kdtgSapiDetail.text = kambing.kedatangan.bulan
            bbawalSapiDetail.text = kambing.kedatangan.beratBadanAwal
            usiadtgSapiDetail.text = kambing.kedatangan.usia
            usiaskgSapiDetail.text = kambing.data.usia
            bbskgSapiDetail.text = kambing.data.berat
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
            namaSapiDetail.text = sapi.tag
            jkSapiDetail.text = sapi.kelamin
            jenisSapiDetail.text = sapi.jenis
            kdtgSapiDetail.text = sapi.kedatangan.bulan
            bbawalSapiDetail.text = sapi.kedatangan.beratBadanAwal
            usiadtgSapiDetail.text = sapi.kedatangan.usia
            usiaskgSapiDetail.text = sapi.data.usia
            bbskgSapiDetail.text = sapi.data.berat
            sttsSapiDetail.text = sapi.data.status
            asalSapiDetail.text = sapi.asal

            tvNamaPemilik.text = sapi.pemilik.nama
            tvNomorTelpon.text = sapi.pemilik.noTelepon
            tvAlamatPemilik.text = sapi.pemilik.alamat
        } else showToast("data Sapi tidak ditemukan")
    }

}