package com.example.sapiii.feature.invest

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityDetailInvesmentBinding
import com.example.sapiii.repository.SapiRepository
import com.example.sapiii.util.gone
import com.google.firebase.database.*

class DetailInvesmentActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailInvesmentBinding
    private lateinit var sapiRef: DatabaseReference
    private lateinit var namaSapi: String

    private val sapiRepository = SapiRepository().getInstance()

    companion object {
        const val RESULT_DELETE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInvesmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sapiRef = database.getReference(Constant.REFERENCE_SAPI)
        namaSapi = intent.getStringExtra("namasapiinves") ?: ""

        initView()
        getDetailSapi()
        initListener()
    }

    private fun initView() {
        if (userRepository.role != Constant.Role.PETERNAK) {
            binding.btnSubmitHarga.gone()
            binding.inputHarga.isEnabled = false
        }
    }

    private fun initListener() {
        binding.btnSubmitHarga.setOnClickListener {
            val hargaSapi = binding.inputHarga.text.toString().toInt()
            val updates = mapOf<String, Any>("harga" to hargaSapi)

            sapiRef.child(namaSapi).updateChildren(updates)
            finish()
        }
    }

    private fun getDetailSapi() = with(binding) {
        showProgressDialog()
        sapiRepository.getSapiDetail(
            namaSapi,
            onComplete = { sapi ->
                dismissProgressDialog()
                val imageSapi = sapi.image
                Glide.with(this@DetailInvesmentActivity)
                    .load(imageSapi)
                    .placeholder(R.drawable.ic_outline_image_24)
                    .into(ivSapi)
                namaSapiMutasi.text = sapi.tag
                inputHarga.setText(sapi.harga.toString())
            },
            onError = {
                dismissProgressDialog()
                showToast("error: ${it.message}")
            })
    }
}