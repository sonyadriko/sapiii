package com.example.sapiii.feature.detail.view

import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityDetailSapiBinding
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel
import com.example.sapiii.util.generateBarcode

class DetailSapiActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailSapiBinding
    private lateinit var namaSapi: String

    private val viewModel: DetailViewModel by viewModels()

    companion object {
        const val RESULT_DELETE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBundle()
        observe()
        initListener()
    }

    private fun initBundle() {
        namaSapi = intent.getStringExtra("namasapi") ?: ""
        if (namaSapi.isEmpty()) {
            viewModel.initBundle(DetailViewModel.Companion.DetailFeature.SAPI, intent?.data)
        } else viewModel.initBundle(DetailViewModel.Companion.DetailFeature.SAPI, namaSapi)
    }

    override fun onBackPressed() {
        if (namaSapi.isEmpty()) {
            goToHomepage()
        } else super.onBackPressed()
    }

    private fun observe() {
        viewModel.viewEffect.observe(this) {
            when (it) {
                is DetailViewModel.ViewEffect.OnDataDeleted -> {
                    if (it.isSuccess) {
                        setResult(RESULT_DELETE)
                        finish()
                    } else showToast("Gagal menghapus data")
                }
                is DetailViewModel.ViewEffect.OnDataGetResult -> {
                    setDetailSapi(it.data as Sapi)
                }
                is DetailViewModel.ViewEffect.ShowToast -> {
                    showToast(it.message)
                }
                is DetailViewModel.ViewEffect.ShowQrCode -> {
                    showQrCode(it.content)
                }
            }
        }

        viewModel.viewState.observe(this) {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (it) {
                DetailViewModel.ViewState.INITIAL -> {
                    dismissProgressDialog()
                }
                DetailViewModel.ViewState.LOADING -> {
                    showProgressDialog()
                }
                DetailViewModel.ViewState.FAIL -> {
                    finish()
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

    private fun setDetailSapi(sapi: Sapi?) = with(binding) {
        if (sapi != null) {
            Glide.with(ivSapi)
                .load(sapi.image)
                .placeholder(R.drawable.ic_outline_image_24)
                .into(ivSapi)
            namaSapiDetail.text = sapi.tag
            pmkSapiDetail.text = sapi.idpmk
            kandangSapiDetail.text = sapi.kodekandang
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