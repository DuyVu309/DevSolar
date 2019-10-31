package com.nor.mp3music.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.nor.mp3music.R
import com.nor.mp3music.models.Song
import java.lang.Exception

class MP3Service : Service() {

    private val NOTIFICATION_ID: Int = 1221
    var data: List<Song>? = null
    var player: MediaPlayer? = null
    var index = 0

    val item = MutableLiveData<Song>()
    val position = MutableLiveData<Int>()
    val isPlaying = MutableLiveData<Boolean>()
    private var thread: Thread? = null

    override fun onCreate() {
        super.onCreate()
        Log.e(javaClass.simpleName, "onCreate")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return MP3Binder(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun pushNotification() {
        val CHANNEL_ID = javaClass.simpleName
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
        notification.setSmallIcon(R.mipmap.ic_launcher)
        notification.setContentTitle("App MP3")
        notification.setContentText(data?.get(index)?.title)
        startForeground(NOTIFICATION_ID, notification.build())
    }

    fun create(index: Int) {
        this.index = index
        player?.release()
        player = MediaPlayer.create(this, Uri.parse(data?.get(index)?.data))
        start()
        pushNotification()
        item.postValue(data?.get(index))

        if (thread == null) {
            thread = Thread {
                while (true) {
                    try {
                        position.postValue(player?.currentPosition)
                        Thread.sleep(1000)
                    }catch (ex: Exception) {
                        return@Thread
                    }
                }
            }
            thread!!.start()
        }
    }

    fun pause() {
        player?.pause()
        isPlaying.postValue(false)
    }

    fun start() {
        player?.start()
        isPlaying.postValue(true)
    }

    fun stop() {
        thread?.interrupt()
        thread = null
        stopForeground(true)
        player?.release()
        item.postValue(null)
        stopSelf()
    }

    class MP3Binder(val service: MP3Service): Binder()
}