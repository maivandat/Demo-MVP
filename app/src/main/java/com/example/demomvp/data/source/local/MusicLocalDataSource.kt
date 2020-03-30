package com.example.demomvp.data.source.local

import com.example.demomvp.data.source.MusicDataSource

class MusicLocalDataSource : MusicDataSource.LocalDataSource {
    companion object {
        private var sInstance: MusicLocalDataSource? = null
        fun getsInstance(): MusicLocalDataSource {
            if (sInstance == null) {
                sInstance = MusicLocalDataSource()
            }
            return sInstance!!
        }
    }
}