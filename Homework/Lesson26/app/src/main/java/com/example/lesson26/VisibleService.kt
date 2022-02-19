package com.example.lesson26
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

/**
 * @author Bulat Bagaviev
 * @created 13.02.2022
 */

class VisibleService: Service() {
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "1"
    private val ACTION_CLOSE = "SERVICE_ACTION_CLOSE"
    private val TIME_COUNTDOWN = 1000 * 20L
    private val TIMER_PERIOD = 1000L

    private var mCountDownTimer: CountDownTimer? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreateService() called")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "onStartService() called")
        if (ACTION_CLOSE == intent?.action)
            stopSelf();
        else {
            startCountdownTimer(TIME_COUNTDOWN, TIMER_PERIOD);
            startForeground(NOTIFICATION_ID, createNotification());
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        stopCountdownTimer()
        Log.d("TAG", "onDestroyService() called")
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        throw IllegalStateException("Not implemented yet!")
    }

    private fun createNotification(): Notification {
        Log.d("TAG", "createNotification() called")
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val intentCloseService = Intent(this, VisibleService::class.java)
        intentCloseService.action = ACTION_CLOSE
        val pendingIntentCloseService =
            PendingIntent.getService(this, 0, intentCloseService, PendingIntent.FLAG_ONE_SHOT)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(getString(R.string.notification_text_title))
            .setContentText(getString(R.string.notification_text_description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentText(getString(R.string.timer_service_content_description) + currentTime)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .addAction(0, getString(R.string.button_stop_service), pendingIntentCloseService)
            .setAutoCancel(true)

        return builder.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.notification_channel_name)

            val description = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startCountdownTimer(time: Long, period: Long) {
        mCountDownTimer  = object : CountDownTimer(time, period) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("TAG", "onTick() called with: millisUntilFinished = ["
                        + millsToSeconds(millisUntilFinished) + "]")
            }

            override fun onFinish() {
                Log.d("TAG", "onFinishCountDown() called")
                stopSelf()
            }
        }
        (mCountDownTimer as CountDownTimer).start()
    }

    private fun stopCountdownTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer!!.cancel()
            mCountDownTimer = null
        }
        Log.d("TAG", "onStopCountDown() called")
    }

    private fun millsToSeconds(time: Long): Long {
        return time / 1000L
    }
}