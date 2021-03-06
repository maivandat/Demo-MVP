package com.example.demomvp.media

import com.example.demomvp.data.model.Song
import android.os.Handler
import com.example.demomvp.utils.getSongDuration

class MediaPlayerManager private constructor() : MediaSetting() {
    private var onClickItem: OnClickItemMusic? = null
    private var currentSong: Song? = null
    private val mutableListSong = mutableListOf<Song>()
    private val handler = Handler()
    private lateinit var updateSeekBarThread: UpdateSeeBarThread

    private object HOLDER {
        val INSTANCE = MediaPlayerManager()
    }

    companion object {
        val instance: MediaPlayerManager by lazy { HOLDER.INSTANCE }
    }

    fun registerItemClickMusic(onClickItemMusic: OnClickItemMusic?) {
        this.onClickItem = onClickItemMusic
    }

    override fun create() {
        mediaPlayer.reset()
        mediaPlayer.apply {
            setDataSource(currentSong?.songURL)
            prepare()
            updateSeekBarThread = UpdateSeeBarThread(handler)
        }
    }

    override fun <T> change(obj: T) {
        currentSong = obj as Song
        create()
    }

    override fun start() {
        handler.removeCallbacks(updateSeekBarThread)
        mediaPlayer.start()
        onClickItem?.getDuration(getDuration(),
                                  getSongDuration(getDuration().toLong())
        )
        onClickItem?.setState(false)
        handler.post(updateSeekBarThread)
    }

    override fun pause() {
        mediaPlayer.pause()
        onClickItem?.setState(true)
        handler.removeCallbacks(updateSeekBarThread)
    }

    override fun previous() {
        change(getSongPrevious())
    }

    override fun next() {
        change(getSongNext())
    }

    override fun stop() {
        mediaPlayer.stop()
        handler.removeCallbacks(updateSeekBarThread)
    }

    override fun seek(milliSecond: Int) {
        mediaPlayer.seekTo(milliSecond)
    }

    private fun getSongPrevious(): Song {
        var position = mutableListSong.indexOf(currentSong)
        if (position == 0) {
            position = mutableListSong.size - 1
        } else {
            position --
        }
        onClickItem?.sendSong(mutableListSong[position])
        return mutableListSong[position]
    }

    private fun getSongNext(): Song {
        var position = mutableListSong.indexOf(currentSong)
        if (position == mutableListSong.size - 1) {
            position = 0
        } else {
            position ++
        }
        onClickItem?.sendSong(mutableListSong[position])
        return mutableListSong[position]
    }

    fun updateSong(song: Song?) {
        this.currentSong = song
    }

    fun updateSongs(list: List<Song>) {
        this.mutableListSong.addAll(list)
    }

    inner class UpdateSeeBarThread(
        private val handlerThread: Handler
    ) : Runnable {

        override fun run() {
            if (getCurrentDuration() < getDuration()) {
                onClickItem?.updateTime(getCurrentDuration(),
                                         getSongDuration(getCurrentDuration().toLong()))
                handler.post(this)
            } else {
                handlerThread.removeCallbacks(this)
                next()
                start()
            }
        }
    }
}
