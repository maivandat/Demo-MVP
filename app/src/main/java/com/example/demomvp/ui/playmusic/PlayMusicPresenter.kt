package com.example.demomvp.ui.playmusic

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import com.example.demomvp.data.model.Song
import com.example.demomvp.utils.getSongDuration

class PlayMusicPresenter(private val context: Context) : PlayMusicContract.Presenter {
    private lateinit var mView: PlayMusicContract.View
    private val LOG = PlayMusicPresenter::class.java.simpleName

    override fun getSongData(activity: Activity) {
        val intent = activity.intent
        val obj = intent.getSerializableExtra("song") as Song
        val list = intent.getSerializableExtra("listSong") as MutableList<Song>
        val position = intent.getIntExtra("position", 0)
        mView.getSong(obj, list, position)
    }

    override fun startSong(urlSong: String, mediaPlayer: MediaPlayer) {

        mediaPlayer.apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(urlSong)
            prepare()
            start()
        }

        mView.getDurationSong(mediaPlayer.duration.toLong())

    }

    override fun playSong(mediaPlayer: MediaPlayer){
        mediaPlayer.start()
    }

    override fun pauseSong(mediaPlayer: MediaPlayer) {
        mediaPlayer.pause()
    }

    override fun nextSong(listSong: MutableList<Song>, mediaPlayer: MediaPlayer, position: Int) {
        var mPosition: Int = position
        Log.d(LOG, "Position: $position")
        mPosition ++
        if (mPosition > listSong!!.size - 1) {
            mPosition = 0
        }
        mView.getSong(listSong[mPosition], mPosition)
        startSong(listSong[mPosition].urlSong, mediaPlayer)
        mView.getCurrentPosition(0, "00:00")
    }

    override fun previousSong(listSong: MutableList<Song>, mediaPlayer: MediaPlayer, position: Int) {
        var mPosition: Int = position
        Log.d(LOG, "Position: $position")
        mPosition --
        if (mPosition < 0) {
            mPosition = listSong!!.size - 1
        }

        mView.getSong(listSong[mPosition], mPosition)
        startSong(listSong[mPosition].urlSong, mediaPlayer)
        mView.getCurrentPosition(0, "00:00")
    }

    override fun timeSong(mediaPlayer: MediaPlayer, position: Int,
                          listSong: MutableList<Song>, progress: Int,
                          max: Int) {

        if (progress == max) {
            nextSong(listSong, mediaPlayer, position)
            mView.getCurrentPosition(0, "00:00")
        }else {
            var currentPosition = mediaPlayer.currentPosition
            var currentPositionStr = getSongDuration(currentPosition.toLong())
            mView.getCurrentPosition(currentPosition, currentPositionStr)
        }
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun setView(view: PlayMusicContract.View) {
        mView = view
    }

    companion object {
        private var sInstance: PlayMusicPresenter? = null

        fun getInstance(context: Context): PlayMusicPresenter {
            if (sInstance == null) {
                sInstance = PlayMusicPresenter(context)
            }
            return sInstance!!
        }
    }

}