package com.example.sapiii

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.sapiii.feature.HomeActivity

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

//        val mainActivity = Context as HomeActivity
//        val notificationManager = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Membuat intent untuk membuka MainActivity saat notifikasi diklik
        val contentIntent = Intent(context, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Menambahkan informasi ke dalam bundle notifikasi
        val notificationBuilder = NotificationCompat.Builder(context, "default")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Waktu Notifikasi")
            .setContentText("Saatnya istirahat sejenak!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Membuat objek NotificationManagerCompat
        val notificationManager = NotificationManagerCompat.from(context)

        // Mengirim notifikasi ke sistem
        notificationManager.notify(1, notificationBuilder.build())
    }
}