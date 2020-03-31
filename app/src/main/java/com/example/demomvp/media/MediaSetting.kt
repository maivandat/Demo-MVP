package com.example.demomvp.media

import android.media.MediaPlayer

abstract class MediaSetting {
    protected val mediaPlayer: MediaPlayer = MediaPlayer()

    abstract fun create()

    abstract fun <T> change(obj: T)

    abstract fun start()

    abstract fun pause()

    abstract fun previous()

    abstract fun next()

    abstract fun stop()

    abstract fun seek(milliSecond: Int)

    protected fun getDuration(): Int = mediaPlayer.duration

    protected fun currentDuration(): Int = mediaPlayer.currentPosition
}