package com.example.demomvp.screen.musics

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.MusicRepository
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener
import java.lang.Exception

class MusicsPresenter(
    private val musicRepository: MusicRepository
) : MusicsContract.Presenter {
    private lateinit var viewContract: MusicsContract.View

    override fun getMusicList() {
        musicRepository.getData(object : OnFetchDataJsonListener<Song> {

            override fun onSuccess(data: List<Song>?) {
                data?.let { viewContract.musics(data) }
            }

            override fun onError(e: Exception?) {
                e?.let { viewContract.onError(e) }
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
