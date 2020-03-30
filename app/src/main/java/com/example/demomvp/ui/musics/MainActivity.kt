package com.example.demomvp.ui.musics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.MusicRepository
import com.example.demomvp.data.source.local.MusicLocalDataSource
import com.example.demomvp.data.source.remote.MusicRemoteDataSource
import com.example.demomvp.ui.adapter.MusicAdapter
import com.example.demomvp.utils.ItemRecyclerOnClickListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), MusicsContract.View, ItemRecyclerOnClickListener {

    private var adapter: MusicAdapter? = null
    private val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    private lateinit var musicPresenter: MusicsPresenter
    private lateinit var mMusicRepository: MusicRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMusicRepository = MusicRepository.getsInstance(
            MusicLocalDataSource.getsInstance(),
            MusicRemoteDataSource.getsInstance()
        )
        musicPresenter = MusicsPresenter.getInstance(this, mMusicRepository)
        musicPresenter.setView(this)

        musicPresenter.getMusicList()

    }

    override fun musics(list: MutableList<Song>) {
        adapter = MusicAdapter(this, list)
        recyclerViewMusic.layoutManager = manager
        adapter!!.setOnItemClickListener(this)
        recyclerViewMusic.adapter = adapter
    }

    override fun onError(exception: Exception) {

    }

    override fun onRecyclerItemClick(obj: Song?, position: Int, list: MutableList<Song>) {
        musicPresenter.sendMusicData(this, obj!!, position, list)
    }
}
