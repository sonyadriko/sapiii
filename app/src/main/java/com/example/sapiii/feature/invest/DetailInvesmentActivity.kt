package com.example.sapiii.feature.invest

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityDetailInvesmentBinding
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.example.sapiii.repository.KambingRepository
import com.example.sapiii.repository.SapiRepository
import com.example.sapiii.util.gone

class DetailInvesmentActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailInvesmentBinding

    private var dataSapi: Sapi? = null
    private var dataKambing: Kambing? = null

    private val sapiRepository = SapiRepository().getInstance()
    private val kambingRepository = KambingRepository().getInstance()

    companion object {
        const val RESULT_DELETE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInvesmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataSapi = intent.getSerializableExtra("datasapiinves") as Sapi?
        dataKambing = intent.getSerializableExtra("datakambinginves") as Kambing?

        initView()
        if (dataSapi != null) setDataSapi(dataSapi!!)
        if (dataKambing != null) setDataKambing(dataKambing!!)
        initListener()
    }

    private fun initView() {
        if (userRepository.role != Constant.Role.PETERNAK) {
            binding.btnSubmitHarga.gone()
            binding.inputHarga.isEnabled = false
        }

        val isSapi =
            if (dataSapi != null) true
            else if (dataKambing != null) false
            else false

        binding.isSapi = isSapi
    }

    private fun initListener() {
        binding.btnSubmitHarga.setOnClickListener {
            showProgressDialog()
            val harga = binding.inputHarga.text.toString().toInt()

            if (dataSapi != null) {
                sapiRepository.updateSapi(dataSapi!!.copy(harga = harga)) {
                    dismissProgressDialog()
                    finish()
                }
                return@setOnClickListener
            }
            if (dataKambing != null) {
                kambingRepository.updateKambing(dataKambing!!.copy(harga = harga)) {
                    dismissProgressDialog()
                    finish()
                }
                return@setOnClickListener
            }

            showToast("Error data ternak tidak ditemukan")
        }

        binding.ivWhatsapp.setOnClickListener {
            if (dataSapi != null) {
                openWhatsApp(
                    dataSapi!!.pemilik.noTelepon,
                    "Hai, Saya tertarik dengan Sapi ${dataSapi!!.tag}"
                )
                return@setOnClickListener
            }

            if (dataKambing != null) {
                openWhatsApp(
                    dataKambing!!.pemilik.noTelepon,
                    "Hai, Saya tertarik dengan Kambing ${dataKambing!!.tag}"
                )
                return@setOnClickListener
            }

            showToast("Error data ternak tidak ditemukan")
        }
    }

    private fun setDataSapi(sapi: Sapi) = with(binding) {
        val imageSapi = sapi.image
        Glide.with(this@DetailInvesmentActivity)
            .load(imageSapi)
            .placeholder(R.drawable.ic_outline_image_24)
            .into(ivSapi)
        namaSapiMutasi.text = sapi.tag
        inputHarga.setText(sapi.harga.toString())
    }

    private fun setDataKambing(kambing: Kambing) = with(binding) {
        val imageKambing = kambing.image
        Glide.with(this@DetailInvesmentActivity)
            .load(imageKambing)
            .placeholder(R.drawable.ic_outline_image_24)
            .into(ivSapi)
        namaSapiMutasi.text = kambing.tag
        inputHarga.setText(kambing.harga.toString())
    }
}