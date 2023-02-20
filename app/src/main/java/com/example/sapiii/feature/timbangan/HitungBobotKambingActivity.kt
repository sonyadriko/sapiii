package com.example.sapiii.feature.timbangan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sapiii.R
import com.example.sapiii.databinding.ActivityHitungBobotKambingBinding

class HitungBobotKambingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHitungBobotKambingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitungBobotKambingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hitung()
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