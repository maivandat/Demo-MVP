package com.example.demomvp.ui.playmusic

import android.app.Activity
import android.media.MediaPlayer
import com.example.demomvp.data.model.Song
import com.example.demomvp.utils.BasePresenter

interface PlayMusicContract {
    interface View {
        fun getSong(song: Song, list: MutableList<Song>, position: Int)
        fun getSong(song: Song, position: Int)
        fun getDurationSong(position: Long)
        fun getCurrentPosition(iPosition: Int, sPosition: String)
    }

    interface Presenter: BasePresenter<View> {
        fun getSongData(activity: Activity)
        fun startSong(urlSong: String, mediaPlayer: MediaPlayer)
        fun playSong(mediaPlayer: MediaPlayer)
        fun pauseSong(mediaPlayer: MediaPlayer)
        fun nextSong(listSong: MutableList<Song>, mediaPlayer: MediaPlayer, position: Int)
        fun previousSong(listSong: MutableList<Song>, mediaPlayer: MediaPlayer, position: Int)
        fun timeSong(
            mediaPlayer: MediaPlayer, position: Int,
            listSong: MutableList<Song>, progress: Int,
            max: Int
        )
    }
}