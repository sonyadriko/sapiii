package com.example.sapiii.feature.timbangan

import android.os.Bundle
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityHitungBobotKambingBinding
import com.example.sapiii.repository.BeratRepository

class HitungBobotKambingActivity : BaseActivity() {

    private val bobotDatabase = BeratRepository.getInstance(Constant.REFERENCE_BERAT_KAMBING)
    private lateinit var binding: ActivityHitungBobotKambingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitungBobotKambingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        hitung()
    }

    private fun initView() {
        bobotDatabase.getBobotHewan(
            onComplete = {
                binding.hasilBbkambing.text = it.toString()
            },
            onError = {
                showToast("Data is not found")
            }
        )
    }

    private fun hitung() {
        binding.buttonCalculateKambing.setOnClickListener{
            val pb = binding.etPbKambing.text.toString().toDouble()
            val ld = binding.etLdKambing.text.toString().toDouble()

            val result = proseshitung(pb,ld)
            binding.hasilBbkambing.text = result.toString()
        }
    }

    private fun proseshitung(pb: Double, ld: Double): Any {
        val ld2 = ld * ld
        val atas = ld2 * pb
        val bawah = 10*10*10*10
        return atas/bawah
    }
}