package com.example.sapiii.feature

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import com.example.sapiii.databinding.ActivityHomeBinding
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.feature.home.HomeFragment
import com.example.sapiii.feature.notification.AlarmItem
import com.example.sapiii.feature.notification.AlarmScheduler
import com.example.sapiii.feature.notification.AndroidAlarmScheduler
import com.example.sapiii.feature.notification.NotificationReceiver.Companion.CHANNEL_ID
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var scheduler: AlarmScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scheduler = AndroidAlarmScheduler(this)
        setChannel()
        setNotification()
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())

                else -> {

                }
            }
            true
        }
    }

    private fun setChannel() {
        val name = "Notification Channel"
        val desc = "Description of channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun setNotification() {
        // Mengatur waktu untuk notifikasi pertama kali muncul pada pukul 09:00
        val calendar1 = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // Mengatur waktu untuk notifikasi kedua kali muncul pada pukul 13:00
        val calendar2 = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 13)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // Mengatur waktu untuk notifikasi ketiga kali muncul pada pukul 18:00
        val calendar3 = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 18)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        scheduler.schedule(
            AlarmItem(
                time = LocalDateTime.ofInstant(calendar1.toInstant(), ZoneId.systemDefault())
            )
        )

        scheduler.schedule(
            AlarmItem(
                time = LocalDateTime.ofInstant(calendar2.toInstant(), ZoneId.systemDefault())
            )
        )

        scheduler.schedule(
            AlarmItem(
                time = LocalDateTime.ofInstant(calendar3.toInstant(), ZoneId.systemDefault())
            )
        )
    }

    private fun replaceFragment(homeFragment: HomeFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, homeFragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentById(R.id.frame_layout)
        if (f is HomeFragment) showDialogLogout()
        else super.onBackPressed()
    }
}