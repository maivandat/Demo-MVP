package com.example.demomvp.media

import com.example.demomvp.data.model.Song
import android.os.Handler
import android.util.Log
import com.example.demomvp.utils.getSongDuration

class MediaPlayerManager private constructor(
    private val onClickItemMusic: OnClickItemMusic
): MediaSetting() {

    private var currentSong: Song? = null
    private val songs = mutableListOf<Song>()
    private val handler = Handler()
    private lateinit var updateSeekBarThread: UpdateSeeBarThread

    override fun create() {

        mediaPlayer.reset()
        mediaPlayer.apply {
            setDataSource(currentSong!!.urlSong)
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
        onClickItemMusic.getDuration(getDuration(), getSongDuration(getDuration().toLong()))
        onClickItemMusic.setState(false)
        handler.post(updateSeekBarThread)
    }

    override fun pause() {
        mediaPlayer.pause()
        onClickItemMusic.setState(true)
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
        var position = songs.indexOf(currentSong)
        if (position == 0) position = songs.size - 1
        else position--
        onClickItemMusic.sendSong(songs[position])
        return songs[position]
    }
    private fun getSongNext(): Song {
        var position = songs.indexOf(currentSong)
        if (position == songs.size - 1) position = 0
        else position++
        onClickItemMusic.sendSong(songs[position])
        return songs[position]
    }

    fun updateSong(song: Song){
        this.currentSong = song
    }
    fun updateSongs(list: List<Song>) {
        this.songs.addAll(list)
    }

    companion object {
        private val TAG = MediaPlayerManager::class.java.simpleName
        private var sInstance: MediaPlayerManager? = null
        fun getInstance(onClickItemMusic: OnClickItemMusic): MediaPlayerManager {
            if (sInstance == null) {
                sInstance = MediaPlayerManager(onClickItemMusic)
            }
            return sInstance!!
        }
    }

    inner class UpdateSeeBarThread(
        private val handlerThread: Handler
    ): Runnable {
        override fun run() {
            if (currentDuration() < getDuration()) {
                onClickItemMusic.updateTime(currentDuration(), getSongDuration(currentDuration().toLong()))
                handler.post(this)
            }else {
                handlerThread.removeCallbacks(this)
                next()
                start()
            }
        }
    }

}