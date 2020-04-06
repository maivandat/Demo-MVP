package com.example.demomvp.data.model

import java.io.Serializable

data class Song(
    val songTitle: String,
    val songImageURL: String,
    val songURL: String
) : Serializable

object SongEntry {
    const val SONG = "results"
    const val TITLE = "songName"
    const val URL_IMAGE = "imageURL"
    const val URL_SONG = "songURL"
}
