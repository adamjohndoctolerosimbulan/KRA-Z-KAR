package com.example.krazkar

import android.app.ActivityOptions
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.SeekBar

class CarInfoActivity4 : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var seekBar: SeekBar
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var updateSeekBarRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_info4)

        // Initialize MediaPlayer with proper AudioAttributes
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(resources.openRawResourceFd(R.raw.carsound4))
            prepareAsync()
            setOnPreparedListener {
                seekBar.max = duration
            }
        }

        // Find buttons and seek bar
        val playButton: ImageButton = findViewById(R.id.play_button)
        val pauseButton: ImageButton = findViewById(R.id.pause_button)
        val stopButton: ImageButton = findViewById(R.id.stop_button)
        seekBar = findViewById(R.id.seekBar)

        // Set up button click listeners
        playButton.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                handler.post(updateSeekBarRunnable)
            }
        }

        pauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        stopButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.prepareAsync() // Prepare the MediaPlayer to start again
                seekBar.progress = 0
                handler.removeCallbacks(updateSeekBarRunnable)
            }
        }

        // Set up seek bar change listener
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                handler.removeCallbacks(updateSeekBarRunnable)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                handler.post(updateSeekBarRunnable)
            }
        })

        updateSeekBarRunnable = Runnable {
            seekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(updateSeekBarRunnable, 100)
        }

        mediaPlayer.setOnCompletionListener {
            seekBar.progress = 0
            handler.removeCallbacks(updateSeekBarRunnable)
        }

        // Set up click listener for the button to go back to MainActivity
        findViewById<ImageButton>(R.id.backHome1).setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.release() // Release resources
                handler.removeCallbacks(updateSeekBarRunnable)
            }
            val intent = Intent(this, MainActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            startActivity(intent, options.toBundle())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release() // Release resources
        handler.removeCallbacks(updateSeekBarRunnable)
    }
}