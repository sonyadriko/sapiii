package com.example.sapiii.feature.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.sapiii.R
import com.example.sapiii.feature.HomeActivity

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel1"
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        // Membuat intent untuk membuka MainActivity saat notifikasi diklik
        val contentIntent = Intent(context, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, contentIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Menambahkan informasi ke dalam bundle notifikasi
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Notifikasi Pakan")
            .setContentText("saatnya istirahat sejenak, waktunya makan")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Membuat objek NotificationManagerCompat
        val notificationManager = NotificationManagerCompat.from(context)

        // Mengirim notifikasi ke sistem
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder)
    }
}