package com.example.demomvp.media

import com.example.demomvp.data.model.Song
import android.os.Handler
import com.example.demomvp.utils.getSongDuration

class MediaPlayerManager private constructor(): MediaSetting() {
    private var onClickItem: OnClickItemMusic? = null
    private var currentSong: Song? = null
    private val listSong: MutableList<Song> = mutableListOf()
    private val handler = Handler()
    private lateinit var updateSeekBarThread: UpdateSeeBarThread

    private object HOLDER { val INSTANCE = MediaPlayerManager() }

    companion object {
        private val TAG = MediaPlayerManager::class.java.simpleName

        val instance: MediaPlayerManager by lazy { HOLDER.INSTANCE }
    }

    fun registerItemClickMusic(onClickItemMusic: OnClickItemMusic) {
        this.onClickItem = onClickItemMusic
    }

    override fun create() {
        mediaPlayer.reset()
        mediaPlayer.apply {
            setDataSource(currentSong!!.songURL)
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
        onClickItem!!.getDuration(getDuration(), getSongDuration(getDuration().toLong()))
        onClickItem!!.setState(false)
        handler.post(updateSeekBarThread)
    }

    override fun pause() {
        mediaPlayer.pause()
        onClickItem!!.setState(true)
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
        var position = listSong.indexOf(currentSong)
        if (position == 0) position = listSong.size - 1
        else position --
        onClickItem!!.sendSong(listSong[position])
        return listSong[position]
    }

    private fun getSongNext(): Song {
        var position = listSong.indexOf(currentSong)
        if (position == listSong.size - 1) position = 0
        else position ++
        onClickItem!!.sendSong(listSong[position])
        return listSong[position]
    }

    fun updateSong(song: Song){
        this.currentSong = song
    }
    fun updateSongs(list: List<Song>) {
        this.listSong.addAll(list)
    }

    inner class UpdateSeeBarThread(
        private val handlerThread: Handler
    ): Runnable {

        override fun run() {
            if (currentDuration() < getDuration()) {
                onClickItem!!.updateTime(currentDuration(), getSongDuration(currentDuration().toLong()))
                handler.post(this)
            }else {
                handlerThread.removeCallbacks(this)
                next()
                start()
            }
        }
    }
}