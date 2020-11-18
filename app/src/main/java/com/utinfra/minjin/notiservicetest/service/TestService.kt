package com.utinfra.minjin.notiservicetest.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.utinfra.minjin.notiservicetest.MainActivity
import com.utinfra.minjin.notiservicetest.R
import com.utinfra.minjin.notiservicetest.Test.COUNT
import kotlin.math.sqrt

class TestService : Service(), SensorEventListener {

    private val iBinder : IBinder = Binder()

    private lateinit var sensorManager : SensorManager
    private var sensor : Sensor? = null
    private var mShakeTime : Long = 0

    private var notification : Notification = Notification()
    lateinit var notificationManager: NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        notificationShow()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST)


        return START_REDELIVER_INTENT
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {

        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            val axisX = event.values[0]
            val axisY = event.values[1]
            val axisZ = event.values[2]

            val gravityX = axisX / SensorManager.GRAVITY_EARTH
            val gravityY = axisY / SensorManager.GRAVITY_EARTH
            val gravityZ = axisZ / SensorManager.GRAVITY_EARTH

            val f = gravityX * gravityY * gravityZ * gravityX * gravityY * gravityZ
            val squared : Double = sqrt(f.toDouble())
            val gForce : Float = squared.toFloat()
            if(gForce > 1.0F) {

                val currentTime = System.currentTimeMillis()

                if(mShakeTime + 500 > currentTime) return

                mShakeTime = currentTime
                Log.d("로그", "흔들림 ${COUNT++}")

                notificationShow()
            }



        }

    }




    override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)


    }


    override fun onBind(p0: Intent?): IBinder? {

        return iBinder

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun notificationShow() {
        super.onCreate()

        val CHANNEL_ID = "channel_1"
        val channel: NotificationChannel = NotificationChannel(CHANNEL_ID, "Android_test", NotificationManager.IMPORTANCE_LOW)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

         notification =
                NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setContentTitle("오늘 걸음 수")
                        .setContentText("STEP $COUNT")
                        .setContentIntent(pendingIntent)
                        .build()

        startForeground(2, notification)
    }

}