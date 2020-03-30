package com.example.demomvp.data.source

import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.local.MusicLocalDataSource
import com.example.demomvp.data.source.remote.MusicRemoteDataSource
import com.example.demomvp.data.source.remote.OnFetchDataJsonListener

class MusicRepository(
    private val mMusicLocalDataSource: MusicLocalDataSource,
    private val mMusicRemoteDataSource: MusicRemoteDataSource
): MusicDataSource.LocalDataSource, MusicDataSource.RemoteDataSource{
    companion object {
        private var sInstance: MusicRepository? = null
        fun getsInstance(
            mMusicLocalDataSource: MusicLocalDataSource,
            mMusicRemoteDataSource: MusicRemoteDataSource
        ): MusicRepository {
            if (sInstance == null) {
                sInstance = MusicRepository(mMusicLocalDataSource, mMusicRemoteDataSource)
            }
            return sInstance!!
        }
    }
    //remote
    override fun getData(listener: OnFetchDataJsonListener<Song>) {
        mMusicRemoteDataSource.getData(listener)
    }



}