package com.example.demomvp.data.source.local

import com.example.demomvp.data.source.MusicDataSource

class MusicLocalDataSource : MusicDataSource.Local {

    private object HOLDER { val INSTANCE = MusicLocalDataSource() }

    companion object { val instance: MusicLocalDataSource by lazy { HOLDER.INSTANCE } }
}