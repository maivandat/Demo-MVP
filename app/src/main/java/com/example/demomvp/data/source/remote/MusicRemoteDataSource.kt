package com.example.demomvp.data.source.remote

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.MusicDataSource
import com.example.demomvp.data.source.remote.fetchjson.GetDataJson

class MusicRemoteDataSource : MusicDataSource.Remote {

    private object HOLDER {
        val INSTANCE = MusicRemoteDataSource()
    }

    companion object {
        val instance: MusicRemoteDataSource by lazy { HOLDER.INSTANCE }
    }

    override fun getData(listener: OnFetchDataJsonListener<Song>) {
        val getDataJson = GetDataJson(listener)
        getDataJson.getSongs()
    }

}