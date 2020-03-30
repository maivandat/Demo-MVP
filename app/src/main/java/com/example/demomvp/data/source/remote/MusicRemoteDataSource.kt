package com.example.demomvp.data.source.remote

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.MusicDataSource
import com.example.demomvp.data.source.remote.fetchjson.GetDataJson

class MusicRemoteDataSource : MusicDataSource.RemoteDataSource {
    companion object {
        private var sInstance: MusicRemoteDataSource? = null
        fun getsInstance(): MusicRemoteDataSource {
            if (sInstance == null) {
                sInstance = MusicRemoteDataSource()
            }
            return sInstance!!
        }
    }

    override fun getData(listener: OnFetchDataJsonListener<Song>) {
        val getDataJson: GetDataJson = GetDataJson(listener)
        getDataJson.getData()
    }

}