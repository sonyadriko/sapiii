package com.example.sapiii.feature.timbangan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sapiii.R
import com.example.sapiii.databinding.ActivityHitungBobotSapiBinding

class HitungBobotSapiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHitungBobotSapiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitungBobotSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hitung()
    }

    private fun hitung() {
        TODO("Not yet implemented")
        binding.buttonCalculateSapi.setOnClickListener{
            val pb = binding.etPbSapi.text.toString().toDouble()
            val ld = binding.etLdSapi.text.toString().toDouble()

            val result = proseshitung(pb,ld)
            binding.hasilBbsapi.text = result.toString()
        }
    }

    private fun proseshitung(pb: Double, ld: Double): Double {
        val ld2 = ld * ld
        val atas = ld2 * pb
        return atas/10840
    }
}