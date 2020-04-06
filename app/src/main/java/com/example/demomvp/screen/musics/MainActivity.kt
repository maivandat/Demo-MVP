package com.example.demomvp.screen.musics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.demomvp.R
import com.example.demomvp.data.model.Song
import com.example.demomvp.data.source.MusicRepository
import com.example.demomvp.screen.musics.adapter.MusicAdapter
import com.example.demomvp.screen.playmusic.PlayMusicActivity
import com.example.demomvp.utils.Constant
import com.example.demomvp.utils.OnItemRecyclerOnClickListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import java.lang.Exception

class MainActivity :
    AppCompatActivity(), MusicsContract.View,
    OnItemRecyclerOnClickListener {
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var musicPresenter: MusicsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initView() {
        musicAdapter = MusicAdapter()
        musicAdapter.setOnItemClickListener(this)
        recyclerViewMusic.adapter = musicAdapter
    }

    private fun initData() {
        val musicRepository = MusicRepository.instance
        musicPresenter = MusicsPresenter(musicRepository)
        musicPresenter.setView(this)
        musicPresenter.getMusicList()
    }

    override fun musics(mutableListSong: MutableList<Song>) {
        musicAdapter.updateData(mutableListSong)
    }

    override fun onError(exception: Exception) {
        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
    }

    override fun onRecyclerItemClick(song: Song?, songList: List<Song>) {
        val intent = Intent(this, PlayMusicActivity::class.java)
        intent.putExtra(Constant.KEY_SONG, song)
        intent.putExtra(Constant.KEY_LIST_SONG, songList as Serializable)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_down, R.anim.anim_up)
    }
}
