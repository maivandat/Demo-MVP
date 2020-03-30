package com.example.demomvp.screen.playmusic

import android.app.Activity
import android.media.MediaPlayer
import com.example.demomvp.data.model.Song
import com.example.demomvp.media.MediaPlayerManager
import com.example.demomvp.utils.BasePresenter

interface PlayMusicContract {
    interface View {
        fun getMusicData(song: Song, songs: List<Song>)
    }

    interface Presenter: BasePresenter<View> {
        fun getSongData(activity: Activity)
        fun pauseSong(mediaPlayerManager: MediaPlayerManager)
        fun create(mediaPlayerManager: MediaPlayerManager, song: Song, songs: List<Song>)
        fun nextSong(mediaPlayerManager: MediaPlayerManager)
        fun previousSong(mediaPlayerManager: MediaPlayerManager)
        fun startSong(mediaPlayerManager: MediaPlayerManager)
        fun stopSong(mediaPlayerManager: MediaPlayerManager)
        fun seekSong(mediaPlayerManager: MediaPlayerManager, currentDuration: Int)
        fun timeSong(
            mediaPlayer: MediaPlayer, position: Int,
            listSong: MutableList<Song>, progress: Int,
            max: Int
        )
    }
}