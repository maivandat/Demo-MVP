package com.example.demomvp.data.source

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener

interface MusicDataSource {
    interface LocalDataSource {

    }

    interface RemoteDataSource {
        fun getData(listener: OnFetchDataJsonListener<Song>)
    }
}