package com.example.demomvp.screen.musics

import com.example.demomvp.data.model.Song
import com.example.demomvp.utils.BasePresenter
import java.lang.Exception

interface MusicsContract {

    interface View {

        fun musics(songList: MutableList<Song>)

        fun onError(exception: Exception)
    }

    interface Presenter: BasePresenter<View> {

        fun getMusicList()
    }
}