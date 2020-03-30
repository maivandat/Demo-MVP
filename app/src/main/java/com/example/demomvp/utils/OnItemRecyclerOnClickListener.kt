package com.example.demomvp.utils

import android.content.Context
import com.example.demomvp.data.model.Song

interface OnItemRecyclerOnClickListener {
    fun onRecyclerItemClick(context: Context, obj: Song?, songs: List<Song>)
}