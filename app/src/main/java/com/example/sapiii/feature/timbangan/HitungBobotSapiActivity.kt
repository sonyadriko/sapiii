package com.example.sapiii.feature.timbangan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sapiii.MqttHandler
import com.example.sapiii.databinding.ActivityHitungBobotSapiBinding
import org.eclipse.paho.client.mqttv3.MqttClient


class HitungBobotSapiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHitungBobotSapiBinding

    val BROKER_URL = "broker.emqx.io"
    val CLIENT_ID = "Meiratri01"

    // Initialize the MQTT client
    private var mqttHandler: MqttHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitungBobotSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mqttHandler = MqttHandler()
        mqttHandler!!.connect(BROKER_URL, CLIENT_ID)
        hitung()
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

    override fun onDestroy() {
        mqttHandler?.disconnect()
        super.onDestroy()
    }

    private fun publishMessage(topic: String, message: String) {
        Toast.makeText(this, "Publishing message: $message", Toast.LENGTH_SHORT).show()
        mqttHandler?.publish(topic, message)
    }

    private fun subscribeToTopic(topic: String) {
        Toast.makeText(this, "Subscribing to topic $topic", Toast.LENGTH_SHORT).show()
        mqttHandler?.subscribe(topic)
    }


}