package com.example.demomvp.ui.musics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demomvp.R
import com.example.demomvp.model.Song
import com.example.demomvp.ui.adapter.MusicAdapter
import com.example.demomvp.utils.ItemRecyclerOnClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MusicsContract.View, ItemRecyclerOnClickListener {

    private var adapter: MusicAdapter? = null
    private val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    private lateinit var musicPresenter: MusicsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        musicPresenter = MusicsPresenter.getInstance(this)
        musicPresenter.setView(this)

        musicPresenter.getMusicList()

    }

    override fun musics(list: MutableList<Song>) {
        adapter = MusicAdapter(this, list)
        recyclerViewMusic.layoutManager = manager
        adapter!!.setOnItemClickListener(this)
        recyclerViewMusic.adapter = adapter
    }

    override fun onRecyclerItemClick(obj: Song?, position: Int, list: MutableList<Song>) {
        musicPresenter.sendMusicData(this, obj!!, position, list)
    }
}
