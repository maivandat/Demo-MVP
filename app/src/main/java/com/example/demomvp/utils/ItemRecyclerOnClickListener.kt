package com.example.demomvp.utils

import com.example.demomvp.model.Song

interface ItemRecyclerOnClickListener {
    fun onRecyclerItemClick(objects: Song?, position: Int, list: MutableList<Song>)
}