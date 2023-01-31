package com.example.mediaplayertest

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayertest.MusicService.MyBinder
import java.io.File

class MainActivity : AppCompatActivity() {

    private var mService = MusicService()

    private val mServiceConntection = object : ServiceConnection {
        override fun onServiceConnected(compName: ComponentName?, binder: IBinder?) {
            mService = (binder as MyBinder).service
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mService = MusicService()
        }
    }

    fun bindServiceConnection() {
        val intent = Intent(this, MusicService::class.java)
        startService(intent)
        bindService(intent, mServiceConntection, BIND_AUTO_CREATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.button)
        btn.setOnClickListener {
            doPlay()
        }
    }

    private fun doPlay() {
        val mediaPlayer = MediaPlayerUtil
        mediaPlayer.setOnMediaStateListener(object : MediaPlayerUtil.OnMediaStateListener {
            override fun onPrepared() {
                mediaPlayer.start()
            }

            override fun onSeekUpdate(curTimeInt: Int) {
                TODO("Not yet implemented")
            }

            override fun onCompletion() {
                TODO("Not yet implemented")
            }

            override fun onError(): Boolean {
                mediaPlayer.reset()
                return true
            }
        })
        val file = File(Environment.getExternalStorageDirectory(), "iwish.mp3")
        try {
            mediaPlayer.prepare(file.absolutePath)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val handler = Handler()

    private val runnable = Runnable {
        if (mService.getMediaPlayer().isPlaying) {

        } else {

        }
    }
}