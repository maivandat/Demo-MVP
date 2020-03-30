package com.example.demomvp.ui.musics

import android.content.Context
import com.example.demomvp.data.model.Song
import com.example.demomvp.utils.BasePresenter
import java.lang.Exception

interface MusicsContract {
    interface View {
        fun musics(list: MutableList<Song>)
        fun onError(exception: Exception)
    }

    interface Presenter: BasePresenter<View> {
        fun getMusicList()
        fun sendMusicData(context: Context, song: Song, position: Int, list: MutableList<Song>)
    }
}