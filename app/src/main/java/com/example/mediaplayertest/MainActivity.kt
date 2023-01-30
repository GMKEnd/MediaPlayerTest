package com.example.mediaplayertest

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayertest.MusicService.MyBinder

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
    }

    private val handler = Handler()

    private val runnable = Runnable {
        if (mService.getMediaPlayer().isPlaying) {

        } else {

        }
    }
}