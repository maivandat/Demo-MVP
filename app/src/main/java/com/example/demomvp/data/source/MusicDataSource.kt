package com.example.demomvp.data.source

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener

interface MusicDataSource {

    interface Local {

    }

    interface Remote {
        fun getData(listener: OnFetchDataJsonListener<Song>)
    }
}
