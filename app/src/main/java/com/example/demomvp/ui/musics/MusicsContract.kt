package com.example.demomvp.ui.musics

import android.content.Context
import com.example.demomvp.model.Song
import com.example.demomvp.ui.base.BasePresenter

interface MusicsContract {
    interface View {
        fun musics(list: MutableList<Song>)
    }

    interface Presenter: BasePresenter<View> {
        fun getMusicList()
        fun sendMusicData(context: Context, song: Song, position: Int, list: MutableList<Song>)
    }
}