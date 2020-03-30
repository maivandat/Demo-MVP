package com.example.demomvp.media

import com.example.demomvp.data.model.Song

interface OnClickItemMusic {
    fun sendSong(song: Song)
    fun updateTime(currentDuration: Int, sCurrentDuration: String)
    fun getDuration(duration: Int, sDuration: String)
    fun setState(state: Boolean)
}