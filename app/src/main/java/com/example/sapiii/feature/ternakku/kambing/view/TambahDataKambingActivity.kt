package com.example.sapiii.feature.ternakku.kambing.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityTambahDataKambingBinding
import com.example.sapiii.domain.*
import com.example.sapiii.feature.ternakku.kambing.viewmodel.TambahDataKambingViewModel

class TambahDataKambingActivity : BaseActivity() {

    private lateinit var binding: ActivityTambahDataKambingBinding
    private val viewModel: TambahDataKambingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataKambingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKelaminDropdown()
        setStatusDropdown()
        initListener()

    }

    private fun initListener() = with(binding) {
        etJenisKelaminKambing.setOnItemClickListener { _, _, i, _ ->
            viewModel.setKelamin(i)
        }

        etStatus.setOnItemClickListener { _, _, i, _ ->
            viewModel.setStatus(i)
        }

        btnSbmitKambing.setOnClickListener {
            showProgressDialog()
            val dataKambingNew = Kambing(
                tag = etNamaKambing.text.toString(),
                jenis = etJenisKambing.text.toString(),
                kelamin = viewModel.kelamin,
                asal = etAsalKambing.text.toString(),
                kedatangan = Kedatangan(
                    bulan = etKdtgKambing.text.toString(),
                    usia = etUsiadtgKambing.text.toString(),
                    beratBadanAwal = etBrtbdnAwalKambing.text.toString()
                ),
                data = DataHewan(
                    usia = etUsiaskgKambing.text.toString(),
                    berat = etBrtbdnSkgKambing.text.toString(),
                    status = viewModel.status
                ),
                pemilik = Pemilik(
                    nama = "Sony",
                    noTelepon = "082264854113",
                    alamat = "Randu Barat 9/10"
                )
            )
            viewModel.addData(dataKambingNew) { isSuccess ->
                dismissProgressDialog()
                if (isSuccess) {
                    showToast("Successfully Saved")
                } else showToast("Failed")
            }
        }

    }

    private fun setStatusDropdown() {
        val adapter =
            ArrayAdapter(this@TambahDataKambingActivity, R.layout.list_item, viewModel.statusList)
        binding.etStatus.setAdapter(adapter)
    }

    private fun setKelaminDropdown() {
        val adapter =
            ArrayAdapter(this@TambahDataKambingActivity, R.layout.list_item, viewModel.kelaminList)
        binding.etJenisKelaminKambing.setAdapter(adapter)
    }
}