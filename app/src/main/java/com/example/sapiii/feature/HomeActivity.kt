package com.example.sapiii.feature

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sapiii.NotificationReceiver

import com.example.sapiii.databinding.ActivityHomeBinding
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.feature.home.HomeFragment
import java.util.*

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

    private fun replaceFragment(homeFragment: HomeFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, homeFragment)
        fragmentTransaction.commit()
    }

    fun setNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

// Membuat intent untuk memanggil kelas NotificationReceiver
        val intent = Intent(this, NotificationReceiver::class.java)

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
            set(Calendar.HOUR_OF_DAY, 15)
            set(Calendar.MINUTE, 11)
            set(Calendar.SECOND, 0)
        }

// Mengatur waktu untuk notifikasi ketiga kali muncul pada pukul 17:00
        val calendar3 = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 17)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

// Menentukan waktu berulang untuk notifikasi pada interval sehari
        val interval = AlarmManager.INTERVAL_DAY

// Mendaftarkan penerima notifikasi dengan AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar1.timeInMillis,
            interval,
            PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        )
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar2.timeInMillis,
            interval,
            PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        )
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar3.timeInMillis,
            interval,
            PendingIntent.getBroadcast(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        )
    }
}