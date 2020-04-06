package com.example.demomvp.utils

import com.example.demomvp.data.model.Song

interface OnItemRecyclerOnClickListener {

    fun onRecyclerItemClick(song: Song, listSong: List<Song>)
}