package com.example.mediaplayertest

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayertest.MusicService.MyBinder
import java.io.File

class MainActivity : AppCompatActivity() {

    private var mPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createBtn: Button = findViewById(R.id.create_btn)
        createBtn.setOnClickListener {
            doCreate()
        }

        val prepareBtn: Button = findViewById(R.id.prepare_btn)
        prepareBtn.setOnClickListener {
            doPrepare()
        }

        val pauseBtn: Button = findViewById(R.id.pause_btn)
        pauseBtn.setOnClickListener {
            doPause()
        }

        val resumeBtn: Button = findViewById(R.id.resume_btn)
        resumeBtn.setOnClickListener {
            doResume()
        }

        val text: TextView = findViewById(R.id.play_status)
        val getStatusBtn: Button = findViewById(R.id.status)
        getStatusBtn.setOnClickListener {
            text.text = MediaPlayerUtil.getStatus()
        }
    }

    private fun doCreate() {
        mPlayer = MediaPlayer()
        mPlayer = MediaPlayer.create(this, R.raw.testcut)
        mPlayer.start()
    }

    private fun doPrepare() {
        mPlayer = MediaPlayer()
        val uri = Uri.parse("android.resource://com.example.mediaplayertest/" + R.raw.testcut)
        try {
            mPlayer.setDataSource(this, uri)
            mPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mPlayer.setOnPreparedListener{
            mPlayer.start()
        }
    }

    private fun doPause() {
        mPlayer.pause()
    }

    private fun doResume() {
        mPlayer.start()
    }

    private val listener = MediaPlayer.OnPreparedListener { mPlayer.start() }

    private fun doMediainit() {
        val uri = Uri.parse("android.resource://com.android.sim/" + R.raw.testcut)
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            mPlayer.setDataSource(this, uri)
            mPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mPlayer.setOnPreparedListener(listener)
    }

    private fun doPlay() {
        MediaPlayerUtil.setOnMediaStateListener(object : MediaPlayerUtil.OnMediaStateListener {
            override fun onPrepared() {
                MediaPlayerUtil.start()
            }

            override fun onSeekUpdate(curTimeInt: Int) {
                TODO("Not yet implemented")
            }

            override fun onCompletion() {
                TODO("Not yet implemented")
            }

            override fun onError(): Boolean {
                MediaPlayerUtil.reset()
                return true
            }
        })
        val url = Uri.parse("//music.163.com/outchain/player?type=2&id=2006471884&auto=1&height=66")
        val file = File(Environment.getExternalStorageDirectory(), "iwish.mp3")
        val uri = Uri.parse("android.resource://com.android.sim/" + R.raw.testcut)
        try {
            MediaPlayerUtil.prepare(uri, this)
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
}