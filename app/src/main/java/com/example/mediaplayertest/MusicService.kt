package com.example.mediaplayertest

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Environment
import android.os.IBinder
import android.util.Log

class MusicService : Service() {
    private val binder: IBinder = MyBinder()

    inner class MyBinder : Binder() {
        val service: MusicService
            get() = this@MusicService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private val mediaPlayer = MediaPlayer()

    private var index = 1

    private val musicDir = arrayOf(
        Environment.getExternalStorageDirectory().absolutePath + "/Downloads/response.mp4")

    fun onPrepare() {
        try {
            index = 0
            mediaPlayer.setDataSource(musicDir[index])
            mediaPlayer.prepare()
        } catch (e: Exception) {
            Log.d("hint", "can't get to the song")
            e.printStackTrace()
        }
    }

    fun getMediaPlayer(): MediaPlayer {
        return mediaPlayer
    }

    fun playOrPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        } else {
            mediaPlayer.start()
        }
    }
}