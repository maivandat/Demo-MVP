package com.example.demomvp.data.source

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.local.MusicLocalDataSource
import com.example.demomvp.data.source.remote.MusicRemoteDataSource
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener

class MusicRepository private constructor(
    private val localDataSource: MusicLocalDataSource,
    private val remoteDataSource: MusicRemoteDataSource
) : MusicDataSource.Local, MusicDataSource.Remote {

    private object HOLDER {
        val INSTANCE = MusicRepository(
            localDataSource = MusicLocalDataSource.instance,
            remoteDataSource = MusicRemoteDataSource.instance)
    }

    companion object {
        val instance: MusicRepository by lazy { HOLDER.INSTANCE }
    }

    // Remote
    override fun getData(listener: OnFetchDataJsonListener<Song>) {
        remoteDataSource.getData(listener)
    }
}
