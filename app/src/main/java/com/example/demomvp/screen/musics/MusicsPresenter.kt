package com.example.demomvp.screen.musics

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.MusicRepository
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener
import com.example.demomvp.screen.playmusic.PlayMusicActivity
import com.example.demomvp.utils.Constant
import java.io.Serializable
import java.lang.Exception

class MusicsPresenter internal constructor(
    private val musicRepository: MusicRepository
): MusicsContract.Presenter {

    private var viewContract: MusicsContract.View? = null

    override fun getMusicList() {
        musicRepository.getData(object : OnFetchDataJsonListener<Song> {
            override fun onSuccess(data: MutableList<Song>?) {
                viewContract!!.musics(data!!)
            }

            override fun onError(e: Exception?) {
                viewContract!!.onError(e!!)
            }

        })
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun setView(view: MusicsContract.View) {
        viewContract = view
    }
}