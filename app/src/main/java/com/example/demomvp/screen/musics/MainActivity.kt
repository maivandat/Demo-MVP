package com.example.demomvp.screen.musics

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.MusicRepository
import com.example.demomvp.data.source.local.MusicLocalDataSource
import com.example.demomvp.data.source.remote.MusicRemoteDataSource
import com.example.demomvp.screen.musics.adapter.MusicAdapter
import com.example.demomvp.utils.OnItemRecyclerOnClickListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), MusicsContract.View, OnItemRecyclerOnClickListener {

    private lateinit var adapter: MusicAdapter
    private lateinit var musicPresenter: MusicsPresenter
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()

    }

    private fun initView() {
        adapter = MusicAdapter()
        adapter!!.setOnItemClickListener(this)
        recyclerViewMusic.adapter = adapter
    }

    private fun initData() {
        val musicRepository = MusicRepository.instance
        musicPresenter = MusicsPresenter(this, musicRepository)
        musicPresenter.setView(this)
        musicPresenter.getMusicList()
    }

    override fun musics(list: MutableList<Song>) {
        adapter.updateData(list)
    }

    override fun onError(exception: Exception) {
        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
    }

    override fun onRecyclerItemClick(context: Context, obj: Song?, songs: List<Song>) {
        musicPresenter.sendMusicData(context, obj!!, songs)
    }
}
