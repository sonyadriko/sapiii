package com.example.sapiii.feature.ternakku.sapi.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityTambahDataSapiBinding
import com.example.sapiii.domain.DataSapi
import com.example.sapiii.domain.Kedatangan
import com.example.sapiii.domain.Pemilik
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.ternakku.sapi.viewmodel.TambahDataSapiViewModel
import com.example.sapiii.util.toFloatNew

class TambahDataSapiActivity : BaseActivity() {

    private lateinit var binding: ActivityTambahDataSapiBinding
    private val viewModel: TambahDataSapiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKelaminDropdown()
        setStatusDropdown()
        initListener()
    }

    private fun setStatusDropdown() {
        val adapter =
            ArrayAdapter(this@TambahDataSapiActivity, R.layout.list_item, viewModel.statusList)
        binding.etStatus.setAdapter(adapter)
    }

    private fun setKelaminDropdown() {
        val adapter =
            ArrayAdapter(this@TambahDataSapiActivity, R.layout.list_item, viewModel.kelaminList)
        binding.etJenisKelamin.setAdapter(adapter)
    }

    private fun initListener() = with(binding) {
        etJenisKelamin.setOnItemClickListener { _, _, i, _ ->
            viewModel.setKelamin(i)
        }

        etStatus.setOnItemClickListener { _, _, i, _ ->
            viewModel.setStatus(i)
        }

        btnSbmitSapi.setOnClickListener {
            val dataSapiNew = Sapi(
                tag = etNamaSapi.text.toString(),
                jenis = etJenisSapi.text.toString(),
                kelamin = viewModel.kelamin,
                asal = etAsalSapi.text.toString(),
                kedatangan = Kedatangan(
                    bulan = etKdtgSapi.text.toString(),
                    usia = etUsiadtgSapi.text.toString(),
                    beratBadanAwal = etBrtbdnAwalSaapi.text.toString()
                ),
                data = DataSapi(
                    usia = etUsiaskgSapi.text.toString(),
                    berat = etBrtbdnSkgSapi.text.toString(),
                    status = viewModel.status
                ),
                pemilik = Pemilik(
                    nama = "Sony",
                    noTelepon = "082264854113",
                    alamat = "Randu Barat 9/10"
                )
            )

            viewModel.addData(dataSapiNew) { isSuccess ->
                if (isSuccess) {
                    showToast("Successfully Saved")
                } else showToast("Failed")
            }
        }
    }
}