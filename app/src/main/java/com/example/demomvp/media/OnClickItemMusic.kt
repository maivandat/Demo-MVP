package com.example.demomvp.media

import com.example.demomvp.data.model.Song

interface OnClickItemMusic {

    fun sendSong(song: Song)

    fun updateTime(currentDuration: Int, stringCurrentDuration: String)

    fun getDuration(duration: Int, stringDuration: String)

    fun setState(state: Boolean)
}
