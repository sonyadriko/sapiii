package com.example.sapiii.feature.timbangan

import android.os.Bundle
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityHitungBobotSapiBinding
import com.example.sapiii.repository.BeratRepository


class HitungBobotSapiActivity : BaseActivity() {

    private val bobotDatabase = BeratRepository.getInstance(Constant.REFERENCE_BERAT_SAPI)
    private lateinit var binding: ActivityHitungBobotSapiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitungBobotSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        hitung()
    }

    private fun initView() {
        showProgressDialog()
        bobotDatabase.getBobotHewan(
            onComplete = {
                dismissProgressDialog()
                binding.hasilBbsapi.text = it
            },
            onError = {
                dismissProgressDialog()
                showToast("Data is not found")
            }
        )
    }

    private fun hitung() {
        binding.buttonCalculateSapi.setOnClickListener{
            val pb = binding.etPbSapi.text.toString().toDouble()
            val ld = binding.etLdSapi.text.toString().toDouble()

            val result = prosesHitung(pb,ld)
            binding.hasilBbsapi.text = result.toString()
        }
    }

    private fun prosesHitung(pb: Double, ld: Double): Double {
        val ld2 = ld * ld
        val atas = ld2 * pb
        return atas/10840
    }

}