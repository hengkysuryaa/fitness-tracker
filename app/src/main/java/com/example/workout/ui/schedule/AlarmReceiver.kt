package com.example.workout.ui.schedule

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.workout.R
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val CYCLING_START = 100
        const val RUNNING_START = 101
        const val CYCLING_END = 102
        const val RUNNING_END = 103
        const val CYCLING_START_ED = 104
        const val RUNNING_START_ED = 105
        const val CYCLING_START_PD = 106
        const val RUNNING_START_PD = 107
        const val CYCLING_END_ED = 108
        const val RUNNING_END_ED = 109
        const val CYCLING_END_PD = 110
        const val RUNNING_END_PD = 111
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notifId = Random(System.currentTimeMillis()).nextInt(1000)
        Log.d("MainActivity","dor")
        var type = intent.getIntExtra("type", 0)
        var message = intent.getStringExtra("message")
        Log.d("MainActivity", type.toString())
        Log.d("MainActivity",message)
        if (type == RUNNING_START) {
            var title = "Running Scheduler: Start Running!"
            message = "Message: $message"
            createNotification(context, title, message, notifId)
        } else if (type == CYCLING_START) {
            var title = "Cycling Scheduler: Start Cycling!"
            message = "Message: $message"
            createNotification(context, title, message, notifId)
        } else if (type == CYCLING_END) {
            var title = "Cycling Scheduler: End Cycling!"
            message = "Done!"
            createNotification(context, title, message, notifId)
        } else if (type == RUNNING_END) {
            var title = "Running Scheduler: End Running!"
            message = "Done!"
            createNotification(context, title, message, notifId)
        } else if (type == RUNNING_START_ED) {
            var title = "Running Scheduler(ED): Start Running!"
            message = "EVERYDAY"
            createNotification(context, title, message, notifId)
        } else if (type == RUNNING_START_PD) {
            var title = "Running Scheduler(PD): Start Running!"
            message = "PARTICULAR DAY"
            createNotification(context, title, message, notifId)
        }
        else if (type == CYCLING_START_ED) {
            var title = "Cycling Scheduler(ED): Start Cycling!"
            message = "EVERYDAY"
            createNotification(context, title, message, notifId)
        } else if (type == CYCLING_START_PD) {
            var title = "Cycling Scheduler(PD): Start Cycling!"
            message = "PARTICULAR DAY"
            createNotification(context, title, message, notifId)
        }
        else if (type == CYCLING_END_ED) {
            var title = "Cycling Scheduler(ED): End Cycling!"
            message = "Done!"
            createNotification(context, title, message, notifId)
        } else if (type == RUNNING_END_ED) {
            var title = "Running Scheduler(ED): End Running!"
            message = "Done!"
            createNotification(context, title, message, notifId)
        }
        else if (type == CYCLING_END_PD) {
            var title = "Cycling Scheduler(PD): End Cycling!"
            message = "Done!"
            createNotification(context, title, message, notifId)
        } else if (type == RUNNING_END_PD) {
            var title = "Running Scheduler(PD): End Running!"
            message = "Done!"
            createNotification(context, title, message, notifId)
        }

//        intent.removeExtra("title")
//        intent.removeExtra("message")
    }

    private fun createNotification(context: Context, title: String, message: String, notificationId: Int) {
        val channelId = "C1"
        val channelName = "Channel 1"

        val notifMgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notifBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
                .setSound(notifSound)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000,1000,1000,1000,1000)
            notifBuilder.setChannelId(channelId)
            notifMgr.createNotificationChannel(channel)
        }

        val notification = notifBuilder.build()
        notifMgr.notify(notificationId, notification)
    }
}