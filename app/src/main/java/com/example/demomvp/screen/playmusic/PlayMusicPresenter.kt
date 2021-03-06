package com.example.demomvp.screen.playmusic

import android.app.Activity
import android.content.Intent
import com.example.demomvp.data.model.Song
import com.example.demomvp.media.MediaPlayerManager
import com.example.demomvp.utils.Constant


@Suppress("UNCHECKED_CAST")
class PlayMusicPresenter : PlayMusicContract.Presenter {
    private lateinit var viewContract: PlayMusicContract.View

    override fun getSongData(activity: Activity) {
        val intent = activity.intent
        val song  =
            intent.getSerializableExtra(Constant.KEY_SONG) as Song
        val listSong =
            intent.getSerializableExtra(Constant.KEY_LIST_SONG) as List<*>
        viewContract.getMusicData(song, listSong as List<Song>)
    }

    override fun pauseSong(mediaPlayerManager: MediaPlayerManager) {
        mediaPlayerManager.pause()
    }

    override fun createSong(
        mediaPlayerManager: MediaPlayerManager,
        song: Song,
        mutableListSong: List<Song>
    ) {
        mediaPlayerManager.updateSong(song)
        mediaPlayerManager.updateSongs(mutableListSong)
        mediaPlayerManager.create()
    }

    override fun startSong(mediaPlayerManager: MediaPlayerManager) {
        mediaPlayerManager.start()
    }

    override fun stopSong(mediaPlayerManager: MediaPlayerManager) {
        mediaPlayerManager.stop()
    }

    override fun seekSong(
        mediaPlayerManager: MediaPlayerManager,
        currentDuration: Int
    ) { mediaPlayerManager.seek(currentDuration) }

    override fun nextSong(mediaPlayerManager: MediaPlayerManager) {
        mediaPlayerManager.next()
        mediaPlayerManager.start()
    }

    override fun previousSong(mediaPlayerManager: MediaPlayerManager) {
        mediaPlayerManager.previous()
        mediaPlayerManager.start()
    }

    override fun onStart() {}

    override fun onStop() {}

    override fun setView(view: PlayMusicContract.View) {
        viewContract = view
    }
}
