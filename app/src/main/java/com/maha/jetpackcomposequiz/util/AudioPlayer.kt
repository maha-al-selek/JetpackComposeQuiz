package com.maha.jetpackcomposequiz.util

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.annotation.RawRes

object AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private var isInitialized = false

    fun init(context: Context, @RawRes audioResId: Int) {
        if (!isInitialized) {
            val audioUri = Uri.parse("android.resource://${context.packageName}/$audioResId")
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setDataSource(context, audioUri)
                prepareAsync()
                setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.start()
                }
                setOnCompletionListener {
                    release()
                    isInitialized = false
                }
                isInitialized = true
            }
        }
    }

    fun playSound() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.seekTo(0)
            } else {
                it.start()
            }
        }
    }

    fun getDurationInMillis(): Long {
        return mediaPlayer?.duration?.toLong() ?: 0L
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        isInitialized = false
    }
}
