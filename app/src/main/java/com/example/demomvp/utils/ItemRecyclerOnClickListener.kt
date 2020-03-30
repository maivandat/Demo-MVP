package com.example.demomvp.utils

import com.example.demomvp.data.model.Song

interface ItemRecyclerOnClickListener {
    fun onRecyclerItemClick(objects: Song?, position: Int, list: MutableList<Song>)
}