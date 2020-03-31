package com.example.demomvp.data.source

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.local.MusicLocalDataSource
import com.example.demomvp.data.source.remote.MusicRemoteDataSource
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener

class MusicRepository private constructor(
    private val musicLocalDataSource: MusicLocalDataSource,
    private val musicRemoteDataSource: MusicRemoteDataSource
) : MusicDataSource.Local, MusicDataSource.Remote{

    private object HOLDER {
        val INSTANCE = MusicRepository(
            musicLocalDataSource = MusicLocalDataSource.instance,
            musicRemoteDataSource = MusicRemoteDataSource.instance
        )
    }

    companion object {
        val instance: MusicRepository by lazy { HOLDER.INSTANCE }
    }

    //remote
    override fun getData(onFetchDataJsonListener: OnFetchDataJsonListener<Song>) {
        musicRemoteDataSource.getData(onFetchDataJsonListener)
    }



}